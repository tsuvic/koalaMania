up:
	docker compose up -d
down:
	docker compose down
app:
	docker compose exec app sh gradlew bootRun
test:
	docker compose exec app sh gradlew test