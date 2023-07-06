Security
========

The API implements security using _signed_ JWT access tokens. The application
plays both the role of the _resource_ and _authorization_ server, meaning that
it also exposes endpoints to define users and mint new JWT tokens.

User registration (sign-in)
--------------------------

The API provides the `/api/v1/users` resource to register new users. User
passwords are not stored in the database in plain text, rather encoded using 
BCrypt hashing function.

> For more information on how to register a new user, please refer to
> the [API doc](http://localhost:8081/swagger-ui/index.html#/).

User authentication (login)
---------------------------

The application authorizes users using JWT access tokens. To issue a new
access token for an existing user the API you need to issue a `POST` request
to `/api/v1/logins` resource, with the user's credentials in the request 
payload. If the credentials are correct, the API will respond with a `200 OK`
response and will include the newly minted access token in the response 
`Authorization` header, as a `Bearer` token.

> For more information on how to login as a new user, please refer to
> the [API doc](http://localhost:8081/swagger-ui/index.html#/).

User authorization
------------------

To consume the API's protected resources you will need to include in each
request an `Authorization` header a valid JWT access token using the `Bearer` 
authentication scheme, as follows:

```
Authorization: Bearer ...
```

Not that for simplicity the API does not support a more refined authorization
model using user roles.

> For more information on how to consume protected resources, please refer to
> the [API doc](http://localhost:8081/swagger-ui/index.html#/).
