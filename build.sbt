organization in ThisBuild := "com.knoldus.cc"
version in ThisBuild := "1.0-SNAPSHOT"

// the Scala version that will be used for cross-compiled libraries
scalaVersion in ThisBuild := "2.11.8"

val macwire = "com.softwaremill.macwire" %% "macros" % "2.2.5" % "provided"
val scalaTest = "org.scalatest" %% "scalatest" % "3.0.1" % Test

lazy val `knol-specialist` = (project in file("."))
  .aggregate(`cc-ingestion-api`, `cc-ingestion-impl`, `ks-api`, `ks-impl`, `analytics-api`, `analytics-impl`, domains)

lazy val `cc-ingestion-api` = (project in file("cc-ingestion-api"))
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslApi
    )
  )

lazy val `ks-api` = (project in file("ks-api"))
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslApi
    )
  ).dependsOn(domains)

lazy val `analytics-api` = (project in file("analytics-api"))
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslApi
    )
  )

lazy val `cc-ingestion-impl` = (project in file("cc-ingestion-impl"))
  .enablePlugins(LagomScala)
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslPersistenceCassandra,
      lagomScaladslKafkaBroker,
      lagomScaladslTestKit,
      macwire,
      scalaTest
    )
  )
  .settings(lagomForkedTestSettings: _*)
  .dependsOn(`cc-ingestion-api`)

lazy val `ks-impl` = (project in file("ks-impl"))
  .enablePlugins(LagomScala)
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslPersistenceCassandra,
      lagomScaladslKafkaBroker,
      lagomScaladslTestKit,
      macwire,
      scalaTest,
      "org.postgresql" % "postgresql" % "9.4.1208.jre7",
      "com.github.dnvriend" %% "akka-persistence-jdbc" % "2.5.2.0"
    )
  )
  .settings(lagomForkedTestSettings: _*)
  .dependsOn(`ks-api`, domains)

lazy val `analytics-impl` = (project in file("analytics-impl"))
  .enablePlugins(LagomScala)
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslPersistenceCassandra,
      lagomScaladslKafkaBroker,
      lagomScaladslTestKit,
      macwire,
      scalaTest
    )
  )
  .settings(lagomForkedTestSettings: _*)
  .dependsOn(`analytics-api`)

lazy val domains = (project in file("domains"))
  .enablePlugins(LagomScala)
  .settings(
    libraryDependencies ++= Seq(
      scalaTest
    )
  ).settings(lagomForkedTestSettings: _*)
