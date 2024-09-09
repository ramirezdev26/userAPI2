# Users API - README

## Overview

The **Users API** is a modern CRUD API for user management, implemented in **Java** as part of the Capstone Project. This API is designed to integrate seamlessly with legacy systems while providing enhanced functionality and security. The API supports operations such as creating, reading, updating, and deleting user data, all of which are accessible via a secure, token-based authentication system using **JWT (JSON Web Tokens)**.

## Key Features

- **CRUD Operations**: Full support for Create, Read, Update, and Delete operations on user data.
- **Legacy Data Compatibility**: The API is designed to be consistent with the existing legacy systems in terms of data structure and behavior.
- **Security**: JWT-based authentication ensures secure access to the API endpoints, requiring authorized access using login/password credentials.
- **API Client Simulation**: A simple API client is provided to simulate CRUD operations for multiple users, facilitating easy testing and validation.
- **Java Implementation**: The API is built using **Java** and follows the principles of Clean Architecture for scalability, modularity, and maintainability.

## Requirements

- **Java**: The API must be implemented in Java, as per technical requirements.
- **Security**: All endpoints are secured using JWT for authorized access.
- **API Client**: A simple API client will be provided for testing and validating the CRUD operations.

## API Endpoints

- **POST /users**: Create a new user.
- **GET /users/{id}**: Retrieve details of a specific user by ID.
- **PUT /users/{id}**: Update an existing user's details.
- **DELETE /users/{id}**: Delete a user by ID.

## Authentication

All API requests must include a valid **JWT** token in the `Authorization` header. The token can be obtained by sending valid login credentials (username and password) to the **/auth/login** endpoint.

- **POST /auth/login**: Authenticates a user and returns a JWT token.

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
