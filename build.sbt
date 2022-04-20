import sbt.Keys.{libraryDependencies, name}

ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.12.10"

lazy val root = (project in file("."))
  .settings(
    name := "untitled5",
libraryDependencies +=  "org.apache.spark" %% "spark-core" % "3.2.1",
libraryDependencies +=  "org.apache.spark" %% "spark-sql" % "3.2.1",
libraryDependencies += "com.ibm.db2" % "jcc" % "11.5.7.0",
libraryDependencies += "com.amazonaws" % "aws-java-sdk" % "1.12.187",
libraryDependencies += "com.ibm.stocator" % "stocator" % "1.1.4"
  )
