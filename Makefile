.PHONY: azure/run azure/run-with-debug azure/build azure/build-watch

azure/run: azure/app/MyAwesomeFunction.jar
	cd azure/app && func host start

azure/run-with-debug: azure/app/MyAwesomeFunction.jar
	cd azure/app && func host start --language-worker -- "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005"

azure/app/MyAwesomeFunction.jar:
	sbt azure/assembly

azure/build: 
	sbt azure/assembly

azure/build-watch:
	sbt "~azure/assembly"

build:
	sbt aggregate/assembly

build-watch:
	sbt "~aggregate/assembly"
