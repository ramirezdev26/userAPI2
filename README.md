# Users API

**A secure and scalable API for managing user data with JWT authentication.**

## Overview

The **Users API** is a modern, secure, and scalable CRUD API designed for managing user data, built with **Java**. As part of the Capstone Project, this API integrates seamlessly with existing legacy systems while introducing enhanced functionality and security measures, such as **JWT (JSON Web Tokens)** for secure token-based authentication.

This API provides endpoints for creating, reading, updating, and deleting user information, ensuring secure access and easy extensibility through Clean Architecture principles.

## Table of Contents
1. [Key Features](#key-features)
2. [Requirements](#requirements)
3. [API Endpoints](#api-endpoints)
4. [Authentication](#authentication)
5. [Folder Structure](#folder-structure)

## Key Features

- **CRUD Operations**: Supports creating, reading, updating, and deleting user data.
- **Legacy Data Compatibility**: Works with existing systems, ensuring smooth migration and integration.
- **Security**: JWT-based authentication protects sensitive user data and restricts unauthorized access.
- **API Client Simulation**: An included client simulates CRUD operations, allowing for easy testing.
- **Java Implementation**: Built using Java, following Clean Architecture for maintainability and scalability.
## Requirements

- **Java 11+**: Ensure you have at least Java 11 installed.
- **Maven**: For dependency management and building the project.
- **JWT (JSON Web Tokens)**: For secure, token-based authentication.
- **Spring Boot**: Framework for building Java-based applications.
- **Docker**: 

## API Endpoints

- **POST /users**: Create a new user.
  - Example Request:
    ```bash
    curl -X POST http://localhost:8080/users \
    -H "Content-Type: application/json" \
    -d '{"username": "johndoe", "password": "password123"}'
    ```

- **GET /users/{id}**: Retrieve details of a specific user by ID.
  - Example Request:
    ```bash
    curl -X GET http://localhost:8080/users/1 \
    -H "Authorization: Bearer <JWT_TOKEN>"
    ```

- **PUT /users/{id}**: Update an existing user's details.
- **DELETE /users/{id}**: Delete a user by ID.

## Installation

Follow these steps to set up and run the **Users API** locally.

### Step 1: Clone the Repository

First, download the project repository from GitLab:

```bash
git clone https://gitlab.com/jala-university1/cohort-2/oficial-es-desarrollo-de-software-3-iso-214.ga.t2.24.m2/secci-n-d/grupo-3/javaCapstone.git
```

### Step 2: Switch to the develop Branch

After cloning the repository, change to the develop branch:

```bash
cd javaCapstone
git checkout develop
```

### Step 3: Set Up MySQL Database in Docker

Run the following command to start a MySQL container using Docker:

```bash
docker run -d --name sd3db -e MYSQL_ROOT_PASSWORD=sd5 -p 3307:3306 mysql
```

### Step 4: Access the MySQL Container

Once the MySQL container is running, enter the container using the command below:

```bash
docker exec -it sd3db mysql -u root -p
```

### Step 5: Create the Database and Users Table

Now, create the database and users table by running the following SQL commands inside the MySQL prompt:

```bash
CREATE DATABASE sd3;
USE sd3;

CREATE TABLE `users` (
  `id` VARCHAR(36) NOT NULL,
  `name` VARCHAR(200) NOT NULL,
  `login` VARCHAR(20) NOT NULL,
  `password` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE
);
```

## Usage

Once the **Users API** is set up and running, you can interact with it through the following steps.

### Step 1: Start the Application

After setting up the database and building the application, you can start the application by running:

```bash
./grandle run
```

## Authentication

All API requests must include a valid **JWT** token in the `Authorization` header. Tokens can be obtained via the **/auth/login** endpoint by providing valid login credentials (username and password). 

### Example Authentication Request:
```bash
curl -X POST http://localhost:8080/auth/login \
-H "Content-Type: application/json" \
-d '{"username": "user@example.com", "password": "password123"}'
```

### Example Authentication Request:

```bash
POST /auth/login
{
  "username": "user@example.com",
  "password": "password123"
}
```

### Folder Structure
The folder structure follows the principles of Clean Architecture:
```plaintext
src/
├── main/
│   ├── java/
│   │   ├── com/
│   │   │   ├── example/
│   │   │   │   ├── domain/                   # Business logic and models layer
│   │   │   │   │   ├── model/                # Domain models
│   │   │   │   │   │   ├── modelname/        # Domain name
│   │   │   │   │   │   │   ├── ModelRecord/  # Model class
│   │   │   │   │   │   │   ├── ModelRepository/ # Repository interface
│   │   │   │   │   ├── service/              # Use cases / business logic
│   │   │   │   ├── infrastructure/           # Frameworks and infrastructure layer
│   │   │   │   │   ├── drivenadapters/       # Database connections
│   │   │   │   │   │   ├── modelname/        # Entity representation in DB and model
│   │   │   │   │   │   │   ├── ModelAdapter/ # Adapter class between repository and service
│   │   │   │   │   │   │   ├── ModelRepository/ # Repository interface
│   │   │   │   │   │   │   ├── ModelData/    # Model representation as DB entity
│   │   │   │   │   ├── entrypoints/          # RESTful controllers and DTOs
│   │   │   │   │   │   ├── dto/              # DTOs used between adapters and controllers
│   │   │   │   │   │   ├── controllers REST/ # RESTful controllers
│   └── resources/
│       ├── application.xml                   # Repository and other resource configurations


```

## Running Tests

Run the following command to execute the unit tests:

```bash
mvn test
```
