#!/bin/bash

# Set the Docker image tag
DOCKER_TAG="observability"

echo "Current Path: $(pwd)"

cd ../config-server || exit
echo "Current Path: $(pwd)"

read -p "Do you want to run config server? enter y/n --> " user_action
if [ "$user_action" == "y" ]; then
    mvn clean install
    docker build . -t justamitsaha/ms-config-server:$DOCKER_TAG
    docker push justamitsaha/ms-config-server:$DOCKER_TAG
    echo "Config server docker push completed"
else
    echo "Skipping config server"
fi
cd ../

cd discovery-service || exit
echo "Current Path: $(pwd)"

read -p "Do you want to run discovery service? enter y/n --> " user_action
if [ "$user_action" == "y" ]; then
    mvn clean install
    docker build . -t justamitsaha/ms-discovery-service:$DOCKER_TAG
    docker push justamitsaha/ms-discovery-service:$DOCKER_TAG
    echo "Discovery service docker push completed"
else
    echo "Skipping discovery service"
fi
cd ../

cd gateway-service || exit
echo "Current Path: $(pwd)"

read -p "Do you want to run gateway service? enter y/n --> " user_action
if [ "$user_action" == "y" ]; then
    mvn clean install
    docker build . -t justamitsaha/ms-gateway-service:$DOCKER_TAG
    docker push justamitsaha/ms-gateway-service:$DOCKER_TAG
    echo "Gateway service docker push completed"
else
    echo "Skipping gateway service"
fi
cd ../

cd identity-service || exit
echo "Current Path: $(pwd)"

read -p "Do you want to run identity service? enter y/n --> " user_action
if [ "$user_action" == "y" ]; then
    mvn clean install
    docker build . -t justamitsaha/ms-identity-service:$DOCKER_TAG
    docker push justamitsaha/ms-identity-service:$DOCKER_TAG
    echo "Identity service docker push completed"
else
    echo "Skipping identity service"
fi
cd ../

cd product-service || exit
echo "Current Path: $(pwd)"

read -p "Do you want to run product service? enter y/n --> " user_action
if [ "$user_action" == "y" ]; then
    mvn clean install
    docker build . -t justamitsaha/ms-product-service:$DOCKER_TAG
    docker push justamitsaha/ms-product-service:$DOCKER_TAG
    echo "Product service docker push completed"
else
    echo "Skipping product service"
fi
cd ../
