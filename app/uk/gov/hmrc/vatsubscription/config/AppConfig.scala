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

package uk.gov.hmrc.vatsubscription.config

import javax.inject.{Inject, Singleton}

import play.api.Mode.Mode
import play.api.{Configuration, Environment}
import uk.gov.hmrc.play.config.ServicesConfig
import uk.gov.hmrc.vatsubscription.config.featureswitch.{FeatureSwitch, FeatureSwitching}

@Singleton
class AppConfig @Inject()(val runModeConfiguration: Configuration, environment: Environment) extends ServicesConfig with FeatureSwitching {
  override protected def mode: Mode = environment.mode

  def desUrl: String =
    getString(
      if (isEnabled(featureswitch.StubDESFeature)) "microservice.services.des.stub-url"
      else "microservice.services.des.url"
    )

  lazy val desAuthorisationToken: String = s"Bearer ${getString("microservice.services.des.authorisation-token")}"
  lazy val desEnvironmentHeader: (String, String) =
    "Environment" -> getString("microservice.services.des.environment")

  override def isEnabled(featureSwitch: FeatureSwitch): Boolean = super.isEnabled(featureSwitch)

}
