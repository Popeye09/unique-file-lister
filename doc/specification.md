# Unique File Lister RESTful app using the spring framework

## Overview
The Unique File Lister is a RESTful application built with Spring Boot.

- **UniqueFileService**: Lists files with unique base name and counts their occurrences under a specified directory recursively.
- **HistoryService**: Tracks and returns the history of queries made to `UniqueFileService`.

Services use Postgres database to store query history. The project uses Gradle for building and Podman for containerization.

## Functional Requirements

### UniqueFileService
- **Endpoint**: `GET /getUnique/{directory}?username={username}`
  - **Path Variable**: `directory` - The directory path to search recursively.
  - **Query Parameter**: `username` - The Linux username of the requester.
  - **Functionality**: 
    - Recursively traverse the directory and find files with unique base name.
    - Return a JSON array of file base names with number of occurrences with that unique name.
    - Log the query (username, timestamp, directory) in the database.

### HistoryService
- **Endpoint**: `GET /history`
  - **Functionality**: 
    - Retrieve all query records from the database.
    - Return a JSON array of objects with `username`, `timestamp`, and `directory`.

## Database
- **Type**: Postgres
- **Table**: `history`
  - **Columns**:
    - `id`: BIGINT, PRIMARY KEY, AUTO_INCREMENT
    - `username`: VARCHAR(255)
    - `timestamp`: TIMESTAMP
    - `directory`: VARCHAR(255)

## Build and Deployment
- **Build Tool**: Gradle
- **Containerization**: Podman with multi-stage builds to include only executables in the final image.
- **Run Command**: `make run` builds and runs the app.

## Additional Requirements
- **Unit Tests**
- **Documentation**: Javadoc generated for all classes and methods.
- **CI/CD**: Managed via Git with GitHub Actions for automated testing.