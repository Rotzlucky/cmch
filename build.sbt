name := """cmch"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava, PlayEbean)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  "mysql" % "mysql-connector-java" % "5.1.35",
  javaWs,
  evolutions,
  "org.yaml" % "snakeyaml" % "1.16",
  "org.webjars" %% "webjars-play" % "2.5.0",
  "org.webjars.bower" % "compass-mixins" % "0.12.7",
  "org.webjars" % "requirejs" % "2.1.14-1",
  "org.webjars" % "bootstrap" % "3.3.6" exclude("org.webjars", "jquery"),
  "org.webjars" % "jquery" % "1.11.1",
  "org.webjars" % "jQuery-Autocomplete" % "1.2.7",
  "org.webjars.bower" % "font-awesome" % "4.6.3"
)

javaOptions in Test += "-Dconfig.file=conf/test.conf"

routesGenerator := InjectedRoutesGenerator

PlayKeys.externalizeResources := false

offline := true

fork in run := false