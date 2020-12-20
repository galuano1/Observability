# Observability For Distributed MicroServices

####[Approach Rationale](docs/Decision-Process.md)

A fairly basic implementation of the selected approach can be seen below.
![Filter based approach](docs/Filter-Approach.png)

In this implementation, 
- A Servlet filter (zipkin-filter) is injected into all webapps running on top of a Servlet Container. The Filter intercepts all incoming HTTP Requests, detects tracers embedded in them, and if not, add them and passes them along.
- The Service detect the tracers embedded and uses them when making outbound calls.
- Once the control has returned to the Filter at the end of the transaction, the transactions metrics (Span in zipkin terms) is reported to the Observability Service.

##### Prerequisites to build and run the Demo
1. Java 8
2. Maven 3.4+
3. Docker 19+

##### Build
Either 
- run ```build.sh``` to build everything on *nix like systems

Or run the following commands in given sequence
1. ```cd zipkin-filter```
2. ```mvn clean install```
3. ```cd ../demo/frontend```
4. ```mvn clean package```
5. ```docker build -f Dockerfile -t frontend:0.0.1 .```
6. ```cd ../web-api-test``` 
7. ```mvn clean package```
8. ```docker build -f Dockerfile -t webtest:0.0.1 .```

##### Run
Run the following commands in the given sequence
1. ```cd demo```
2. ```docker-compose up```

##### Test
- Hit the Frontend <br>
```curl --location --request PUT 'http://localhost:9091/'``` <br>
```curl --location --request GET 'http://localhost:9091/'``` <br>
- Hit the Backend <br>
```curl --location --request DELETE 'http://localhost:9090/'``` <br>
```curl --location --request PATCH 'http://localhost:9090/'``` <br>
- View the traces at [http://localhost:9411/](http://localhost:9411/)
