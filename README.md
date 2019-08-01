# ScalaFunctions

Sample projects for running Scala and Scala.js application on Azure Functions, AWS Lambda and more...

## How to build

### Azure Functions (JVM)

```sh
$ sbt azure/assembly
# if you want to handle file changes and build
$ sbt "project azure" "~assembly"
```

or use Makefile
```sh
$ make azure/build
# if you want to handle file changes and build
$ make azure/build-watch
```

### Azure Functions (Node.js)

_Now, I don't prepare easy build command :(_

```sh
$ sbt scalajs/fullOptJS
# if you want to handle file changes and build
$ sbt "project scalajs" "~fullOptJS"
```

`scalajs/dist/handler.js` will be built.
And, 

```sh
$ cd azurejs
$ npx webpack
```

## How to run

### Azure Functions (JVM)

Required:
* JDK 8 (func commands required 8)
* Azure Functions Core Tools

```sh
$ cd azure/app
$ func host start
# if you want to debug jar by attaching process
$ func host start --language-worker -- "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005"
```

if you can use Docker, use Docker! (recommended)
```sh
$ make docker/init # build Azure Functions Java Host
$ make docker/azure-functions
```

### Azure Functions (Node.js)

_Now, I don't prepare easy run command :(_

Required:
* Node.js 8 or 10
* Azure Functions Core Tools

```sh
$ cd azurejs
$ func host start
# Todo: How to debug...
```
