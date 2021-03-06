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

package uk.gov.hmrc.vatsubscription.models.updateVatSubscription.request

import play.api.libs.json.Json
import uk.gov.hmrc.play.test.UnitSpec

class AgentOrCapacitorSpec extends UnitSpec {

  "AgentOrCapacitor Writes" should {

    val model: AgentOrCapacitor = AgentOrCapacitor("XAIT0000000000")

    "output a correctly formatted json object" in {
      val result = Json.obj(
        "identification" -> Json.obj(
          "ARN" -> "XAIT0000000000"
        )
      )
      AgentOrCapacitor.writes.writes(model) shouldBe result
    }
  }
}
