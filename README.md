# Bank Account Kata

This project is an implementation of the Bank Account Kata, designed with a focus on clean architecture, domain-driven design (DDD) principles, and scalability.

## Table of Contents

- [Bank Account Kata](#bank-account-kata)
  - [Table of Contents](#table-of-contents)
  - [Architecture Overview](#architecture-overview)
  - [Key Features](#key-features)
  - [Project Structure](#project-structure)
  - [Getting Started](#getting-started)
    - [Prerequisites](#prerequisites)
    - [Running Tests](#running-tests)
  - [Scalability Features](#scalability-features)

## Architecture Overview

The application follows a layered architecture inspired by Domain-Driven Design, promoting separation of concerns and maintainability:

1.  **Domain Layer (`src/main/java/bankkata/domain`)**:
    *   Contains the core business logic, entities (e.g., `Account`, `Money`, `Operation`), and value objects.
    *   Enforces business rules and invariants.
    *   Includes domain services (e.g., `AccountService`, `StatementService`) that orchestrate domain-specific operations.
    *   Defines repository interfaces (`AccountRepository`) for data persistence, abstracting the storage mechanism.

2.  **Application Layer (`src/main/java/bankkata/application`)**:
    *   Orchestrates application use cases (e.g., deposit, withdrawal, view statement).
    *   Acts as a mediator between the Domain Layer and the Infrastructure Layer.
    *   Uses Data Transfer Objects (DTOs) (e.g., `OperationDto`, `StatementDto`) for communication with the outside world (e.g., UI, API clients).
    *   Handles application-specific exceptions (`ApplicationException`).

3.  **Infrastructure Layer (`src/main/java/bankkata/infrastructure`)**:
    *   Provides concrete implementations for interfaces defined in the Domain and Application layers.
    *   Includes repository implementations (e.g., `InMemoryAccountRepository`).
    *   Manages external concerns like database interactions, external service integrations (e.g., `DefaultClock`), and UI components (e.g., statement formatters like `TextStatementFormatter`).

## Key Features

*   **Account Operations**: Securely deposit and withdraw funds from an account.
*   **Transaction Validation**: Ensures operations are valid (e.g., sufficient funds for withdrawal).
*   **Account Statement**: Generate a detailed statement of all transactions, including dates, amounts, and running balances.
*   **Clean Architecture**: Enforces a clear separation of concerns, making the system easier to understand, maintain, and test.
*   **Scalability by Design**: The architecture is structured to support future growth and increased transaction volume.
*   **Testability**: Components are designed for isolated unit testing and integration testing.

## Project Structure

The project is organized as follows:

```text
.
├── pom.xml                # Maven project configuration
├── README.md              # This file
└── src/
    ├── main/
    │   └── java/
    │       └── bankkata/
    │           ├── App.java                    # Main application entry point
    │           ├── application/                # Application layer (use cases, DTOs)
    │           │   ├── dto/
    │           │   ├── exception/
    │           │   └── service/
    │           ├── domain/                     # Domain layer (entities, services, repositories)
    │           │   ├── exception/
    │           │   ├── model/
    │           │   ├── repository/
    │           │   └── service/
    │           ├── infrastructure/             # Infrastructure layer (implementations, external services)
    │           │   ├── clock/
    │           │   ├── formatter/
    │           │   └── repository/
    │           └── util/                       # Utility classes
    └── test/
        └── java/
            └── bankkata/                   # Unit and integration tests
                ├── AcceptanceTest.java
                ├── BankingIntegrationTest.java
                ├── domain/
                ├── infrastructure/
                └── util/
```

## Getting Started

### Prerequisites

*   Java Development Kit (JDK) 17 or higher
*   Apache Maven 3.6.0 or higher


### Running Tests

To execute the unit and integration tests:

```bash
mvn test
```

Test results will be available in the `target/surefire-reports` directory.

## Scalability Features

The architecture is designed with scalability in mind:

1.  **Stateless Domain Logic**: The core domain logic is largely stateless, which facilitates horizontal scaling by allowing multiple instances of the application to run behind a load balancer.
2.  **Decoupled Components**: The separation between layers allows for independent scaling of different parts of the system. For example, the data persistence mechanism can be scaled independently of the application logic.
3.  **Extensibility**: Adding new features or operation types can be done with minimal impact on existing code, primarily by adding new domain logic and application services.
4.  **Optimized for Performance**: While this is a Kata, the principles allow for the introduction of performance optimizations (e.g., caching, optimized database queries) at the infrastructure layer without altering the core business logic.
5.  **Clear Boundaries for Testability**: Well-defined interfaces and dependency injection make components easily testable in isolation, ensuring reliability as the system grows.

---
