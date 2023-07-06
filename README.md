Payments API
============

Pre-requisites
--------------

The api needs java 17 and a postgres database to run. You can install java
via [sdkman](https://sdkman.io/). For convenience, there is a docker stack
located in `deploy/local` that you can launch locally using docker compose,
provided you have installed [docker](https://www.docker.com/products/docker-desktop/).

Running the application
-----------------------

Start the application's dependencies:

```
$ cd ./deploy/local
$ docker compose up
```

To build the application locally type the following in the project root:
```
$ ./mvnw clean package
```

To run it type the following in the project root:
```
$ java -jar target/payments-api-0.0-SNAPSHOT.jar
```

What to do next
---------------

* Check out the API documentation [here](http://localhost:8081/swagger-ui/index.html#/)
* Import in Postman a collection of requests that can be found in [./doc/api/](./doc/api/payments-api.postman_collection.json)
* Read more about the API's security model [here](./doc/Security.md)
* Read more about the API's performance features [here](./doc/Performance.md)
* Learn more on how to run the application tests [here](./src/test/README.md)
* Check out coverage reports in `target/site/jacoco/index.html`.
