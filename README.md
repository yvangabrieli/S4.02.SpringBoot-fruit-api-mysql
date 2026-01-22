# 🍎 Fruit API MySQL - Level 2

REST API for managing fruit stock with providers using MySQL database.

## 📋 Table of Contents

- [Description](#-description)
- [Requirements](#-requirements)
- [Technologies](#-technologies)
- [Project Structure](#-project-structure)
- [Setup & Installation](#-setup--installation)
- [Running the Application](#-running-the-application)
- [API Endpoints](#-api-endpoints)
- [Testing](#-testing)
- [Docker](#-docker)
- [Database Schema](#-database-schema)
- [Development Process](#-development-process)
- [Assignment Details](#-assignment-details)

---

## 📝 Description

This project is a **Spring Boot REST API** for managing a fruit inventory system with providers. It allows you to:

- **Manage Providers**: Create, read, update, and delete fruit suppliers
- **Manage Fruits**: Track fruit stock with associated providers
- **Full CRUD Operations**: Complete Create, Read, Update, Delete functionality
- **Validation & Error Handling**: Input validation and global exception handling
- **Database Integration**: MySQL for production, H2 for testing
- **Containerized**: Docker + Docker Compose

---

## 🎯 Requirements

### Functional Requirements

#### Providers
- Register new providers with `name` and `country`
- List all registered providers
- Update provider information
- Delete providers (only if they have no associated fruits)

#### Fruits
- Add fruits with `name`, `weightInKilos`, and `providerId`
- All fruits must have an associated provider
- List all fruits
- Get fruit details by ID
- Update fruit information (including changing provider)
- Delete fruits

### Non-Functional Requirements
- Proper HTTP status codes (200, 201, 204, 400, 404, 409)
- Input validation using Jakarta Bean Validation
- Global exception handling
- RESTful API design
- Persistent data with MySQL
- Containerized with Docker

---

## 🛠️ Technologies

- **Java 21**
- **Spring Boot 3.5.9**
- **Spring Web** (REST Controllers)
- **Spring Data JPA** (ORM)
- **Hibernate** (JPA implementation)
- **MySQL 8.1** (Production DB)
- **H2 Database** (Testing DB)
- **Jakarta Bean Validation**
- **Lombok**
- **JUnit 5 & Mockito**
- **Docker & Docker Compose**
- **Maven** (Build tool)

---

## 📁 Project Structure

fruit-api-mysql/

├── src/

│ ├── main/

│ │ ├── java/cat/itacademy/s04/t02/n02/fruit/

│ │ │ ├── controller/

│ │ │ │ ├── FruitController.java

│ │ │ │ └── ProviderController.java

│ │ │ ├── service/

│ │ │ │ ├── FruitService.java

│ │ │ │ ├── FruitServiceImpl.java

│ │ │ │ ├── ProviderService.java

│ │ │ │ └── ProviderServiceImpl.java

│ │ │ ├── repository/

│ │ │ │ ├── FruitRepository.java

│ │ │ │ └── ProviderRepository.java

│ │ │ ├── model/

│ │ │ │ ├── Fruit.java

│ │ │ │ └── Provider.java

│ │ │ ├── dto/

│ │ │ │ ├── FruitRequestDTO.java

│ │ │ │ ├── FruitResponseDTO.java

│ │ │ │ ├── ProviderRequestDTO.java

│ │ │ │ └── ProviderResponseDTO.java

│ │ │ ├── mapper/

│ │ │ │ ├── FruitMapper.java

│ │ │ │ └── ProviderMapper.java

│ │ │ ├── validator/

│ │ │ │ ├── FruitValidator.java

│ │ │ │ └── ProviderValidator.java

│ │ │ ├── exception/

│ │ │ │ ├── BusinessRuleException.java

│ │ │ │ ├── DuplicateResourceException.java

│ │ │ │ ├── FruitNotFoundException.java

│ │ │ │ ├── ProviderNotFoundException.java

│ │ │ │ ├── ValidationException.java

│ │ │ │ └── GlobalExceptionHandler.java

│ │ │ └── FruitApiMysqlApplication.java

│ │ └── resources/

│ │ └── application.properties

├── test/java/cat/itacademy/s04/t02/n02/fruit/

│ ├── controller/

│ │ ├── FruitControllerTest.java

│ │ └── ProviderControllerTest.java

│ ├── service/

│ │ ├── FruitServiceTest.java

│ │ └── ProviderServiceTest.java

│ └── FruitApiMysqlApplicationTests.java

├── Dockerfile

├── docker-compose.yml

├── pom.xml

├── mvnw

├── mvnw.cmd

└── README.md

---

## ⚙️ Setup & Installation

### Prerequisites

- Java 21
- Maven 3.8+
- Docker & Docker Compose
- MySQL (via Docker)

### 1. Clone the repository

git clone <repository-url>
cd fruit-api-mysql

---

## ⚙️ Setup & Installation

### 1. Configure `application.properties`
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/fruitdb?useSSL=false&serverTimezone=UTC
spring.datasource.username=user
spring.datasource.password=userpassword

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect
spring.application.name=fruit-api-mysql

---

Start MySQL with Docker
docker-compose up -d

Verify container is running:
docker ps

---

🚀 Running the Application

Using Maven
./mvnw spring-boot:run
The API will be available at: http://localhost:8080

---

🌐 API Endpoints

Providers
POST /providers – Create a provider

GET /providers – List all providers

GET /providers/{id} – Get provider by ID

PUT /providers/{id} – Update provider

DELETE /providers/{id} – Delete provider

---

Fruits
POST /fruits – Create a fruit

GET /fruits – List all fruits

GET /fruits/{id} – Get fruit by ID

PUT /fruits/{id} – Update fruit

DELETE /fruits/{id} – Delete fruit

⚠️ Note: When creating a fruit, you must provide an existing providerId.

---

🐳 Docker
Build & Run
docker-compose up --build -d

Logs
docker-compose logs -f

Stop containers
docker-compose down

---

🗄️ Database Schema
providers

CREATE TABLE providers (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL UNIQUE,
  country VARCHAR(255) NOT NULL
);

---

fruits

CREATE TABLE fruits (
  id BIGINT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  weight_in_kilos INT NOT NULL,
  provider_id BIGINT NOT NULL,
  FOREIGN KEY (provider_id) REFERENCES providers(id)
);

---

🧪 Testing
Run all tests:
./mvnw test

---

🔄 Development Process
Write failing controller tests (MockMvc)

Implement controller and mappers

Write failing service tests (Mockito)

Implement service logic

Integrate with JPA repositories

Run end-to-end integration tests

Refactor and commit

---

📚 Assignment Details
Level: 2 – MySQL integration

Goal: CRUD operations for fruits and providers with proper validation

Exercise Completion: All provider and fruit endpoints implemented with DTOs, validation, service layer, exception handling, and tests.

---



