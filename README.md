# clean-architecture-quarkus project

Project based on the course [Avançando no PHP: Introdução à Clean Architecture](https://cursos.alura.com.br/course/php-introducao-clean-achitecture)
and the course [Domain Driven Design com PHP: Introdução aos conceitos](https://cursos.alura.com.br/course/domain-driven-design-php) but using Java with Quarkus

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```
./mvnw quarkus:dev
```

## Packaging and running the application

The application can be packaged using `./mvnw package`.
It produces the `clean-architecture-quarkus-1.0.0-SNAPSHOT-runner.jar` file in the `/target` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/lib` directory.

The application is now runnable using `java -jar target/clean-architecture-quarkus-1.0.0-SNAPSHOT-runner.jar`.

## Creating a native executable

You can create a native executable using: `./mvnw package -Pnative`.

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: `./mvnw package -Pnative -Dquarkus.native.container-build=true`.

You can then execute your native executable with: `./target/clean-architecture-quarkus-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/building-native-image.

## Start docker for mysql and execute the script

```
docker run --name clean-architecture-quarkus-db -e MYSQL_ROOT_PASSWORD=root MYSQL_DATABASE=clean_architecture_quarkus_db -p 3306:3306 -d mysql
```
