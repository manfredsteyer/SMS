echo Builds and publishes images with micro services

docker build event -t manfredsteyertest/event
docker push manfredsteyertest/event

cd ticket
call mvnw package
cd ..
docker build ticket -t manfredsteyertest/ticket
docker push manfredsteyertest/ticket

docker build invoice -t manfredsteyertest/invoice
docker push manfredsteyertest/invoice
