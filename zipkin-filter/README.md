# Zipkin-Filter

Zipkin-filter is a general-purpose HTTP Servlet filter, intended to be injected into web apps running in Servlet container. It processes all incoming requests, enriches them with tracing markers, and at the end of transactionm it send out the span to Zipkin.

#### Configuration params
All configuration params are injected via System variables.
- ```SERVICE_NAME```: Name to be reported to Zipkin
- ```ZIPKIN_URL```: Zipkin server's base URL, eg. ```http://localhost:9411```