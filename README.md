# Unique File Lister

A RESTful web application built with Spring Boot to recursively traverse directories, list files with unique base names, and track query history in a PostgreSQL database. This project is meant to demonstrate my software engineering skills, including API design, database (repository) integration, testing, containerization and CI/CD practices. It is meant to run on a Linux based system.

If you have any questions or offers to make, feel free to **[Contact](#contact)** me.


[Build Status](https://github.com/Popeye09/unique-file-lister/actions)

## Functional requirements

For the specification, see [doc/specification.md](doc/specification.md)

## Table of Contents

- [Overview](#overview)
- [Installation and Setup](#installation-and-setup)
- [Usage](#usage)
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Database Schema](#database-schema)
- [Testing](#testing)
- [Documentation](#documentation)
- [CI/CD](#cicd)
- [Contact](#contact)
- [Features planned](#features-planned)

## Overview

The Unique File Lister is a Spring Boot application I designed to showcase common object oriented software engineering practices used when developing APIs. It provides two main services:

- **UniqueFileService**: Recursively traverses a specified directory to identify files with unique base names and count their occurrences, returning results as a JSON array.
- **HistoryService**: Logs and retrieves query history, including usernames, timestamps, directories, and file extension filters.

The application uses a PostgreSQL database to store query history, Gradle for building, and Podman for containerization. The project is tested with unit and integration tests and includes Javadoc documentation. CI/CD is managed via GitHub Actions.

## Installation and Setup

### Prerequisites
- Podman (or Docker, see [Run](#run))
- Make (for running the provided `Makefile`)

The project is tested to work on Linux-based systems, but if you want to run it on macOS, run:

```bash
podman machine init
podman machine start
```
first.

### Run
1. **Clone the Repository**:
   ```bash
   git clone https://github.com/Popeye09/unique-file-lister.git
   cd unique-file-lister
   ```

1. **Run with make and podman**:
    ```bash
    make run
    ```
    - **Notes**:

    I did not try this, but if you really want to, you can alias podman as docker with:
    ```
    alias podman=docker
    ```
    and things might work.
    
    This alias only applies to the current shell session.

### Clean
- **Run make clean**:

    ```
    make clean
    ```

## Usage

Running the project creates 2 unique-file-lister instances available on your host:

- **app1**: `http://localhost:8081`
- **app2**: `http://localhost:8082`

Each instance connects to a single database container that holds their respective databases (`app1` and `app2`).

For the examples, I will use app1.

The application exposes two REST endpoints.

### 1. Unique File Listing
- **Endpoint**: `GET /getUnique/{directory}?username={username}&extension={extension}`
- **Parameters**:
  - `directory` (path): Directory to traverse (e.g., `usr/bin`). This will be interpreted as /usr/bin
  - `username` (query, optional): Username of the requester (defaults to system user).
  - `extension` (query, optional): File extension filter (e.g., `txt`).
- **Example Request**:
  ```bash
  curl "http://localhost:8081/getUnique/etc?username=bob&extension=conf"
  ```
- **Example Response**:
  ```json
  {
    "config.conf": 2,
    "settings.conf": 1
  }
  ```
- **Notes**:

  **WARNING: Avoid** directories with **recursive symbolic links**, as **they cause infinite loops and** may **lead to stack overflow**.
  
  During development, I used 
  ```sh
  find -L <target_directory> \( -type f -o -type l \) -name <target_filename>
  ```
   to confirm directory traversal behavior.

### 2. Query History
- **Endpoint**: `GET /history`
- **Example Request**:
  ```bash
  curl "http://localhost:8081/history"
  ```
- **Example Response**:
  ```json
  [
    {
      "id": 1,
      "username": "bob",
      "timestamp": 1633024800000,
      "directory": "/usr/bin",
      "extension": null
    },
    {
      "id": 2,
      "username": "alice",
      "timestamp": 1633024900000,
      "directory": "/etc",
      "extension": "conf"
    }
  ]
  ```


## Features

- **Unique File Listing**:
  - Recursively traverses directories to count files with unique base names.
  - Supports filtering by file extension.
  - Logs queries to a PostgreSQL database.
- **Query History**:
  - Retrieves all past queries with details (username, timestamp, directory, extension).
- **Error Handling**:
  - Handles cases like non-existent directories and non-directory paths
- **Containerized Deployment**:
  - Uses Podman with multi-stage builds for lightweight container images.

## Technologies Used

- **Backend**: Spring Boot 3.4.5
- **Database**: PostgreSQL
- **Build Tool**: Gradle
- **Containerization**: Podman
- **Testing**: JUnit, Spring Boot Test, Mockito
- **Documentation**: Javadoc, OpenAPI (Swagger)
- **CI/CD**: GitHub Actions
- **Other Libraries**: Lombok, ModelMapper, Spring Data JPA

## Database Schema

The application uses a single table in PostgreSQL:

| Table   | Column     | Type        | Description                                  |
|---------|------------|-------------|----------------------------------------------|
| history | id         | BIGINT      | Primary key, auto-incremented                |
|         | username   | TEXT        | Username of the requester                    |
|         | timestamp  | BIGINT      | Timestamp of the query (milliseconds)        |
|         | directory  | TEXT        | Directory path queried                       |
|         | extension  | TEXT        | File extension filter (nullable)             |

## Testing

The project includes unit and integration tests, located in `src/test/java`:
- **Unit Tests**: Cover service logic, such as `UniqueFileRecursiveTreeTraverseService` and `SimpleFileReaderService`.
- **Integration Tests**: Validate controller endpoints and database interactions (e.g., `UniqueFileControllerIntegrationTests`, `HistoryControllerIntegrationTests`).

### Local testing

To run the tests locally, you must run a postgres database yourself.

1. Run postgres database using podman:

    ```sh
    podman run --name unique-file-lister-database -e POSTGRES_USER=test -e POSTGRES_PASSWORD=password -e POSTGRES_DB=test -p 5432:5432 -d --replace postgres:latest
    ```
1. Run the tests:

    ```sh
    cd unique-file-lister
    ./gradlew test
    ```

## Documentation

- **Javadoc**: 

    Javadoc is provided in the source files, generate it with:

  ```bash
  cd unique-file-lister
  ./gradlew javadoc
  ```
  The output will be in `build/docs/javadoc`.
- **OpenAPI**: 

    Swagger documentation is available at `http://localhost:8081/swagger-ui` when the application is running.

    Documentation can also be accessed at `http://localhost:8081/doc`

## CI/CD

The project uses GitHub Actions for continuous integration. The workflow:
- Runs tests on every push.
- Tests code readability.

_Note that it takes a lot of effort to satisfy super-linter_

See `.github/workflows` for details.

## Contact

- **Author**: Kovács Áron
- **Email**: kovacs.aron13@gmail.com
- **GitHub**: [Popeye09](https://github.com/Popeye09)

Feel free to reach out with questions or offers!


## Features planned

- Read files as specific user (if user exists)

- Handle and access denied errors.

- Detect and prevent infinite loops caused by recursive symbolic links.

- Add REST endpoint

    - /generateFileStructure?depth=5&totalFiles=20&uniqueBaseNames=3

- upload images to dockerhub!

[Jump up](#unique-file-lister)