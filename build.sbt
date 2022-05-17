import sbt.Keys.{libraryDependencies, name}
import sbtassembly.AssemblyKeys.assembly

ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.12.10"

lazy val root = (project in file("."))
  .settings(
    name := "sparkPractice",
    assembly / mainClass := Some("Main"),
    assembly / assemblyMergeStrategy := {
      case PathList("META-INF", xs @ _*) => MergeStrategy.discard
      case x => MergeStrategy.first
    },
libraryDependencies +=  "org.apache.spark" %% "spark-core" % "3.2.1" % "provided",
libraryDependencies +=  "org.apache.spark" %% "spark-sql" % "3.2.1" % "provided",
libraryDependencies += "com.ibm.db2" % "jcc" % "11.5.7.0",
libraryDependencies += "com.amazonaws" % "aws-java-sdk" % "1.12.187",
libraryDependencies += "com.ibm.stocator" % "stocator" % "1.1.4",
libraryDependencies += "mysql" % "mysql-connector-java" % "8.0.27"
  )
