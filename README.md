# Smart Learning Assistant Service

Smart Learning Assistant Service is an AI-driven Q&A microservice that provides instant, personalized answers to queries. Built with Spring Boot and following Clean Architecture principles, this service is designed to be efficient, scalable, and maintainable in modern microservices ecosystems.

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Technologies](#technologies)
- [Project Structure](#project-structure)
- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Installation](#installation)
  - [Configuration](#configuration)
  - [Running the Application](#running-the-application)
- [API Documentation](#api-documentation)
- [Testing](#testing)
- [Load Balancing & Microservices](#load-balancing--microservices)
- [Dockerization](#dockerization)
- [Contributing](#contributing)
- [License](#license)

## Overview

Smart Learning Assistant Service is an AI-driven Q&A microservice that uses advanced natural language processing to deliver real-time, personalized answers. The project leverages reactive programming with Reactor, integrates with PostgreSQL for persistence, and is designed for a microservices architecture using Spring Cloud (Eureka, OpenFeign) for service discovery and inter-service communication.

## Features

- **Instant Q&A Responses:** Provides immediate responses using AI-driven processing.
- **Reactive Streaming:** Supports real-time streaming of responses with Reactor's Flux.
- **Clean Architecture:** Separates concerns into domain, application, infrastructure, and presentation layers.
- **Persistence:** Saves chat history and user feedback in a PostgreSQL database.
- **Microservices Ready:** Integrates with Spring Cloud Eureka and OpenFeign.
- **API Documentation:** Automatically generated API docs using SpringDoc (Swagger UI).

## APIs
![Project Screenshot](https://raw.githubusercontent.com/yusefellban/smart-learning-assistant-service/refs/heads/main/images/Screenshot%202025-03-18%20051254.png)



## Technologies

- **Java 17**
- **Spring Boot 3.4.3**
- **Spring Cloud (Eureka, OpenFeign)**
- **Spring AI Ollama Starter**
- **Spring Data JPA**
- **PostgreSQL**
- **Reactor (Flux/Mono)**
- **Maven**

## Project Structure

The project follows Clean Architecture principles:

- **Domain Layer:** Contains business models, repositories, and use cases.
- **Application Layer:** Contains DTOs and service classes that orchestrate business logic.
- **Infrastructure Layer:** Contains database entities, repository implementations, and external API client configurations.
- **Presentation Layer:** Contains REST controllers to handle incoming HTTP requests.

## Getting Started

### Prerequisites

- **Java 17**
- **Maven**
- **PostgreSQL** (or any other configured relational database)
- (Optional) **Docker** if you plan to containerize the application

### Installation

1. **Clone the Repository:**
   ```bash
   git clone https://github.com/your-username/smart-learning-assistant-service.git
   cd smart-learning-assistant-service
   ```

2. **Build the Project:**
   ```bash
   mvn clean install
   ```

### Configuration

Configure your database and other settings in the `src/main/resources/application.properties` (or `application.yml`) file. For example:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/your_database
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
```

### Running the Application

Start the application using:
```bash
mvn spring-boot:run
```
The service will start on the default port (e.g., 8080). You can access it at `http://localhost:8080`.

## API Documentation

The API documentation is automatically generated and available via Swagger UI.  
Access it at:  
```
http://localhost:8080/swagger-ui.html
```

## Testing

Unit and integration tests are written using JUnit 5, Mockito, and Reactor Test.  
Run the tests with:
```bash
mvn test
```

## Load Balancing & Microservices

This service is designed to work in a microservices ecosystem:
- **Service Discovery:** Uses Spring Cloud Eureka for service registration and discovery.
- **Client-Side Load Balancing:** Integrates with Spring Cloud LoadBalancer (or can use `@LoadBalanced` RestTemplate/WebClient) for balanced inter-service calls.
- **OpenFeign:** Simplifies HTTP API calls between services.

## Dockerization

To containerize the application:

1. **Create a Dockerfile:**
   ```dockerfile
   FROM openjdk:17
   WORKDIR /app
   COPY target/smart-learning-assistant-service.jar app.jar
   CMD ["java", "-jar", "app.jar"]
   ```

2. **Build the Docker Image:**
   ```bash
   docker build -t smart-learning-assistant-service .
   ```

3. **Run the Docker Container:**
   ```bash
   docker run -p 8080:8080 smart-learning-assistant-service
   ```

## Contributing

Contributions are welcome! Please open issues or submit pull requests with improvements or fixes.


