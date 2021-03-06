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

package uk.gov.hmrc.vatsubscription.services

import javax.inject.{Inject, Singleton}
import uk.gov.hmrc.http.HeaderCarrier
import uk.gov.hmrc.vatsubscription.connectors.UpdateVatSubscriptionConnector
import uk.gov.hmrc.vatsubscription.httpparsers.UpdateVatSubscriptionHttpParser.UpdateVatSubscriptionResponse
import uk.gov.hmrc.vatsubscription.models.post.PPOBPost
import uk.gov.hmrc.vatsubscription.models.{ReturnPeriod, User}
import uk.gov.hmrc.vatsubscription.models.updateVatSubscription.request._

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class UpdateVatSubscriptionService @Inject()(updateVatSubscriptionConnector: UpdateVatSubscriptionConnector) {

  def updateReturnPeriod(updatedReturnPeriod: ReturnPeriod)
                        (implicit user: User[_], hc: HeaderCarrier, ec: ExecutionContext): Future[UpdateVatSubscriptionResponse] = {

    val subscriptionModel = constructReturnPeriodUpdateModel(updatedReturnPeriod)
    updateVatSubscriptionConnector.updateVatSubscription(user, subscriptionModel, hc)
  }

  def updatePPOB(updatedPPOB: PPOBPost)
                (implicit user: User[_], hc: HeaderCarrier, ec: ExecutionContext): Future[UpdateVatSubscriptionResponse] = {

    val subscriptionModel = constructPPOBUpdateModel(updatedPPOB)
    updateVatSubscriptionConnector.updateVatSubscription(user, subscriptionModel, hc)
  }



  def constructReturnPeriodUpdateModel(updatedReturnPeriod: ReturnPeriod)
                                      (implicit user: User[_]): UpdateVatSubscription = {

    val agentOrCapacitor: Option[AgentOrCapacitor] = user.arn.map(AgentOrCapacitor(_))

    UpdateVatSubscription(
      requestedChanges = RequestedChanges(addressDetails = false, returnPeriod = true),
      updatedPPOB = None,
      updatedReturnPeriod = Some(UpdatedReturnPeriod(updatedReturnPeriod)),
      declaration = Declaration(agentOrCapacitor, Signing())
    )
  }

  def constructPPOBUpdateModel(updatedPPOB: PPOBPost)
                              (implicit user: User[_]): UpdateVatSubscription = {

    val agentOrCapacitor: Option[AgentOrCapacitor] = user.arn.map(AgentOrCapacitor(_))

    UpdateVatSubscription(
      requestedChanges = RequestedChanges(addressDetails = true, returnPeriod = false),
      updatedPPOB = Some(UpdatedPPOB(updatedPPOB)),
      updatedReturnPeriod = None,
      declaration = Declaration(agentOrCapacitor, Signing())
    )
  }
}