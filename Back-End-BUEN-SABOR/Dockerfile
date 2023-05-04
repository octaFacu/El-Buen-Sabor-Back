#FROM alpine:latest AS build
#RUN apt-get update
#RUN apt-get install openjdk-17-jdk -y
#COPY . .
#RUN ./gradlew bootJar --no-daemon

#FROM openjdk:17-alpine
#EXPOSE 8080
#COPY ./build/libs/demo-0.0.1-SNAPSHOT.jar ./app.jar

#ENTRYPOINT ["java","-jar","app.jar"]


FROM alpine:latest as build

RUN apk update
RUN apk add openjdk17

COPY . .
RUN chmod +x ./gradlew
RUN ./gradlew bootJar --no-daemon

FROM openjdk:17-alpine
EXPOSE 8080
COPY --from=build ./build/libs/claseDocker-0.0.1.jar ./app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]