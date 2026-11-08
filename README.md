# SpringDataJPA

A Spring Boot REST API demonstrating full **CRUD (and paginated) operations** for **Users** and **Orders** backed by **Spring Data JPA** and **PostgreSQL**.

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Tech Stack](#tech-stack)
- [Architecture](#architecture)
- [API Endpoints](#api-endpoints)
- [Getting Started](#getting-started)
- [Project Structure](#project-structure)
- [Testing](#testing)
- [License](#license)

## Overview

This project is a backend service built with Spring Boot where:

- **Users** have a `name` and an `email`.
- **Orders** belong to a single **User** (many-to-one) and are identified by a `productName`.

The application exposes REST endpoints under the `/api/v1` prefix, uses DTOs for request/response payloads, Lombok for boilerplate code, and a `@RestControllerAdvice` based global exception handler.

## Features

### User APIs

| Method | Endpoint | Description |
| ------ | -------- | ----------- |
| `POST` | `/api/v1/users` | Create a new user |
| `GET` | `/api/v1/users` | Get all users |
| `GET` | `/api/v1/users/paginated` | Get users with pagination/sorting |
| `GET` | `/api/v1/users/{id}` | Get a user by id |
| `DELETE` | `/api/v1/users/{id}` | Delete a user by id |
| `PATCH` | `/api/v1/users/{id}` | Partially update a user |
| `PUT` | `/api/v1/users/{id}` | Fully update a user |

### Order APIs

| Method | Endpoint | Description |
| ------ | -------- | ----------- |
| `POST` | `/api/v1/users/{userId}/orders` | Create an order for a user |
| `GET` | `/api/v1/users/{userId}/orders` | Get all orders of a user |

## Tech Stack

- **Java** 17
- **Spring Boot** 4.0.8
- **Spring Web MVC**
- **Spring Data JPA**
- **PostgreSQL** (via JDBC driver, Neon-hosted database in the default config)
- **Lombok**
- **Bean Validation** (`spring-boot-starter-validation`)
- **Maven** (with Maven Wrapper included)
- **JUnit 5** / Spring Boot Test (context-loads test)

## Architecture

```
src/main/java
├── com.springDataJpa
│   ├── SpringDataJpaApplication.java   # Application entry point
│   ├── controller                      # REST controllers
│   ├── service                         # Business logic
│   ├── repository                      # Spring Data JPA repositories
│   ├── entities                        # JPA entities (User, Order)
│   └── exception                       # Global exceptions / handler
└── dto                                 # Request / response DTOs
```

### Key Design Points

- **Entities**: JPA entities annotated with `@Entity` (`User` → table `users`, `Order` → table `orders`).
  - `Order` uses a `@ManyToOne` relation to `User` via the `user_id` foreign key (lazy fetched).
- **Repositories**: extend `JpaRepository` (e.g., `UserRepository`, `OrderRepository`).
- **Services**: map entities to DTOs and handle persistence logic.
- **Controllers**: map HTTP verbs + paths to service calls, returning `ResponseEntity` with proper status codes.

### Validation Rules

- `name`: required, between **3 and 50 characters**.
- `email`: required, valid email format, between **12 and 50 characters**.

## API Endpoints

### Users

#### Create User — `POST /api/v1/users`

Request:

```json
{
  "name": "John Doe",
  "email": "john.doe@example.com"
}
```

Response: `201 Created`

```json
{
  "id": 1,
  "name": "John Doe",
  "email": "john.doe@example.com"
}
```

#### Get All Users — `GET /api/v1/users`

Response: `200 OK` with a list of user DTOs.

#### Get Paginated Users — `GET /api/v1/users/paginated?page=0&size=10&direction=asc&sortBy=id`

Query params:

- `page` — zero-based page index (default `0`)
- `size` — page size (default handled by the service layer)
- `direction` — `asc` or `desc` (default `asc`)
- `sortBy` — field to sort by (default `id`)

#### Get User By ID — `GET /api/v1/users/{id}`

Response: `200 OK` with the user DTO, or `404 Not Found`.

#### Delete User — `DELETE /api/v1/users/{id}`

Response: `204 No Content`.

#### Update User — `PUT /api/v1/users/{id}`

Request body:

```json
{
  "name": "Jane Doe",
  "email": "jane.doe@example.com"
}
```

Response: `200 OK` with the updated user DTO.

#### Partial Update User — `PATCH /api/v1/users/{id}`

Request body (only provided fields are updated):

```json
{
  "name": "Jane Doe"
}
```

Response: `200 OK` with the updated user DTO.

### Orders

#### Create Order — `POST /api/v1/users/{userId}/orders`

Request:

```json
{
  "productName": "Laptop"
}
```

Response: `201 Created`

```json
{
  "id": 1,
  "productName": "Laptop",
  "user": {
    "id": 1,
    "name": "John Doe",
    "email": "john.doe@example.com"
  }
}
```

#### Get Orders By User — `GET /api/v1/users/{userId}/orders`

Response: `200 OK` with a list of order DTOs for the user.

## Getting Started

### Prerequisites

- Java 17+
- Maven 3+ (or use the included Maven Wrapper: `./mvnw`)
- A running **PostgreSQL** database

### Configuration

The datasource is configured via environment variables (see `src/main/resources/application.properties`):

```properties
spring.datasource.url=${DATABASE_URL}
spring.datasource.username=${DATABASE_USERNAME}
spring.datasource.password=${DATABASE_PASSWORD}
```

For local development, a `dev` profile is active (`spring.profiles.active=dev`) and points to a PostgreSQL database hosted on Neon:

```properties
# src/main/resources/application-dev.properties
spring.datasource.url=jdbc:postgresql://...neon.tech/spring-boot?sslmode=require&channelBinding=require
spring.datasource.username=spring-boot_owner
spring.datasource.password=npg_...
server.port=3000
```

Adjust these values (or override with environment variables) to point to your own PostgreSQL instance.

### Running the Application

```bash
# From the project root
./mvnw spring-boot:run
```

The API will be available at `http://localhost:3000`.

### API Base URL

```
http://localhost:3000/api/v1
```

## Project Structure

```
SpringDataJPA/
├── pom.xml
├── mvnw / mvnw.cmd
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── com/springDataJpa/    # Main application package
│   │   │   │   ├── controller/
│   │   │   │   ├── service/
│   │   │   │   ├── repository/
│   │   │   │   ├── entities/
│   │   │   │   └── exception/
│   │   │   └── dto/                   # DTOs (create/update/response)
│   │   └── resources/
│   │       ├── application.properties
│   │       └── application-dev.properties
│   └── test/
│       └── java/com/springDataJpa/
│           └── SpringDataJpaApplicationTests.java
└── HELP.md
```

## Testing

Run the existing test suite with:

```bash
./mvnw test
```

The current test performs a Spring context load check:

```java
@SpringBootTest
class SpringDataJpaApplicationTests {
    @Test
    void contextLoads() {
    }
}
```

## License

This project does not currently declare a license.
