This is a basic local microservice. Where 
Identity service creates user and fetches them 
Product Service Creates products gets user information from the User Service 
Also it exposes configuration service by reading properties file

mvn spring-boot:build-image

mvn clean
mvn clean install

docker build . -t justamitsaha/ms-config-server:1gateway
docker push justamitsaha/ms-config-server:1gateway

docker build . -t justamitsaha/ms-discovery-service:1gateway
docker push justamitsaha/ms-discovery-service:1gateway

docker build . -t justamitsaha/ms-gateway-service:1gateway
docker push justamitsaha/ms-gateway-service:1gateway

docker build . -t justamitsaha/ms-identity-service:1gateway
docker push justamitsaha/ms-identity-service:1gateway

docker build . -t justamitsaha/ms-product-service:1gateway
docker push justamitsaha/ms-product-service:1gateway

sudo docker container rm f3ae

sudo docker stop $(sudo docker ps -q) 

sudo docker rm $(sudo docker ps -a -q)  	remove all  container
    -a: Includes all containers (not just running ones) in the output.
    -q: Only displays the container IDs, not the complete information about the containers.

sudo docker rmi $(sudo docker images -q)    

docker container exec -it  d0b4  /bin/bash


http://localhost:8080/actuator/circuitbreakers
http://localhost:8080/actuator/circuitbreakerevents?name=identity-circuit-breaker





