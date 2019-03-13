workflow "deployAzure" {
  on = "push"
  resolves = [
    #     "Sync Function Triggers",
    "deploy packages",
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

action "install core tools" {
  uses = "actions/npm@59b64a598378f31e49cb76f27d6f3312b582f680"
  needs = ["login Azure"]
  args = "i azure-functions-core-tools"
}

action "deploy packages" {
  uses = "actions/npm@59b64a598378f31e49cb76f27d6f3312b582f680"
  needs = ["install core tools"]
  secrets = ["AZURE_APPNAME"]
  runs = "sh -c \"cd ${GITHUB_WORKSPACE}/azure/app && ${GITHUB_WORKSPACE}/node_modules/.bin/func azure functionapp publish ${AZURE_APPNAME}\""
}
