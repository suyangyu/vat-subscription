import play.core.PlayVersion
import play.sbt.PlayImport.ws
import sbt.{ModuleID, _}

object AppDependencies {

  lazy val appDependencies: Seq[ModuleID] = compile ++ test()

  private val scalaTestPlusVersion = "2.0.0"
  private val wiremockVersion = "2.6.0"
  private val mockitoVersion = "2.13.0"
  private val catsVersion = "1.0.1"

  val compile = Seq(
    ws,
    "uk.gov.hmrc" %% "bootstrap-play-25" % "1.7.0",
    "org.typelevel" %% "cats-core" % catsVersion
  )

  def test(scope: String = "test,it") = Seq(
    "uk.gov.hmrc" %% "hmrctest" % "3.0.0" % scope,
    "org.scalatest" %% "scalatest" % "3.0.0" % scope,
    "org.pegdown" % "pegdown" % "1.6.0" % scope,
    "com.typesafe.play" %% "play-test" % PlayVersion.current % scope,
    "org.scalatestplus.play" %% "scalatestplus-play" % scalaTestPlusVersion % scope,
    "com.github.tomakehurst" % "wiremock" % wiremockVersion % scope,
    "org.mockito" % "mockito-core" % mockitoVersion % scope
  )

}