# Smart Learning Assistant Service

Smart Learning Assistant Service is an AI-driven Q&A microservice that provides instant, personalized answers to queries. Built with Spring Boot and following Clean Architecture principles, this service is designed to be efficient, scalable, and maintainable in modern microservices ecosystems.

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Technologies](#technologies)
- [Project Structure](#project-structure)
- [DTOs](#dtos)
- [Exceptions](#exceptions)
- [Mapper](#mapper)
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

Smart Learning Assistant Service is an AI-powered Q&A microservice that leverages advanced natural language processing to deliver real-time, personalized answers. The project utilizes reactive programming with Reactor, integrates with PostgreSQL for persistence, and is built for a microservices architecture using Spring Cloud (Eureka, OpenFeign) for service discovery and inter-service communication.

## Features

- **Instant Q&A Responses:** Provides rapid answers using AI-driven processing.
- **Reactive Streaming:** Supports real-time streaming of responses with Reactor's Flux.
- **Clean Architecture:** Separates concerns into domain, application, infrastructure, and presentation layers.
- **Persistence:** Saves chat history and user feedback in a PostgreSQL database.
- **Microservices Ready:** Integrates with Spring Cloud Eureka and OpenFeign.
- **API Documentation:** Automatically generated API documentation via SpringDoc (Swagger UI).

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

The project follows Clean Architecture principles and is organized into several layers:

- **Domain Layer:** Contains business models, repositories, and use cases.
- **Application Layer:** Contains DTOs and service classes that orchestrate business logic.
- **Infrastructure Layer:** Contains database entities, repository implementations, and external API client configurations.
- **Presentation Layer:** Contains REST controllers to handle incoming HTTP requests.

## IOC contanier
![Project Screenshot](https://raw.githubusercontent.com/yusefellban/smart-learning-assistant-service/refs/heads/main/images/Screenshot%202025-03-18%20210146.png)


## DTOs

This layer defines Data Transfer Objects (DTOs) that facilitate communication between different layers and external interfaces. Key DTOs include:
- **AskResponse:** Represents the response for query requests.
- **ChatHistoryResponse:** Converts chat history data into a format suitable for client responses.
- **FeedbackResponse:** Represents user feedback data.

DTOs ensure that business logic remains decoupled from the data representations used in external communications.

## Exceptions

The project implements advanced error handling using custom exceptions:
- **AgentNotRunningException:** Thrown when the AI agent is not active.
- **UserNotFoundException:** Thrown when the requested user cannot be found.
- Additionally, a **GlobalExceptionHandler** is implemented to catch and process exceptions uniformly, providing detailed and consistent error messages to clients.

This approach improves the service's resilience and user experience by managing errors effectively.

## Mapper

Mappers are used to convert data between different representations across various layers:
- **ChatToDomainMapper:** Converts chat history data between the Presentation (DTO) layer and the Domain layer.
- **FeedbackToDomainMapper:** Transforms user feedback data into the appropriate domain model.

Using mappers ensures separation of concerns, enabling each layer to manage data in its preferred format without intermingling responsibilities.

## Getting Started

### Prerequisites

- **Java 17**
- **Maven**
- **PostgreSQL** (or any other configured relational database)
- (Optional) **Docker** if you plan to containerize the application

### Installation

1. **Clone the Repository:**
   ```bash
   git clone https://github.com/yusefellban/smart-learning-assistant-service.git
   cd smart-learning-assistant-service
   ```

2. **Build the Project:**
   ```bash
   mvn clean install
   ```

### Configuration

Update your database and other configurations in the `src/main/resources/application.properties`file. For example:
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
The service will start on the default port (typically 8080) and can be accessed at:
```
http://localhost:8080
```

## API Documentation

### APIs
![Project Screenshot](https://raw.githubusercontent.com/yusefellban/smart-learning-assistant-service/refs/heads/main/images/Screenshot%202025-03-18%20051254.png)


API documentation is automatically generated via Swagger UI.  
Access it at:
```
http://localhost:8080/swagger-ui.html
```

## Testing

Unit and integration tests are written using JUnit 5, Mockito, and Reactor Test.  
To run the tests, execute:
```bash
mvn test
```

## Load Balancing & Microservices

The service is designed for a microservices architecture:
- **Service Discovery:** Uses Spring Cloud Eureka for service registration and discovery.
- **Client-Side Load Balancing:** Integrates with Spring Cloud LoadBalancer (or uses `@LoadBalanced` with RestTemplate/WebClient) for balanced inter-service communication.
- **OpenFeign:** Simplifies HTTP API calls between microservices.


## Contributing

Contributions are welcome! Please open issues or submit pull requests with improvements or fixes.

