from openjdk:8u212-jre-alpine3.9

LABEL maintainer="pradeep.pati@gmail.com"

ENV APP_DIR=/usr/app

RUN mkdir -p ${APP_DIR}

COPY target/webtest-0.0.1-SNAPSHOT.jar ${APP_DIR}

EXPOSE 8080/tcp

WORKDIR ${APP_DIR}

CMD java -jar webtest-0.0.1-SNAPSHOT.jar
