#!/bin/bash

cd config-service/
echo "Do you want to run config service?enter y/n"
read user_action
if [ "$user_action" == "y" ]; then
    mvn clean install
    java - jar config-service-1.0-SNAPSHOT.jar
else
    echo "Skipping config server"
fi

cd ../discovery-service/
echo "Do you want to run discovery service?enter y/n"
read user_action
if [ "$user_action" == "y" ]; then
    mvn clean install
    java - jar discovery-service-1.0-SNAPSHOT.jar
else
    echo "Skipping config server"
fi

cd ../api-gate-way-service/
echo "Do you want to run api gateway service?enter y/n"
read user_action
if [ "$user_action" == "y" ]; then
    mvn clean install
    java - jar api-gate-way-service-1.0-SNAPSHOT.jar
else
    echo "Skipping config server"
fi

cd ../redis-cache-service/
echo "Do you want to run redis-cache-service?enter y/n"
read user_action
if [ "$user_action" == "y" ]; then
    mvn clean install
    java - jar redis-cache-service-1.0-SNAPSHOT.jar
else
    echo "Skipping config server"
fi

cd ../onboard-user-service/
echo "Do you want to run onboard-user-service?enter y/n"
read user_action
if [ "$user_action" == "y" ]; then
    mvn clean install
    java - jar onboard-user-service-1.0-SNAPSHOT.jar
else
    echo "Skipping config server"
fi


