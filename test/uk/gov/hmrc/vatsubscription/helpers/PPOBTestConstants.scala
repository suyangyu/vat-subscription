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

import play.api.libs.json.{JsValue, Json}
import uk.gov.hmrc.vatsubscription.models.get.{PPOBAddressGet, PPOBGet}
import uk.gov.hmrc.vatsubscription.models.post.{PPOBAddressPost, PPOBPost}
import uk.gov.hmrc.vatsubscription.models.ContactDetails

object PPOBTestConstants {

  val addLine1 = "Add Line 1"
  val addLine2 = "Add Line 2"
  val addLine3 = "Add Line 3"
  val addLine4 = "Add Line 4"
  val addLine5 = "Add Line 5"
  val postcode = "TE37 7AD"
  val countryCode = "ES"

  val rlsIndicator = "0001"
  val website = "www.test.com"

  val phoneNumber = "01234 567890"
  val mobileNumber = "07700 123456"
  val faxNumber = "01234 098765"
  val email = "test@test.com"
  val emailVerified = true

  val ppobAddressModelMax = PPOBAddressGet(
    addLine1,
    Some(addLine2),
    Some(addLine3),
    Some(addLine4),
    Some(addLine5),
    Some(postcode),
    countryCode
  )

  val ppobAddressModelMaxPost = PPOBAddressPost(
    addLine1,
    Some(addLine2),
    Some(addLine3),
    Some(addLine4),
    Some(addLine5),
    Some(postcode),
    None
  )

  val contactDetailsModelMax = ContactDetails(
    Some(phoneNumber),
    Some(mobileNumber),
    Some(faxNumber),
    Some(email),
    Some(emailVerified)
  )

  val contactDetailsModelMin = ContactDetails(None, None, None, None, None)

  val ppobModelMax = PPOBGet(ppobAddressModelMax, Some(contactDetailsModelMax), Some(website))

  val ppobModelMaxPost = PPOBPost(ppobAddressModelMaxPost, Some(contactDetailsModelMax), Some(website))


  val ppobAddressGetJson: JsValue = Json.obj("line1" -> "Ronaldini Road", "line3" -> "Pell Way", "postCode" -> "R10 AAA", "countryCode" -> "BRAZIL")
  val ppobAddressGetValue: PPOBAddressGet = PPOBAddressGet("Ronaldini Road", None, Some("Pell Way"), None, None, Some("R10 AAA"),"BRAZIL")

  val ppobAddressPostJson: JsValue = Json.obj("line1" -> "Ronaldini Road", "postCode" -> "R10 AAA")
  val ppobAddressPostValue: PPOBAddressPost = PPOBAddressPost("Ronaldini Road", None, None, None, None, Some("R10 AAA"), None)
  val ppobAddressPostWritesResult: JsValue = Json.obj("line1" -> "Ronaldini Road", "postCode" -> "R10 AAA", "addressValidated" -> true)
}