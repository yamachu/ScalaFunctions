workflow "deployAzure" {
  on = "push"
  resolves = [
    #     "Sync Function Triggers",
    "deploy custom AzureFunctions",
  ]
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
  uses = "./.github/sbt"
  needs = ["only master"]
  args = "azure/assembly"
}

action "Sync Function Triggers" {
  uses = "Azure/github-actions/cli@master"
  needs = ["deploy AzureFunctions"]
  secrets = ["AZURE_RESOURCEGROUP", "AZURE_APPNAME"]
  env = {
    AZURE_SCRIPT = "az resource invoke-action --resource-group ${AZURE_RESOURCEGROUP} --action syncfunctiontriggers --name ${AZURE_APPNAME} --resource-type Microsoft.Web/sites"
  }
}

action "deploy custom AzureFunctions" {
  uses = "Azure/github-actions/functions@master"
  needs = ["login Azure"]
  env = {
    AZURE_APP_NAME = "ScalaFunctions"
    AZURE_APP_PACKAGE_LOCATION = "azure/app"
  }
  runs = "${GITHUB_WORKSPACE}/.github/azure-functions-deploy.sh"
}
