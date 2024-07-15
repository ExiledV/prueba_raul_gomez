# EsPublico Technical Test - Microservices Architecture

## Overview

This project demonstrates a microservices architecture using Spring Boot, designed as part of a technical test for EsPublico. The architecture includes various components such as PostgreSQL, Eureka Server, Spring Cloud Reactive Gateway, JDBC, Spring Actuator, Spring MVC, Java 17, REST API, and the OpenCSV library.

## Components

1. **PostgreSQL**: Relational database management system used for data storage.
2. **Eureka Server**: Service registry for locating microservices.
3. **Spring Cloud Reactive Gateway**: Handles the routing of requests to microservices.
4. **JDBC**: Provides database connectivity.
5. **Spring Actuator**: Adds production-ready features such as monitoring and metrics.
6. **Spring MVC**: Web framework used for creating RESTful web services.
7. **Java 17**: The latest long-term support (LTS) version of Java.
8. **REST API**: Interface for interacting with the microservices.
9. **OpenCSV**: Library used for handling CSV files.

## Architecture

The architecture consists of the following services:

1. **Eureka Server**: Central service registry for managing and locating microservices.
2. **Gateway Service**: Routes external requests to appropriate microservices.
3. **Microservices**: Independent services that perform specific business functions, each connected to a PostgreSQL database.

## Prerequisites

- Java 17
- Maven
- Docker (optional, for containerization)

## Setup Instructions

### 1. Clone the repository

```bash
git clone https://github.com/espublico/technical-test.git
cd technical-test
```

### 2. Run the project with docker

```bash
docker compose up --build
```

### 2. Run the project with maven (if you can't with docker)

Execute this command line, first in eureka server, second in gateway and third in documents microservice.

```bash
cd gateway-prueba-espublico
mvn spring-boot:run
```

### 3. Verify Services

Access the Eureka Dashboard at http://localhost:8761 to verify that all microservices are registered and running.

### 4. Usage
REST API
Each microservice exposes its REST API. You can use tools like curl or Postman to interact with the endpoints. If you use postman, you can import the endpoints in the postman folder.


### 5. Monitoring
Spring Actuator endpoints are available for monitoring and health checks. Access them at /actuator (http://localhost:8080/actuator/health)