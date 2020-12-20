# Web API test project

This is a sample Web app, written in spring-boot, that supports most of hte HTTP verbs. It can be used when we need to determine if the undelying infrastructure for some reason restricts certain HTTP verbs. Only the root endpoint, ```/``` is configured to respond, all others will respond with ```404```.

It has been instrumented with only ```zipkin-filter```, and hence any outbound calls(not possible in current code base) are not going to be traceable.
#### Config Params needed
All configuration params are injected via System variables.
- ```SERVICE_NAME```: Name of the service as it should be reported to Zipkin
- ```ZIPKIN_URL```: Zipkin server's base URL, eg. ```http://zipkin:9411```

#### Building and Running
- Build: ```mvn clean package && docker build -f Dockerfile -t webtest:0.0.1 .```
- Run: To run, build frontend project as well, and then start ```docker-compose``` under ```demo``` directory.