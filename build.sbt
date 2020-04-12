import sbtcrossproject.CrossPlugin.autoImport.{crossProject, CrossType}

val commonSettings = Seq(
  version := "0.1-SNAPSHOT",
  scalaVersion := "2.13.1",
  scalacOptions ++= Seq(
    "-deprecation",
    "-feature",
    "-unchecked",
    "-language:implicitConversions",
    "-Xlint",
    "-Xfatal-warnings",
    "-Ywarn-numeric-widen",
    "-Ywarn-unused",
    "-Ywarn-unused:-imports,_",
    "-Ywarn-value-discard",
  ),
)

lazy val aggregate = (project in file("aggregate"))
  .settings(commonSettings)
  .settings(
    name := "my-awesome-function-aggregate",
  )
  .aggregate(root, azure, aws)

lazy val sharedRoot = crossProject(JSPlatform)
  .crossType(CrossType.Pure)
  .in(file("."))
  .settings(commonSettings)
  .settings(
    name := "my-awesome-function-shared",
  )

lazy val sharedJs = sharedRoot.js
  .dependsOn(root)

lazy val root = (project in file("."))
  .settings(commonSettings)
  .settings(
    name := "my-awesome-function",
    libraryDependencies ++= sharedDependencies,
  )

lazy val azure = (project in file("azure"))
  .settings(commonSettings)
  .settings(
    name := "my-awesome-function-azure",
    libraryDependencies ++= azureDependencies,
    assemblyOutputPath in assembly := baseDirectory.value / "app" / "MyAwesomeFunction.jar",
  )
  .dependsOn(root)

lazy val aws = (project in file("aws"))
  .settings(commonSettings)
  .settings(
    name := "my-awesome-function-aws",
    libraryDependencies ++= awsDependencies,
    assemblyOutputPath in assembly := baseDirectory.value / "app" / "MyAwesomeFunction.jar",
  )
  .dependsOn(root)

lazy val scalajs = (project in file("scalajs"))
  .settings(commonSettings)
  .settings(
    name := "my-awesome-function-scalajs",
    scalaJSLinkerConfig ~= { _.withModuleKind(ModuleKind.CommonJSModule) },
    artifactPath in (Compile, fastOptJS) := baseDirectory.value / "dist" / "handler.js",
    artifactPath in (Compile, fullOptJS) := baseDirectory.value / "dist" / "handler.js",
  )
  .enablePlugins(ScalaJSPlugin)
  .dependsOn(sharedJs)

val sharedDependencies = Seq(
  "org.scala-js" %% "scalajs-stubs" % "1.0.0" % "provided" // scalaJSVersion
)

val azureDependencies = Seq(
  "com.microsoft.azure.functions" % "azure-functions-java-library" % "1.3.1"
)

val circeVersion = "0.11.1"

val awsDependencies = Seq(
  "com.amazonaws" % "aws-lambda-java-core"   % "1.2.0",
  "com.amazonaws" % "aws-lambda-java-events" % "2.2.5"
) ++ Seq(
  "io.circe" %% "circe-core",
  "io.circe" %% "circe-generic",
  "io.circe" %% "circe-parser",
).map(_ % circeVersion)

scalafmtOnCompile in ThisBuild := true
