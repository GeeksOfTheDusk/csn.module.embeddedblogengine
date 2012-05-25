import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

    val appName         = "csn_mini_blogengine"
    val appVersion      = "0.3.0"
    
    val appDependencies = Seq(
      "csn_bbcodeparser" % "csn_bbcodeparser_2.9.1" % "1.0-SNAPSHOT",
      "twitter_bootstrap_module" % "twitter_bootstrap_module_2.9.1" % "1.0-SNAPSHOT"
    )
    
    object Resolvers {
  	  val csnRepo = Resolver.ssh("CSN repo", "server.coding-minds.com", 7022, "/var/www/repositories.coding-minds.com/modules/releases/")(Resolver.mavenStylePatterns)
    }

    val main = PlayProject(appName, appVersion, appDependencies, mainLang = SCALA).settings(
      resolvers += "CSN module repository" at "http://repositories.coding-minds.com/modules/releases/",
      publishMavenStyle := true,
      publishTo := Some(Resolvers.csnRepo)
    )
}
