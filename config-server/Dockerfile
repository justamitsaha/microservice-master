#Start with a base image containing Java runtime
FROM openjdk:17-jdk-slim as build

#Information around who maintains the image
MAINTAINER justamitsaha@gmail.com

EXPOSE 8071

# Add the application's jar to the container
COPY target/config-server-999.jar config-server-999.jar

#execute the application
ENTRYPOINT ["java","-jar","/config-server-999.jar"]

#docker run -p 8080:8080 justamitsaha/eazy-bank-api-jwt-aws
