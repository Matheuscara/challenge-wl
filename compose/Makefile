kong-postgres:
 	KONG_DATABASE=postgres docker compose --profile database up -d --build

kong-dbless:
	docker-compose up -d

clean:
	docker-compose kill
	docker-compose rm -f