.PHONY: azure/run azure/run-with-debug azure/build azure/build-watch dump-outdated outdated

azure/run: azure/app/MyAwesomeFunction.jar
	cd azure/app && func host start

azure/run-with-debug: azure/app/MyAwesomeFunction.jar
	cd azure/app && func host start --language-worker -- "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005"

azure/app/MyAwesomeFunction.jar:
	sbt azure/assembly

azure/build: 
	sbt azure/assembly

azure/build-watch:
	sbt "project azure" "~assembly"

build:
	sbt aggregate/assembly

build-watch:
	sbt "project aggregate" "~assembly"

%/target/dependency-updates.txt:
	sbt $(subst /target/dependency-updates.txt,,$@)/dependencyUpdatesReport

outdated: azure/target/dependency-updates.txt
	python ./tools/parse-dependency-updates.py $^
	@rm $^
