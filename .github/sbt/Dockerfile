FROM hseeberger/scala-sbt:8u181_2.12.8_1.2.8

LABEL "com.github.actions.name"="sbt"
LABEL "com.github.actions.description"="easy sbt runner"
LABEL "com.github.actions.icon"="archive"
LABEL "com.github.actions.color"="purple"

LABEL "repository"="https://github.com/yamachu/ScalaFunctions"
LABEL "homepage"="https://github.com/yamachu/ScalaFunctions"
LABEL "maintainer"="Yusuke Yamada <yamachu.dev@gmail.com>"

ADD entrypoint.sh /entrypoint.sh
ENTRYPOINT ["/entrypoint.sh"]