import Dependencies._

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "jp.gr.java_conf.hangedman",
      scalaVersion := "2.12.2",
      version      := "0.1.0-SNAPSHOT"
    )),
    name := "hive-udtf",
    libraryDependencies += "org.apache.hive" % "hive-exec" % "2.1.1",
    libraryDependencies += scalaTest % Test,
    resolvers += "Spring Plugins" at "http://repo.spring.io/plugins-release/"
  )
