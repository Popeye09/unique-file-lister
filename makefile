.SILENT:

.PHONY: clean

APP1_NAME = app1
APP2_NAME = app2
APP_NAMES = $(APP1_NAME) $(APP2_NAME)
DB_CONTAINER = unique-file-lister-database
NETWORK_NAME = unique-file-lister-network
IMAGE_NAME = smthy/unique-file-lister:latest
IMAGE_BUILDER_NAME = smthy/unique-file-lister-builder


build-local:
	cd unique-file-lister && ./gradlew assemble

clean-local:
	cd unique-file-lister && ./gradlew clean

build-image-builder:
	podman build -t $(IMAGE_BUILDER_NAME) -f unique-file-lister/builder.Dockerfile unique-file-lister
	touch build-image-builder

build-image: build-image-builder
	podman build -t $(IMAGE_NAME) -f unique-file-lister/runner.Dockerfile unique-file-lister
	touch build-image

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
	sleep 2
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
		-e APP_USER=$(APP1_NAME) \
		-p 8081:8080 \
		$(IMAGE_NAME)
	podman rm -f unique-file-lister-$(APP2_NAME) || true
	podman run -d --name unique-file-lister-$(APP2_NAME) \
		--network $(NETWORK_NAME) \
		-e SPRING_DATASOURCE_URL=jdbc:postgresql://$(DB_CONTAINER):5432/$(APP2_NAME) \
		-e SPRING_DATASOURCE_USERNAME=postgres \
		-e SPRING_DATASOURCE_PASSWORD=password \
		-e APP_USER=$(APP2_NAME) \
		-p 8082:8080 \
		$(IMAGE_NAME)

clean: stop clean-local remove remove-image remove-image-builder

start:
	podman start unique-file-lister-$(APP1_NAME) || true
	podman start unique-file-lister-$(APP2_NAME) || true
	podman start $(DB_CONTAINER) || true
stop:
	podman stop -t 1 unique-file-lister-$(APP1_NAME) || true
	podman stop -t 1 unique-file-lister-$(APP2_NAME) || true
	podman stop -t 1 $(DB_CONTAINER) || true

remove: stop
	podman rm unique-file-lister-$(APP1_NAME) || true
	podman rm unique-file-lister-$(APP2_NAME) || true
	podman rm $(DB_CONTAINER) || true
	podman network rm $(NETWORK_NAME) || true

remove-image: remove
	podman image rm $(IMAGE_NAME) || true
	rm -f build-image

remove-image-builder:
	podman image rm $(IMAGE_BUILDER_NAME) || true
	rm -f build-image-builder

