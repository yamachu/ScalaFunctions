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
    "-Ywarn-unused-import",
    "-Ywarn-value-discard",
  ),
)

lazy val aggregate = (project in file("aggregate"))
  .settings(commonSettings)
  .settings(
    name := "my-awesome-function-aggregate",
  )
  .aggregate(root, azure)

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
    libraryDependencies ++= sharedDependencies
  )

lazy val azure = (project in file("azure"))
  .settings(commonSettings)
  .settings(
    name := "my-awesome-function-azure",
    libraryDependencies ++= azureDependencies,
    assemblyOutputPath in assembly := baseDirectory.value / "app" / "MyAwesomeFunction.jar",
  )
  .dependsOn(root)

// Todo: AWS Lambda

lazy val scalajs = (project in file("scalajs"))
  .settings(commonSettings)
  .settings(
    name := "my-awesome-function-scalajs",
    scalaJSLinkerConfig ~= { _.withModuleKind(ModuleKind.CommonJSModule) },
    scalaJSModuleKind := ModuleKind.CommonJSModule,
    scalacOptions += "-P:scalajs:sjsDefinedByDefault",
    artifactPath in (Compile, fastOptJS) := baseDirectory.value / "dist" / "handler.js",
    artifactPath in (Compile, fullOptJS) := baseDirectory.value / "dist" / "handler.js",
  )
  .enablePlugins(ScalaJSPlugin)
  .dependsOn(sharedJs)

val sharedDependencies = Seq(
  "org.scala-js" %% "scalajs-stubs" % scalaJSVersion % "provided"
)

val azureDependencies = Seq(
  "com.microsoft.azure.functions" % "azure-functions-java-library" % "1.2.2"
)

scalafmtOnCompile in ThisBuild := true
