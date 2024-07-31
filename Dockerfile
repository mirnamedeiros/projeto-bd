FROM openjdk:17-jdk-alpine

LABEL maintainer="mirnagmedeiros@gmail.com"

EXPOSE 8080

COPY ./target/*.jar projeto-bd.jar

ENTRYPOINT ["java","-jar","/projeto-bd.jar"]