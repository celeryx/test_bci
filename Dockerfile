FROM adoptopenjdk:11-jdk-hotspot as build

WORKDIR /home

COPY gradlew ./
COPY build.gradle ./
COPY settings.gradle ./
COPY src src/
COPY gradle gradle/

RUN ./gradlew clean build

FROM openjdk:11-jre-slim-buster

COPY --from=build /home/build/libs/*SNAPSHOT.jar app.jar

ENV PORT 8090

ENTRYPOINT ["java","-jar","/app.jar"]
