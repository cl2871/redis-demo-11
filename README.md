# README

## Project Overview

This is a simple Spring Boot application for connecting to a Redis data store.
This particular project uses Java 11, Gradle, and Webflux (for reactive Redis access).

Project dependencies can be found in [build.gradle](./build.gradle).

## Redis Setup

### Run Redis

Before starting, make sure to have Redis running (e.g. locally, on docker, etc.).

For instance, to run Redis on docker, you can do:

`docker run --name some-redis -d redis:latest`

Note: at the time of writing, this project used Redis version 5.

## Application Setup

### Application Properties

To run this project, set the following necessary environment variable for Redis address.

- `REDIS_HOST`

This project uses the default port of 6379, which can be modified in the application.properties.

### Running the Application

To run the application, you can either use your IDE or utilize the gradle wrapper on the command line:

```
# cd into the project directory
./gradlew bootRun
```

Note: make sure to have your JAVA_HOME environment variable set.

## Reference

This project was inspired by the following resources:
- https://www.youtube.com/watch?v=KrxXdnCxiFg
- https://github.com/donnemartin/system-design-primer/blob/master/solutions/system_design/pastebin/README.md