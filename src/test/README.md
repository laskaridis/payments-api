
How to run tests
----------------

To run all test from command line
```bash
$ ./mvnw clean test
```

To run integration tests only from command line
```bash
$ ./mvnw clean test -Dgroups=IntegrationTest
```

To run unit tests only from command line
```dbn-psql
$ ./mvnw clean test -Dgroups=\!IntegrationTest
```