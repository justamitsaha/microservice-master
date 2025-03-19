@echo off

echo Current Path:
cd

set "DOCKER_TAG=basic"

cd ../config-server
set /p user_action="Do you want to run config server?enter y/n --> "
echo Current Path:
if "%user_action%"=="y" (
    call mvn clean install
    call docker build . -t justamitsaha/ms-config-server:%DOCKER_TAG%
    call docker push justamitsaha/ms-config-server:%DOCKER_TAG%
    echo "Config server docker push completed"
    cd ../
) else (
    echo "Skipping config server"
    cd ../
)

cd discovery-service
set /p user_action="Do you want to run discovery service?enter y/n --> "
echo Current Path:
cd
if "%user_action%"=="y" (
    call mvn clean install
    call docker build . -t justamitsaha/ms-discovery-service:%DOCKER_TAG%
    call docker push justamitsaha/ms-discovery-service:%DOCKER_TAG%
    echo "Discovery service docker push completed"
    cd ../
) else (
    echo "Skipping discovery-service"
    cd ../
)

cd gateway-service
set /p user_action="Do you want to run gateway service?enter y/n --> "
echo Current Path:
cd
if "%user_action%"=="y" (
    call  mvn clean install
    call docker build . -t justamitsaha/ms-gateway-service:%DOCKER_TAG%
    call docker push justamitsaha/ms-gateway-service:%DOCKER_TAG%
    echo "Gateway service docker push completed"
    cd ../
) else (
    echo "Skipping gateway-service"
    cd ../
)

cd identity-service/
set /p user_action="Do you want to run identity-service?enter y/n --> "
echo Current Path:
cd
if "%user_action%"=="y" (
    call mvn clean install
    call docker build . -t justamitsaha/ms-identity-service:%DOCKER_TAG%
    call docker push justamitsaha/ms-identity-service:%DOCKER_TAG%
    echo "Identity service docker push completed"
    cd ../
) else (
    echo "Skipping identity-service"
    cd ../
)

cd product-service/
set /p user_action="Do you want to run product-service?enter y/n--> "
echo Current Path:
cd
if "%user_action%"=="y" (
    call mvn clean install
    call docker build . -t justamitsaha/ms-product-service:%DOCKER_TAG%
    call docker push justamitsaha/ms-product-service:%DOCKER_TAG%
    echo "Product service docker push completed"
    cd ../
) else (
    echo "Skipping config server"
    cd ../
)