# Food Delivery System

A backend Food Delivery System built using Spring Boot, PostgreSQL, Docker, JPA, Hibernate and Lombok.

The project follows a **Modular Monolith architecture**, where different business modules are maintained inside a single Spring Boot application.

## Features

- User registration
- User roles
- Address management
- Restaurant management
- Menu item management
- Order creation
- Order item management
- Delivery assignment
- Delivery status management
- Centralized exception handling
- Standard API responses
- Password validation
- PostgreSQL database
- Docker-based PostgreSQL setup

## Technologies Used

- Java
- Spring Boot
- Spring Data JPA
- Hibernate
- PostgreSQL
- Docker
- Maven
- Lombok
- REST API

## Project Architecture

The project follows a Modular Monolith architecture.

```text
src/main/java/com/rohit/food_delivery_system
│
├── auth
│   ├── controller
│   ├── dto
│   ├── model
│   ├── repository
│   └── service
│
├── restaurant
│   ├── controller
│   ├── dto
│   ├── model
│   ├── repository
│   └── service
│
├── order
│   ├── controller
│   ├── dto
│   ├── model
│   ├── repository
│   └── service
│
├── delivery
│   ├── controller
│   ├── dto
│   ├── model
│   ├── repository
│   └── service
│
├── common
│   ├── exception
│   └── response
│
└── FoodDeliverySystemApplication.java
