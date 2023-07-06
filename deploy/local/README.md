# Development environment

To start a local development environment you will need to have docker
compose install on your machine. You can follow the instructions
[here](https://docs.docker.com/compose/install/) to set it up.

### Init

To start the development environment `cd` in this directory and type:

```bash
$ docker-compose up
```
This will start the following services:

* [Postgres database server](https://www.postgresql.org/docs/current/)
* [Keycloak openid connect server](https://www.keycloak.org/documentation)
* [Consul discovery server](https://www.consul.io/docs)
* [Zipkin server](https://zipkin.io/)

### Manage

* Access the keycloak admin console [here](http://localhost:8080/admin/master/console/)
* Access the keycloak jackpots realm login page [here](http://localhost:8080/realms/jackpots/account/#/)
* Access the consul console [here](http://localhost:8500/)
* Access the zipkin server console [here](http://localhost:9411/)

