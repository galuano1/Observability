# Web Frontend
This is a sample webapp, configured with Zipkin-filter, and some custom code in the controller to pass on the tracing headers to the backend.

#### APIs implemented
- ```GET /```
- ```PUT /```
- ```POST /```

#### Config Params needed
All configuration params are injected via System variables.
- ```SERVICE_NAME```: Name of the service as it should be reported to Zipkin
- ```ZIPKIN_URL```: Zipkin server's base URL, eg. ```http://zipkin:9411```
- ```WEBTEST_URL```: URL for the backend, e.g. ```http://webtest:8080/```

#### Building and Running
- Build: ```mvn clean package && docker build -f Dockerfile -t frontend:0.0.1 .```
- Run: To run, build webtest project as well, and then start ```docker-compose``` under ```samples``` directory.