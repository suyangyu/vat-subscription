/*
 * Copyright 2018 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.vatsubscription.service

import assets.TestUtil
import uk.gov.hmrc.vatsubscription.connectors.mocks.MockUpdateVatSubscriptionConnector
import uk.gov.hmrc.vatsubscription.helpers.BaseTestConstants.{testAgentUser, testArn, testUser}
import uk.gov.hmrc.vatsubscription.helpers.PPOBTestConstants.ppobModelMaxPost
import uk.gov.hmrc.vatsubscription.httpparsers.UpdateVatSubscriptionHttpParser.UpdateVatSubscriptionResponse
import uk.gov.hmrc.vatsubscription.models.MAReturnPeriod
import uk.gov.hmrc.vatsubscription.models.updateVatSubscription.request._
import uk.gov.hmrc.vatsubscription.models.updateVatSubscription.response.{ErrorModel, SuccessModel}
import uk.gov.hmrc.vatsubscription.services.UpdateVatSubscriptionService

class UpdateVatSubscriptionServiceSpec extends TestUtil with MockUpdateVatSubscriptionConnector {

  def setup(response: UpdateVatSubscriptionResponse): UpdateVatSubscriptionService = {
    mockUpdateVatSubscriptionResponse(response)
    new UpdateVatSubscriptionService(mockUpdateVatSubscriptionConnector)
  }

  "Calling .updateReturnPeriod" when {

    "connector call is successful" should {

      "return successful UpdateVatSubscriptionResponse model" in {
        val service = setup(Right(SuccessModel("12345")))
        val result = service.updateReturnPeriod(MAReturnPeriod)(testUser, hc, ec)
        await(result) shouldEqual Right(SuccessModel("12345"))
      }
    }

    "connector call is unsuccessful" should {

      "return successful UpdateVatSubscriptionResponse model" in {
        val service = setup(Left(ErrorModel("ERROR", "Error")))
        val result = service.updateReturnPeriod(MAReturnPeriod)(testUser, hc, ec)
        await(result) shouldEqual Left(ErrorModel("ERROR", "Error"))
      }
    }
  }

  "Calling .updatePPOB" when {

    "connector call is successful" should {

      "return successful UpdateVatSubscriptionResponse model" in {
        val service = setup(Right(SuccessModel("12345")))
        val result = service.updatePPOB(ppobModelMaxPost)(testUser, hc, ec)
        await(result) shouldEqual Right(SuccessModel("12345"))
      }
    }

    "connector call is unsuccessful" should {

      "return successful UpdateVatSubscriptionResponse model" in {
        val service = setup(Left(ErrorModel("ERROR", "Error")))
        val result = service.updatePPOB(ppobModelMaxPost)(testUser, hc, ec)
        await(result) shouldEqual Left(ErrorModel("ERROR", "Error"))
      }
    }
  }

  "Calling .constructReturnPeriodUpdateModel" when {

    val service = new UpdateVatSubscriptionService(mockUpdateVatSubscriptionConnector)

    "user is not an Agent" should {

      val result = service.constructReturnPeriodUpdateModel(MAReturnPeriod)(testUser)

      val expectedResult = UpdateVatSubscription(
        requestedChanges = RequestedChanges(addressDetails = false, returnPeriod = true),
        updatedPPOB = None,
        updatedReturnPeriod = Some(UpdatedReturnPeriod(MAReturnPeriod)),
        declaration = Declaration(None, Signing())
      )

      "return a correct UpdateVatSubscription model" in {
        result shouldEqual expectedResult
      }
    }

    "user is an Agent" should {

      val result = service.constructReturnPeriodUpdateModel(MAReturnPeriod)(testAgentUser)

      val expectedResult = UpdateVatSubscription(
        requestedChanges = RequestedChanges(addressDetails = false, returnPeriod = true),
        updatedPPOB = None,
        updatedReturnPeriod = Some(UpdatedReturnPeriod(MAReturnPeriod)),
        declaration = Declaration(Some(AgentOrCapacitor(testArn)), Signing())
      )

      "return an UpdateVatSubscription model containing agentOrCapacitor" in {
        result shouldEqual expectedResult
      }
    }
  }

  "Calling .constructPPOBUpdateModel" when {

    val service = new UpdateVatSubscriptionService(mockUpdateVatSubscriptionConnector)

    "user is not an Agent" should {

      val result = service.constructPPOBUpdateModel(ppobModelMaxPost)(testUser)

      val expectedResult = UpdateVatSubscription(
        requestedChanges = RequestedChanges(addressDetails = true, returnPeriod = false),
        updatedPPOB = Some(UpdatedPPOB(ppobModelMaxPost)),
        updatedReturnPeriod = None,
        declaration = Declaration(None, Signing())
      )

      "return a correct UpdateVatSubscription model" in {
        result shouldEqual expectedResult
      }
    }

    "user is an Agent" should {

      val result = service.constructPPOBUpdateModel(ppobModelMaxPost)(testAgentUser)

      val expectedResult = UpdateVatSubscription(
        requestedChanges = RequestedChanges(addressDetails = true, returnPeriod = false),
        updatedPPOB = Some(UpdatedPPOB(ppobModelMaxPost)),
        updatedReturnPeriod = None,
        declaration = Declaration(Some(AgentOrCapacitor(testArn)), Signing())
      )

      "return an UpdateVatSubscription model containing agentOrCapacitor" in {
        result shouldEqual expectedResult
      }
    }
  }
}
