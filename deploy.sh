#!/bin/bash
set -e

while getopts p: flag
do
    case "${flag}" in
        p) port=${OPTARG};;
    esac
done

if [ -z $port ]; then port=9090; fi

mvn clean install dependency:copy-dependencies -X
sudo docker rm -f gap || true
sudo docker rm -f pg_gap || true
sudo docker rmi surati/gap || true
sudo docker build -t surati/gap .
sudo docker network create -d bridge my-gap-net || true
sudo docker run -it -d --net my-gap-net --name pg_gap \
  -e POSTGRES_USER=gap \
  -e POSTGRES_PASSWORD=admin \
  -e POSTGRES_DB=db_gap \
  postgres:11.0
echo "Waiting for PostgreSQL container..."
sleep 5
sudo docker run -it -d -p $port:8080 -e NB_THREADS=25 --net my-gap-net --name gap surati/gap