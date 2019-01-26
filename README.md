## How to build

### Azure Functions

```sh
$ sbt azure/assembly
# if you want to handle file changes and build
$ sbt "~azure/assembly"
```

## How to run

### Azure Functions

Required:
* JDK 8 (func commands required 8)
* Azure Functions Core Tools

```sh
$ cd azure/app
$ func host start
# if you want to debug jar by attaching process
$ func host start --language-worker -- "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005"
```