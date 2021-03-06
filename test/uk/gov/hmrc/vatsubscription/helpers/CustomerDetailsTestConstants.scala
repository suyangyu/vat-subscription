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

package uk.gov.hmrc.vatsubscription.helpers

import uk.gov.hmrc.vatsubscription.models.CustomerDetails
import BaseTestConstants._
import play.api.libs.json.{JsObject, JsValue, Json}

object CustomerDetailsTestConstants {

  val customerDetailsModelMin = CustomerDetails(None, None, None, None)

  val customerDetailsModelMax = CustomerDetails(
    Some(firstName),
    Some(lastName),
    Some(orgName),
    Some(tradingName)
  )

  val customerDetailsModelMaxWithFRS = CustomerDetails(
    Some(firstName),
    Some(lastName),
    Some(orgName),
    Some(tradingName),
    hasFlatRateScheme = true
  )

  val customerDetailsJsonMaxWithFRS: JsValue = Json.obj(
    "firstName" -> firstName,
    "lastName" -> lastName,
    "organisationName" -> orgName,
    "tradingName" -> tradingName,
    "hasFlatRateScheme" -> true
  )

  val customerDetailsJsonMax: JsValue = Json.obj(
    "firstName" -> firstName,
    "lastName" -> lastName,
    "organisationName" -> orgName,
    "tradingName" -> tradingName,
    "hasFlatRateScheme" -> false
  )

  val customerDetailsJsonMin: JsObject = Json.obj("hasFlatRateScheme" -> false)
}
