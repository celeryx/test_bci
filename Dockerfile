FROM adoptopenjdk:11-jdk-hotspot as build

WORKDIR /home

COPY gradlew ./
COPY build.gradle ./
COPY settings.gradle ./
COPY src src/
COPY gradle gradle/

RUN ./gradlew clean build

FROM openjdk:11-jre-slim-buster

RUN ln -sf /usr/share/zoneinfo/America/Santiago /etc/localtime && echo "America/Santiago" > /etc/timezone

COPY --from=build /home/build/libs/*SNAPSHOT.jar app.jar

ENV PORT 8090
ENV JWT_SECRET_KEY 9a4f2c8d3b7a1e6f45c8a0b3f267d8b1d4e6f3c8a9d2b5f8e3a9c8b5f6v8a3d9
ENV JWT_EXPIRATION_TIME_MS 300000

ENTRYPOINT ["java","-jar","/app.jar"]
