name := """play-java"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava, PlayEbean)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  "mysql" % "mysql-connector-java" % "5.1.35",
  javaWs,
  evolutions,
  "org.yaml" % "snakeyaml" % "1.17",
  "org.webjars.bower" % "compass-mixins" % "0.12.7"
)

javaOptions in Test += "-Dconfig.file=conf/test.conf"

routesGenerator := InjectedRoutesGenerator

PlayKeys.externalizeResources := false

offline := true;

fork in run := false