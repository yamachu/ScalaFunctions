#!/bin/bash

sh -c "npm i azure-functions-core-tools && cd ${FUNCTIONS_ROOT_PATH:-${GITHUB_WORKSPACE}} && ${GITHUB_WORKSPACE}/node_modules/.bin/func azure functionapp publish ${AZURE_APPNAME}"
