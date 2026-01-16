# Apartments Spring Boot Project

## Technology Stack

- **Java 17** with **Spring Boot 3.2.1**
- **PostgreSQL 16.1** database with **Flyway** migrations
- **JWT-based authentication** using Spring Security
- **Gradle 8.5** build system
- **Spring Data JPA** with Hibernate ORM
- **TestContainers** for integration testing
- **JUnit 5** for unit testing

## Core Features

### Apartment Management
- CRUD operations for apartment listings
- Advanced filtering by bedrooms, bathrooms, area, price, parking, and description
- Two filter implementations: JPA Specifications and custom Criteria API
- Input validation for apartment data

### Authentication & Authorization
- User registration and login with JWT tokens (24-hour validity)
- Role-based access control (USER and ADMIN roles)
- Token storage in database for logout capability
- Admin-only endpoints for creating/editing apartments
- BCrypt password encryption

### API Features
- RESTful design
- CORS enabled for frontend (localhost:3000)
- Stateless session management
- Comprehensive endpoint logging

## Database setup

This project comes with a Docker compose file that allows you to create a container with a Postgres database. All the database migration files in the resources folder allows you to have everything you need to have a functional and upgraded database ready to run your application.

### Steps

After you clone this repository, you must:

1. Open the terminal and change directory to app by typing `cd app`
2. Type `docker compose up` to start (or restart) your database containers. This will start both Postgres development and test containers. Ps. Run `docker-compose up -d` to run in detached mode.
3. With your containers running, run your Java application. If using IntelliJ or Eclipse, you can just hit the play button. _Remember to add Java related environment variables, set you SDK, and anything else._
4. You are all set.

## Accessing the API

This application is configured to show every end point available in the logs. Here is a list of all of them:

### Auth Operations

#### Post: /api/auth/login

- **Description:** Authorize the User in the app
- **Return:** Authentication Response, with a Token and a Role
- **Exception:** NoSuchElementException
- **Parameters:** User

#### Post: /api/auth/register

- **Description:** Register a new User in the application
- **Return:** Authentication Response, with a Token and a Role
- **Exception:** IllegalArgumentException, RuntimeException
- **Parameters:** User

#### Post: /api/auth/logout

- **Description:** Logs out a User
- **Return:** None
- **Parameters:** The Authentication Token

### Apartment Operations

#### Get: /api/apartments

- **Return:** A List of Apartments
- **Parameters:** Apartment Filter and a String with the order: Default: "ASC"

#### Get: /api/users/role

- **Description:** Retrieve a sorted list of apartments given the filters applied. It uses Specifications.
- **Return:** A Sorted List of Apartments
- **Parameters:** Apartment Filter and a String with the order: Default: "ASC"

#### Get: /api/apartments-old

- **Description:** Retrieve a sorted list of apartments given the filters applied. It uses Custom Filters.
- **Return:** A Sorted List of Apartments
- **Parameters:** Apartment Filter and a String with the order: Default: "ASC"

#### Get: /api/apartments/{id}

- **Description:** Retrieve an apartment based on its ID.
- **Exceptions:**: ObjectNotFoundException when the ID does not exist.
- **Return:** One Apartment
- **Parameters:** A String containing an ID.

#### Post: /api/apartments/add

- **Description:** Add an apartment.
- **Return:** One Apartment
- **Parameters:** An Object with the Apartment's data.

#### Put: /api/apartments/{id}

- **Description:** Edit an apartment based on its ID.
- **Return:** One Apartment
- **Parameters:** The Apartment ID and an Object with the Apartment's data.

#### Delete: /api/apartment/{id}

- **Description:** Delete an apartment based on its ID.
- **Return:** The Apartment's ID
- **Parameters:** A String containing an ID.

### User Operations

#### Get: /api/users/user

- **Description:** Retrieve a User based on the Token. The Token is obtained through the Security Context.
- **Return:** The User
- **Parameters:** None

#### GET /api/users/role

- **Description:** Retrieve the User's Role based on the Token. The Token is obtained through the Security Context.
- **Return:** The User's Role
- **Parameters:** None

#### Get: /api/users/{id}

- **Description:** Retrieve the User based on the User ID.
- **Return:** The User
- **Parameters:** A ID number

### Jwt Configuration

Source: https://www.youtube.com/watch?v=RnZmeczS_DI

- Create JWT filter
- User class implementing UserDetails
- User service implementing UserDetailsService -> This will be used in the Security configuration
- Role Enum class
- User Repository must implement findByUsername -> this will be used in the `authenticate` function
- Configure Security to use the filter, the session manager, authorize http requests
- Authentication Controller must implement `login` and `register` functions -> these will not be authenticated
- Authentication Controller must implement non authentication functions

## Key Endpoints

| Endpoint | Method | Access | Description |
|----------|--------|--------|-------------|
| `/api/apartments` | GET | Public | List apartments with filters |
| `/api/apartments/{id}` | GET | Public | Get single apartment |
| `/api/apartments/add` | POST | ADMIN | Create apartment |
| `/api/apartments/{id}` | PUT | ADMIN | Edit apartment |
| `/api/apartment/{id}` | DELETE | Authenticated | Delete apartment |
| `/api/auth/login` | POST | Public | User login |
| `/api/auth/register` | POST | Public | User registration |
| `/api/auth/logout` | POST | Authenticated | User logout |
| `/api/users/user` | GET | Authenticated | Get current user |
| `/api/users/role` | GET | Authenticated | Get current user's role |
| `/api/users/{id}` | GET | Authenticated | Get user by ID |

## Project Structure

```
app/src/main/java/com/rentals/apartment/
├── ApartmentsApplication.java   # Main entry point
├── controller/                  # REST endpoints
│   ├── ApartmentsController.java
│   ├── AuthController.java
│   └── UserController.java
├── service/                     # Business logic
│   ├── ApartmentService.java
│   ├── AuthService.java
│   └── JwtService.java
├── domain/                      # Entities and DTOs
│   ├── ApartmentEntity.java
│   ├── ApartmentDTO.java
│   ├── ApartmentFilter.java
│   ├── UserEntity.java
│   ├── UserDTO.java
│   ├── TokenEntity.java
│   └── Role.java
├── repositories/                # Data access layer
│   ├── ApartmentRepository.java
│   ├── UserRepository.java
│   └── TokenRepository.java
├── filter/                      # JWT authentication filter
│   └── JwtAuthFilter.java
└── config/                      # Security configuration
    └── SecurityConfiguration.java
```

## Database Schema

### apartments
| Column | Type | Description |
|--------|------|-------------|
| id | TEXT (PK) | UUID identifier |
| number_of_bedrooms | INTEGER | Number of bedrooms |
| number_of_bathrooms | INTEGER | Number of bathrooms |
| area | NUMERIC | Area in square units |
| has_parking | BOOLEAN | Parking availability |
| price | NUMERIC | Rental price |
| description | TEXT | Apartment description |

### users
| Column | Type | Description |
|--------|------|-------------|
| id | INTEGER (PK) | Auto-generated ID |
| username | TEXT (UNIQUE) | User's username |
| password | TEXT | BCrypt encrypted password |
| first_name | TEXT | User's first name |
| last_name | TEXT | User's last name |
| email | TEXT | User's email |
| role | INTEGER | 1=USER, 2=ADMIN |

### tokens
| Column | Type | Description |
|--------|------|-------------|
| id | INTEGER (PK) | Auto-generated ID |
| token | TEXT | JWT token string |
| user_id | INTEGER (FK) | Reference to users table |
| created_at | TIMESTAMP | Token creation time |
| expired_at | TIMESTAMP | Token expiration time |
