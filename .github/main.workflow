workflow "deployAzure" {
  on = "push"
  resolves = ["deploy AzureFunctions"]
}

action "only master" {
  uses = "actions/bin/filter@d820d56839906464fb7a57d1b4e1741cf5183efa"
  args = "branch master"
}

action "login Azure" {
  uses = "Azure/github-actions/login@master"
  needs = ["build azure package"]
  secrets = ["AZURE_SERVICE_PASSWORD", "AZURE_SERVICE_APP_ID", "AZURE_SERVICE_TENANT", "AZURE_SUBSCRIPTION"]
}

action "deploy AzureFunctions" {
  uses = "Azure/github-actions/functions@master"
  needs = ["login Azure"]
  env = {
    AZURE_APP_NAME = "ScalaFunctions"
    AZURE_APP_PACKAGE_LOCATION = "azure/app"
  }
}

action "build azure package" {
  uses = "actions/docker/cli@8cdf801b322af5f369e00d85e9cf3a7122f49108"
  needs = ["only master"]
  args = "run --rm hseeberger/scala-sbt:8u181_2.12.8_1.2.8 sh -c \"cd $GITHUB_WORKSPACE && sbt azure/assembly\""
}