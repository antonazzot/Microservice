FROM maven:3.8.5-openjdk-17-slim as builder

ADD pom.xml pom.xml
ADD src src/

RUN mvn clean package

FROM openjdk:17-alpine
COPY --from=builder target/KafkaModule-0.0.1.jar application.jar

EXPOSE 8084

CMD java -jar application.jar