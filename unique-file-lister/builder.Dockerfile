FROM eclipse-temurin:21-jdk-alpine

WORKDIR /app
COPY . .

# Assemble only to skip tests
# Tests are run when pushing to origin
RUN ./gradlew assemble --no-daemon