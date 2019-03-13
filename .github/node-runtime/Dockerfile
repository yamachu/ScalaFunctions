FROM node:10

LABEL "com.github.actions.name"="node-runtime"
LABEL "com.github.actions.description"="node runner"
LABEL "com.github.actions.icon"="archive"
LABEL "com.github.actions.color"="purple"

LABEL "repository"="https://github.com/yamachu/ScalaFunctions"
LABEL "homepage"="https://github.com/yamachu/ScalaFunctions"
LABEL "maintainer"="Yusuke Yamada <yamachu.dev@gmail.com>"

ADD entrypoint.sh /entrypoint.sh
ENTRYPOINT ["/entrypoint.sh"]