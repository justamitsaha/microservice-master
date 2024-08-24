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

Resiliency
http://localhost:8080/actuator/circuitbreakers
http://localhost:8080/actuator/circuitbreakerevents?name=identity-circuit-breaker
Put debug point in API with auth like http://localhost:8080/auth/user/findByEmailContaining/007@mailinator.com 
and see the circuit breaker status changing

To test circuit breaker with Feign client put debug point in getContactInfo  in ConfigurationController  of product service
and hit the API http://localhost:8080/authentication/user/getUserContactInfo/2. It can go to Feign client or web client, check from identity service logs
When it goes to Feign client we can see "companyContactInfoDto": null is returned



To see retry pattern put debug point in getContactInfo  in ConfigurationController  of product service
and hit the API http://localhost:8080/product/configuration/contact-info in the 


Rabbit MQ
docker run -it --rm --name chapri -p 5672:5672 -p 15672:15672 rabbitmq:3.13-management

docker exec --interactive --tty kafka1  kafka-topics --bootstrap-server kafka1:19092 --create --topic product --replication-factor 3 --partitions 3

docker exec --interactive --tty kafka1 kafka-topics --bootstrap-server kafka1:19092 --describe

docker exec --interactive --tty kafka1 kafka-topics --bootstrap-server kafka1:19092 --describe --topic product

docker exec --interactive --tty kafka1  kafka-console-producer --bootstrap-server localhost:9092,kafka2:19093,kafka3:19094 --topic product

docker exec --interactive --tty kafka1  kafka-console-consumer --bootstrap-server localhost:9092,kafka2:19093,kafka3:19094 --topic product --from-beginning

