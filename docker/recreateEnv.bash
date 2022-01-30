docker rm -f yourtube-backend
docker rm -f yourtube-frontend
docker rm -f yourtube-database

docker image rm -f docker_backend
docker image rm -f docker_frontend
docker image rm -f yourtube-database

docker-compose up