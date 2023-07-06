Performance & Scalability
=========================

Application level caching
-------------------------

The application uses spring's default in-memory cache to retrieve certain resources,
to avoid hitting the database and thereby reduce the request processing time. Although
for development purposes this works, for a production deployment with potentially 
multiple service instances, a distributed cache implementation would scale better.


HTTP level caching
------------------

The application supports ETags in some cases which would allow the agents to cache
responses locally. Implementation is based on a shallow ETag filter which will always
retrieve the resource either from the cache or the database and calculate its ETag. In
a production deployment this functionality might potentially be moved over to a 
reverse proxy instead.

Bintable requests
-----------------

The application uses [Bintable](https://bintable.com) to retrieve information regarding
the provided IIN. In order to avoid issuing un-necessary requests, the API will
always store the result of each IIN lookup in the local database and serve and use them
for any subsequent card clearance requests.




