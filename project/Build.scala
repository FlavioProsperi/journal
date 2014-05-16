import sbt._
import Keys._
// plugins
import com.intel.media.MediaBuildPlugin
import eu.henkelmann.sbt._

object IntelMediaBuild extends Build {

  val project_id = "journal"

  object Dependencies {
      val deps = Seq(
          // Additional dependencies go here
         "com.github.scct" %% "scct" % "0.2+" % "scct",
         "org.scala-lang" % "scala-library" % "2.10.3"
      )
  }

  object Resolvers {
      val resolver = Seq(
       // Additional resolvers go here
     )
  }

  lazy val buildSettings = Defaults.defaultSettings ++
      seq(ScctPlugin.instrumentSettings : _*) ++
      MediaBuildPlugin.MediaBuildSettings ++ Seq( // Custom plugin's settings specifics go here
        testListeners <<= target.map(t => Seq(new eu.henkelmann.sbt.JUnitXmlTestsListener(t.getAbsolutePath)))
      ) // end of buildSettings

  lazy val root = Project(   // Project's settings specifics go here
    id = project_id,
    base = file("."),
    settings = buildSettings ++ Seq(
      name := project_id,
      organization := "intelmedia.ws.journal",  
      MediaBuildPlugin.mediabuildProjectid := project_id,
      scalaVersion := "2.10.3",
      libraryDependencies ++= Dependencies.deps,
      resolvers ++= Resolvers.resolver
    )
  )
}
