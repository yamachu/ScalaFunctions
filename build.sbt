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
    resolvers ++= azureResolvers,
    assemblyOutputPath in assembly := baseDirectory.value / "app" / "MyAwesomeFunction.jar",
  )
  .dependsOn(root)

// Todo: AWS Lambda

val azureDependencies = Seq(
  "com.microsoft.azure.functions" % "azure-functions-java-library" % "1.2.0"
)

val azureResolvers = Seq(
  "Central Maven Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"
)

scalafmtOnCompile in ThisBuild := true
