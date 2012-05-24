import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

    val appName         = "csn_blogengine_saple"
    val appVersion      = "1.0-SNAPSHOT"

    val appDependencies = Seq(
      "csn_bbcodeparser" % "csn_bbcodeparser_2.9.1" % "1.0-SNAPSHOT",
      "twitter_bootstrap_module" % "twitter_bootstrap_module_2.9.1" % "1.0-SNAPSHOT",
      "closed-social-network's-mini-blog-engine" % "closed-social-network's-mini-blog-engine_2.9.1" % "1.0-SNAPSHOT"
    )

    val main = PlayProject(appName, appVersion, appDependencies, mainLang = SCALA).settings(
      resolvers += "CSN Modules Repository" at "http://repositories.coding-minds.com/modules/releases/",
      resolvers += "Local Play Repository" at "file:///usr/local/Cellar/play/2.0.1/libexec/repository/local/"
    )

}
