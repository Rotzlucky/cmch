name := """play-java"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava, PlayEbean)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  "mysql" % "mysql-connector-java" % "5.1.35",
  javaWs,
  evolutions
)

javaOptions in Test += "-Dconfig.file=conf/test.conf"

PlayKeys.externalizeResources := false

//fork in run := true
offline := true;

fork in run := true