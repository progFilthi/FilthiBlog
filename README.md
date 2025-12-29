# Blog Post Application – Spring Boot Backend

A backend-focused blog application built with **Java and Spring Boot**, showcasing secure authentication, role-based access control, clean architecture, and containerized development using Docker.

This project was built to demonstrate practical backend engineering skills relevant to **entry-level and junior Software Engineer** roles.

---

## Features

- JWT-based authentication
- Role-Based Access Control (RBAC) for users and admins
- RESTful API for blog post management (CRUD)
- Secure endpoint protection with Spring Security
- PostgreSQL persistence using JPA/Hibernate
- Layered architecture (Controller, Service, Repository)
- DTO-based request and response handling
- Fully containerized with Docker and Docker Compose

---

## Tech Stack

**Backend**
- Java
- Spring Boot
- Spring Security
- Spring Data JPA (Hibernate)

**Security**
- JWT Authentication
- Role-Based Access Control (RBAC)

**Database**
- PostgreSQL

**DevOps**
- Docker
- Docker Compose

---

## Architecture Overview

The application follows a clean, layered architecture:

- **Controller Layer**  
  Handles HTTP requests and responses and exposes REST endpoints.

- **Service Layer**  
  Contains business logic and enforces authorization rules.

- **Repository Layer**  
  Manages database access using Spring Data JPA.

- **DTOs (Data Transfer Objects)**  
  Used for request/response payloads to prevent exposing internal entity models and to improve validation and API safety.

---

## Authentication & Authorization

### Authentication
- Users authenticate using **JWT**
- A signed token is issued upon successful login
- Protected endpoints require the token in the request header

Authorization: Bearer <JWT_TOKEN>

markdown
Copy code

### Authorization (RBAC)
- Role-based access enforced via Spring Security
- Roles:
  - `USER`
  - `ADMIN`

**Access rules**
- Public: authentication and read-only endpoints
- Protected: create, update, and delete operations
- Admin-only: restricted administrative endpoints

All write operations are fully secured.

---

## REST API Design

The API follows RESTful best practices:
- Resource-based URLs
- Proper HTTP methods
- Stateless requests
- Meaningful HTTP status codes

### Example Endpoints

POST /api/auth/register
POST /api/auth/login

GET /api/posts
GET /api/posts/{id}
POST /api/posts
PUT /api/posts/{id}
DELETE /api/posts/{id}

yaml
Copy code

---

## Database

- PostgreSQL is used as the primary database
- Entities are mapped using JPA annotations
- Designed and tested with thousands of records during development

---

## Docker & Containerization

The application is fully containerized for consistent local development.

**Services**
- Spring Boot API
- PostgreSQL database

**Benefits**
- One-command startup
- No local database installation required
- Environment setup reduced from ~30 minutes to under 5 minutes

---

## Running Locally

### Prerequisites
- Docker
- Docker Compose

### Steps

1. Clone the repository
git clone https://github.com/progFilthi/filthishop-backend
cd blog-post-app

markdown
Copy code

2. Start the application
docker-compose up --build

markdown
Copy code

3. Access the API
http://localhost:8080

yaml
Copy code

---

## What This Project Demonstrates

- Building secure REST APIs with Spring Boot
- Implementing JWT authentication and RBAC
- Designing maintainable backend architectures
- Working with relational databases using JPA
- Applying Docker for reproducible development environments

---

## Future Improvements

- Unit and integration tests (JUnit, Mockito)
- Pagination and sorting
- Refresh token support
- API documentation with OpenAPI/Swagger
- Event-driven features (Kafka / async processing)

---

## Author

**Emma**  
Backend-focused Software Engineer  
Java • Spring Boot • Distributed Systems
