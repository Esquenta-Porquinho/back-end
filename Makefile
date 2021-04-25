DEV  = "dev/docker-compose.yml"
PROD = "prod/docker-compose.yml" # TODO


up.db.dev:
	docker-compose -f $(DEV) up -d

down.db.dev:
	docker-compose -f $(DEV) down

clean:
	docker ps -a | grep "pig" | awk '{print $1}' | xargs docker container rm
	docker images | grep "pig" | awk '{print $1}' | xargs docker rmi
