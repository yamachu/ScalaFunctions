val commonSettings = Seq(
  version := "0.1-SNAPSHOT",
  scalaVersion := "2.12.8",
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

lazy val root = (project in file("."))
  .settings(commonSettings)
  .settings(
    name := "my-awesome-function",
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

val azureDependencies = Seq(
  "com.microsoft.azure.functions" % "azure-functions-java-library" % "1.2.2"
)

val circeVersion = "0.11.1"

val commonDependencies = Seq() ++ Seq(
  "io.circe" %% "circe-core",
  "io.circe" %% "circe-generic",
  "io.circe" %% "circe-parser"
).map(_ % circeVersion)

scalafmtOnCompile in ThisBuild := true
