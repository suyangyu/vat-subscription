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

package uk.gov.hmrc.vatsubscription.connectors

import assets.{MockHttpClient, TestUtil}
import uk.gov.hmrc.vatsubscription.helpers.BaseTestConstants.testUser
import uk.gov.hmrc.vatsubscription.helpers.UpdateVatSubscriptionTestConstants._
import uk.gov.hmrc.vatsubscription.httpparsers.UpdateVatSubscriptionHttpParser.UpdateVatSubscriptionResponse
import uk.gov.hmrc.vatsubscription.models.updateVatSubscription.request.UpdateVatSubscription
import uk.gov.hmrc.vatsubscription.models.updateVatSubscription.response.{ErrorModel, SuccessModel}

import scala.concurrent.Future

class UpdateVatSubscriptionConnectorSpec extends TestUtil with MockHttpClient {

  "UpdateVatSubscriptionConnector url()" should {

    object TestConnector extends UpdateVatSubscriptionConnector(mockHttp, mockAppConfig)

    lazy val result: String = TestConnector.url("123456789")

    "correctly format the url" in {
      result shouldEqual "http://localhost:9156/vat/subscription/vrn/123456789"
    }
  }

  "UpdateVatSubscriptionConnector .updateVatSubscription()" when {

    val requestModel: UpdateVatSubscription = UpdateVatSubscription(
      requestedChanges = changeReturnPeriod,
      updatedPPOB = None,
      updatedReturnPeriod = Some(updatedReturnPeriod),
      declaration = nonAgentDeclaration
    )

    def setup(response: UpdateVatSubscriptionResponse): UpdateVatSubscriptionConnector = {
      mockHttpPut[UpdateVatSubscription, UpdateVatSubscriptionResponse](response)
      new UpdateVatSubscriptionConnector(mockHttpClient, mockAppConfig)
    }

    "http PUT is successful" should {
      lazy val connector = setup(Right(SuccessModel("12345")))

      lazy val result: Future[UpdateVatSubscriptionResponse] = connector.updateVatSubscription(testUser, requestModel, hc)

      "return successful UpdateVatSubscriptionResponse model" in {
        await(result) shouldEqual Right(SuccessModel("12345"))
      }
    }

    "http PUT is unsuccessful" should {
      lazy val connector = setup(Left(ErrorModel("BAD_REQUEST", "REASON")))

      lazy val result: Future[UpdateVatSubscriptionResponse] = connector.updateVatSubscription(testUser, requestModel, hc)

      "return successful UpdateVatSubscriptionResponse model" in {
        await(result) shouldEqual Left(ErrorModel("BAD_REQUEST", "REASON"))
      }
    }
  }
}
