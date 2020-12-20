cd zipkin-filter && mvn clean install
cd ../demo/frontend && mvn clean package && docker build -f Dockerfile -t frontend:0.0.1 .
cd ../web-api-test && mvn clean package && docker build -f Dockerfile -t webtest:0.0.1 .