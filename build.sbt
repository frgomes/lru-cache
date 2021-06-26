ThisBuild / organization     := "io.mathminds"
ThisBuild / organizationName := "mathminds"
ThisBuild / name             := "lru-cache"

ThisBuild / scalaVersion       := "2.13.3"
ThisBuild / crossScalaVersions := Seq("2.13.3", "3.0.0")

ThisBuild / Test / parallelExecution := false
ThisBuild / Test / fork := true
ThisBuild / run  / fork := true

lazy val zioVersion = "1.0.9"

lazy val dependencies : Seq[Setting[_]] =
  Seq(
    libraryDependencies ++= Seq(
      "dev.zio" %% "zio"               % zioVersion,
    ),
  )

lazy val zioTestFramework : Seq[Setting[_]] =
  Seq(
    libraryDependencies ++= Seq(
      "dev.zio" %% "zio-test"          % zioVersion % Test,
      "dev.zio" %% "zio-test-sbt"      % zioVersion % Test,
    ),
    testFrameworks += new TestFramework("zio.test.sbt.ZTestFramework")
  )

lazy val root = (project in file("."))
  .settings(name := (ThisBuild / name).value)
  .settings(dependencies)
  .settings(zioTestFramework)
