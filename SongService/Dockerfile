FROM maven:3.8.5-openjdk-17-slim as builder

ADD pom.xml pom.xml
ADD src src/

RUN mvn clean package

FROM openjdk:17-alpine
COPY --from=builder target/songartifact-0.0.1.jar application.jar

EXPOSE 8081:8081

CMD java -jar application.jar