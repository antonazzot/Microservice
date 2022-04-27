FROM maven:3.8.5-openjdk-17-slim AS build

ADD pom.xml pom.xml
ADD src src/

RUN mvn clean package

FROM openjdk:17-alpine
COPY --from=build target/Evreka-0.0.1.jar application.jar

EXPOSE 8761

CMD java -jar application.jar