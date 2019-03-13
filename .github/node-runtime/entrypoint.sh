#!/bin/bash

sh -c "cd ${FUNCTIONS_ROOT_PATH:-${GITHUB_WORKSPACE}} && ${GITHUB_WORKSPACE}/node_modules/.bin/func azure functionapp publish ${AZURE_APPNAME}"
