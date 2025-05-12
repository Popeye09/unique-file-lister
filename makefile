.SILENT:

.PHONY: build build-image run clean

APP1_NAME = app1
APP2_NAME = app2
APP_NAMES = $(APP1_NAME) $(APP2_NAME)
DB_CONTAINER = unique-file-lister-database
NETWORK_NAME = unique-file-lister-network
IMAGE_NAME = smthy/unique-file-lister:latest

build-local:
	cd unique-file-lister && ./gradlew assemble

build-image:
	podman build -t $(IMAGE_NAME) unique-file-lister

run: build-image
	podman network create $(NETWORK_NAME) || true
	podman rm -f $(DB_CONTAINER) || true
	# Start database container
	podman run -d --name $(DB_CONTAINER) \
		--network $(NETWORK_NAME) \
		-e POSTGRES_PASSWORD=password \
		-p 5432:5432 \
		postgres:latest
	# Wait for database to start up
	until podman exec $(DB_CONTAINER) pg_isready -U postgres; do sleep 2; done
	# Create databases
	# Create databases
	for db in $(APP_NAMES); do \
		echo "Creating database $$db..."; \
		podman exec -u postgres $(DB_CONTAINER) createdb -U postgres $$db || exit 1; \
	done
	# Start application instances
	podman rm -f unique-file-lister-$(APP1_NAME) || true
	podman run -d --name unique-file-lister-$(APP1_NAME) \
		--network $(NETWORK_NAME) \
		-e SPRING_DATASOURCE_URL=jdbc:postgresql://$(DB_CONTAINER):5432/$(APP1_NAME) \
		-e SPRING_DATASOURCE_USERNAME=postgres \
		-e SPRING_DATASOURCE_PASSWORD=password \
		-p 8081:8080 \
		$(IMAGE_NAME)
	podman rm -f unique-file-lister-$(APP2_NAME) || true
	podman run -d --name unique-file-lister-$(APP2_NAME) \
		--network $(NETWORK_NAME) \
		-e SPRING_DATASOURCE_URL=jdbc:postgresql://$(DB_CONTAINER):5432/$(APP2_NAME) \
		-e SPRING_DATASOURCE_USERNAME=postgres \
		-e SPRING_DATASOURCE_PASSWORD=password \
		-p 8082:8080 \
		$(IMAGE_NAME)

clean: stop
	podman rm unique-file-lister-$(APP1_NAME) || true
	podman rm unique-file-lister-$(APP2_NAME) || true
	podman rm $(DB_CONTAINER) || true
	podman network rm $(NETWORK_NAME) || true
	podman image rm $(IMAGE_NAME) || true
	cd unique-file-lister && ./gradlew clean

start:
	podman start unique-file-lister-$(APP1_NAME) || true
	podman start unique-file-lister-$(APP2_NAME) || true
	podman start $(DB_CONTAINER) || true
stop:
	podman stop unique-file-lister-$(APP1_NAME) || true
	podman stop unique-file-lister-$(APP2_NAME) || true
	podman stop $(DB_CONTAINER) || true

