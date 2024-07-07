This is a basic local microservice. Where 
Identity service creates user and fetches them 
Product Service Creates products gets user information from the User Service 
Also it exposes configuration service by reading properties file

mvn spring-boot:build-image

mvn clean
mvn clean install

docker build . -t justamitsaha/ms-product-service:1gateway

docker push justamitsaha/ms-identity-service:1gateway



