#Start with a base image containing Java runtime
FROM openjdk:17-jdk-slim as build

# Install curl
RUN apt-get update && apt-get install -y curl

#Information around who maintains the image
MAINTAINER justamitsaha@gmail.com

EXPOSE 8090

# Add the application's jar to the container
COPY target/identity-service-999.jar identity-service-999.jar

#execute the application
ENTRYPOINT ["java","-jar","/identity-service-999.jar"]

#docker run -p 8080:8080 justamitsaha/eazy-bank-api-jwt-aws
