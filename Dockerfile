FROM openjdk:12.0.2-jdk-oracle
MAINTAINER Cezar Mathe <cezarmathe @ gmail.com>

COPY . /cnc-api/
WORKDIR /cnc-api

RUN /cnc-api/gradlew check
RUN /cnc-api/gradlew build

RUN mv /cnc-api/build/libs/cnc-api.jar /cnc-api.jar

EXPOSE 8080

ENTRYPOINT java -jar /cnc-api.jar