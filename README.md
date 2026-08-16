# 🛒 QuickCart – E-Commerce Microservices

QuickCart is a **production-oriented E-Commerce backend application** built using **Java 17, Spring Boot, and Microservices Architecture**.

The project demonstrates real-world backend concepts including **JWT authentication, API Gateway security, service discovery, synchronous REST communication, asynchronous event-driven communication using Apache Kafka, inventory management, payment processing, email notifications, and distributed tracing**.

---

## 📌 Project Overview

QuickCart follows a **Microservices Architecture** where each business capability is implemented as an independent service.

The system is designed to demonstrate how modern e-commerce applications can be built using:

* Java 17
* Spring Boot
* Spring Cloud
* Spring Cloud Gateway
* Netflix Eureka
* Apache Kafka
* OpenFeign
* Spring Security
* JWT
* MongoDB
* MySQL
* Razorpay
* Zipkin
* Docker
* Swagger / OpenAPI

---

# 🏗️ System Architecture

```text
                         ┌─────────────────────┐
                         │       Client        │
                         │  Web / Postman      │
                         └──────────┬──────────┘
                                    │
                                    ▼
                         ┌─────────────────────┐
                         │    API Gateway      │
                         │ Spring Cloud        │
                         │ Gateway + JWT       │
                         └──────────┬──────────┘
                                    │
                    ┌───────────────┼────────────────┐
                    │               │                │
                    ▼               ▼                ▼
             ┌────────────┐  ┌────────────┐  ┌────────────┐
             │   User     │  │  Product   │  │   Order    │
             │  Service   │  │  Service   │  │  Service   │
             └─────┬──────┘  └─────┬──────┘  └─────┬──────┘
                   │               │                │
                   ▼               ▼                │
              ┌─────────┐      ┌─────────┐          │
              │ MongoDB │      │  MySQL  │          │
              └─────────┘      └─────────┘          │
                                                    │
                                      ┌─────────────┼──────────────┐
                                      │             │              │
                                      ▼             ▼              ▼
                              ┌────────────┐ ┌────────────┐ ┌──────────────┐
                              │ Inventory  │ │  Payment   │ │ Notification │
                              │  Service   │ │  Service   │ │   Service    │
                              └────────────┘ └────────────┘ └──────────────┘
                                      │             │              │
                                      └─────────────┼──────────────┘
                                                    ▼
                                             ┌─────────────┐
                                             │    Kafka    │
                                             │ Event Bus   │
                                             └─────────────┘

                         ┌─────────────────────┐
                         │   Eureka Server     │
                         │ Service Discovery   │
                         └─────────────────────┘

                         ┌─────────────────────┐
                         │      Zipkin         │
                         │ Distributed Tracing │
                         └─────────────────────┘
```

---

# 🧩 Microservices

| Service              | Responsibility                      | Database / Technology |
| -------------------- | ----------------------------------- | --------------------- |
| API Gateway          | Routing, JWT validation, security   | Spring Cloud Gateway  |
| Service Registry     | Service discovery                   | Eureka                |
| User Service         | Registration, login, authentication | MongoDB               |
| Product Service      | Product management                  | MySQL                 |
| Inventory Service    | Stock management                    | MySQL                 |
| Order Service        | Order creation and management       | MySQL                 |
| Payment Service      | Payment processing                  | MySQL + Razorpay      |
| Notification Service | Email notifications                 | Kafka + SMTP          |

---

# 🔹 1. API Gateway

The API Gateway acts as the **single entry point** for clients.

### Responsibilities

* Route requests to microservices
* Validate JWT tokens
* Extract user information and roles
* Apply authorization rules
* Prevent direct exposure of internal services
* Provide centralized security

### Example Routes

```text
/api/users/**       → user-service
/api/products/**    → product-service
/api/inventory/**   → inventory-service
/api/orders/**      → order-service
/api/payments/**    → payment-service
```

### Security Flow

```text
Client
   │
   │ JWT Token
   ▼
API Gateway
   │
   ├── Validate JWT
   ├── Extract username
   ├── Extract roles
   └── Authorize request
           │
           ▼
      Target Service
```

Public endpoints such as login and registration can be accessed without authentication.

Protected endpoints require a valid JWT.

---

# 🔐 Authentication & Authorization

QuickCart uses **Spring Security + JWT** for authentication.

### Authentication Flow

```text
Client
   │
   │ POST /api/users/login
   ▼
User Service
   │
   ├── Validate email/password
   ├── BCrypt password verification
   └── Generate JWT
           │
           ▼
        Client
```

For subsequent requests:

```text
Client
   │
   │ Authorization: Bearer <JWT>
   ▼
API Gateway
   │
   ├── Validate Token
   ├── Extract Role
   └── Authorize
        │
        ▼
Microservice
```

### Security Technologies

* Spring Security
* JWT
* BCrypt
* Role-Based Authorization
* Gateway Filter

---

# 🔎 Service Discovery

QuickCart uses **Netflix Eureka** for service discovery.

Instead of hardcoding service addresses:

```text
http://localhost:8082
```

services communicate using logical service names:

```text
http://inventory-service
http://order-service
http://product-service
```

### Eureka Flow

```text
                 ┌──────────────────┐
                 │  Eureka Server   │
                 └────────┬─────────┘
                          │
             ┌────────────┼────────────┐
             │            │            │
             ▼            ▼            ▼
       user-service  order-service  inventory-service
```

Each service:

1. Starts
2. Registers with Eureka
3. Sends heartbeat
4. Can discover other services

This removes tight coupling to fixed host/port combinations.

---

# 🔄 Communication Patterns

QuickCart uses **two major communication patterns**.

## 1. Synchronous Communication

Used when the caller needs an immediate response.

Technology:

**OpenFeign**

Example:

```text
Order Service
      │
      │ OpenFeign
      ▼
Inventory Service
      │
      ▼
 Stock Availability
```

Examples:

* Order Service → Inventory Service
* Payment Service → Order Service
* Order Service → Product Service

### Advantages

* Simple request/response model
* Immediate result
* Easy to implement

### Disadvantages

* Services are temporarily coupled
* Failure of the downstream service can affect the caller
* Network latency affects response time

---

# ⚡ 2. Asynchronous Communication

QuickCart uses **Apache Kafka** for event-driven communication.

Example:

```text
Order Service
      │
      │ Publish Event
      ▼
     Kafka
      │
      ├──────────────► Payment Service
      │
      └──────────────► Notification Service
```

The producer does not need to directly call every consumer.

### Benefits

* Loose coupling
* Better scalability
* Asynchronous processing
* Event-driven architecture
* Consumers can process events independently

---

# 📨 Kafka Event-Driven Architecture

Kafka acts as the **event backbone** of QuickCart.

### Example Events

```text
OrderPlacedEvent
OrderConfirmedEvent
PaymentSuccessEvent
PaymentFailedEvent
InventoryRollbackEvent
```

---

# 🛍️ Order Placement Flow

A typical order workflow is:

```text
Client
  │
  ▼
API Gateway
  │
  ▼
Order Service
  │
  ├──────► Product Service
  │          │
  │          └── Validate Product
  │
  ├──────► Inventory Service
  │          │
  │          └── Check / Reserve Stock
  │
  ▼
Order Created
  │
  ▼
Publish OrderPlacedEvent
  │
  ▼
Kafka
  │
  ▼
Payment Service
  │
  ├── Create Payment
  ├── Process Payment
  └── Update Payment Status
```

---

# 💳 Payment Flow

```text
Order Service
      │
      │ OrderPlacedEvent
      ▼
    Kafka
      │
      ▼
Payment Service
      │
      ├── Validate Order
      ├── Create Payment
      └── Razorpay Integration
             │
             ▼
       Payment Result
```

After payment processing:

```text
Payment Service
      │
      ├── SUCCESS
      │
      │ Publish Event
      ▼
    Kafka
      │
      ├──────────────► Order Service
      │
      └──────────────► Notification Service
```

---

# ❌ Payment Failure & Rollback

Distributed systems require compensation when a later operation fails.

Example:

```text
Order Created
     │
     ▼
Inventory Reserved
     │
     ▼
Payment Failed
     │
     ▼
Rollback Inventory
     │
     ▼
Stock Restored
```

The rollback is a **compensating transaction**.

Example:

```text
Payment Service
      │
      │ Payment Failed
      ▼
Order Service
      │
      ▼
Inventory Service
      │
      ▼
Rollback Inventory
```

This avoids keeping inventory permanently reserved for a failed payment.

---

# 📧 Notification Service

Notification Service consumes events from Kafka.

Example:

```text
Payment Service
      │
      │ PaymentSuccessEvent
      ▼
    Kafka
      │
      ▼
Notification Service
      │
      ▼
SMTP / Gmail
      │
      ▼
Customer Email
```

Notifications can include:

* Order confirmation
* Payment success
* Payment failure
* Order failure
* Other order-related updates

The notification service is asynchronous so email processing does not unnecessarily block the main transaction flow.

---

# 📦 Inventory Management

Inventory Service manages product stock.

Example:

```text
Product SKU: SAM-0c3f2ab3
Available Stock: 10
Requested Quantity: 2

10 - 2 = 8
```

When an order is placed:

```text
Check Stock
    │
    ├── Available → Reserve / Reduce Stock
    │
    └── Not Available → Reject Order
```

If payment subsequently fails:

```text
Rollback
    │
    ▼
Reserved Stock Restored
```

---

# 🗄️ Database Architecture

QuickCart follows a **database-per-service approach** where practical.

### User Service

```text
MongoDB
```

Used for user information and authentication-related data.

Example fields:

```text
id
name
email
password
mobileNo
roles
createdAt
updatedAt
enabled
accountNonLocked
failedAttempts
```

### Other Services

Relational data can be managed using:

```text
MySQL
```

with:

* Spring Data JPA
* Hibernate
* HikariCP

---

# 💰 Razorpay Payment Integration

Payment Service integrates with **Razorpay** for online payment processing.

High-level flow:

```text
Client
   │
   ▼
Order Service
   │
   ▼
Payment Service
   │
   ▼
Razorpay
   │
   ├── Payment Success
   └── Payment Failure
```

Payment status is then propagated through the system using service communication and Kafka events.

---


# 🧰 Technology Stack

## Backend

* Java 17
* Spring Boot
* Spring MVC
* Spring Security
* Spring Data JPA
* Spring Data MongoDB
* Hibernate
* REST APIs

## Microservices

* Spring Cloud Gateway
* Netflix Eureka
* OpenFeign

## Messaging

* Apache Kafka
* Kafka Producer
* Kafka Consumer

## Security

* JWT
* Spring Security
* BCrypt
* Role-Based Authorization

## Databases

* MongoDB
* MySQL

## Payment

* Razorpay

## Development Tools

* IntelliJ IDEA
* Postman
* Git
* GitHub
* Docker
* Kafka UI

---

# 🗂️ Project Structure

```text
QuickCart/
│
├── api-gateway/
│
├── service-registry/
│
├── user-service/
│
├── product-service/
│
├── inventory-service/
│
├── order-service/
│
├── payment-service/
│
├── notification-service/
│
├── docker-compose.yml
│
└── README.md
```

---

# 🔁 Complete Order Communication Flow

```text
                         CLIENT
                           │
                           ▼
                    ┌─────────────┐
                    │ API Gateway │
                    └──────┬──────┘
                           │
                           ▼
                    ┌─────────────┐
                    │Order Service│
                    └──────┬──────┘
                           │
             ┌─────────────┴─────────────┐
             │                           │
             ▼                           ▼
      Product Service             Inventory Service
             │                           │
             └─────────────┬─────────────┘
                           │
                           ▼
                    Order Created
                           │
                           ▼
                  OrderPlacedEvent
                           │
                           ▼
                        KAFKA
                           │
                           ▼
                  ┌────────────────┐
                  │Payment Service │
                  └───────┬────────┘
                          │
                    ┌─────┴─────┐
                    │           │
                    ▼           ▼
                Razorpay     Payment DB
                    │
                    ▼
             Payment Result
                    │
          ┌─────────┴─────────┐
          │                   │
          ▼                   ▼
     Order Service      Notification
          │                Service
          │                   │
          ▼                   ▼
   Update Order Status     Send Email
          │
          ▼
      Final Result
```

---

# 🔄 Failure Handling

QuickCart demonstrates compensation for failures in distributed workflows.

### Scenario

```text
Order Created
     ↓
Inventory Reserved
     ↓
Payment Processing
     ↓
Payment Failed
     ↓
Rollback Inventory
     ↓
Update Order = FAILED
     ↓
Send Failure Notification
```

This is conceptually similar to a **Saga-style compensation workflow**.

> Note: This project does not implement a full distributed transaction using 2PC. Instead, it demonstrates service-level operations and compensating actions.

---

# 🧠 Important Microservices Concepts Demonstrated

This project demonstrates practical implementation of:

* Microservices Architecture
* API Gateway
* Service Discovery
* Client-side Load Balancing
* REST API
* OpenFeign
* Synchronous Communication
* Asynchronous Communication
* Event-Driven Architecture
* Apache Kafka
* Kafka Producer / Consumer
* JWT Authentication
* Role-Based Authorization
* Database-per-Service
* MongoDB
* MySQL
* Payment Integration
* Email Notification
* Compensating Transactions
* Distributed Tracing
* API Documentation
* Dockerized Infrastructure

---

# 🐳 Infrastructure

Kafka and Kafka UI can be started using Docker Compose.

Example infrastructure:

```text
Docker
 │
 ├── Kafka
 │
 └── Kafka UI
```

Kafka:

```text
localhost:9092
```

Kafka UI:

```text
localhost:7575
```

---

# ▶️ Running the Project

## 1. Start Infrastructure

Start Docker and run:

```bash
docker-compose up -d
```

Verify:

```bash
docker ps
```

---

## 2. Start Service Registry

Start:

```text
service-registry
```

Verify Eureka dashboard.

---

## 3. Start Microservices

Recommended startup order:

```text
1. service-registry
2. api-gateway
3. user-service
4. product-service
5. inventory-service
6. order-service
7. payment-service
8. notification-service
```

The exact order can vary because Eureka enables service discovery, but starting the registry first makes startup and troubleshooting easier.

---

# 🧪 Testing

APIs can be tested using:

* Postman

Recommended testing sequence:

```text
1. Register User
       ↓
2. Login
       ↓
3. Receive JWT
       ↓
4. Add / View Products
       ↓
5. Check Inventory
       ↓
6. Create Order
       ↓
7. Process Payment
       ↓
8. Verify Order Status
       ↓
9. Verify Notification
```

---

# 🔑 Example Authentication Request

```http
POST /api/users/login
Content-Type: application/json
```

Example:

```json
{
  "email": "user@example.com",
  "password": "password"
}
```

Response:

```json
{
  "token": "<JWT_TOKEN>"
}
```

Use the token for protected APIs:

```http
Authorization: Bearer <JWT_TOKEN>
```

---

# 📡 Kafka Communication

Example event:

```java
OrderPlacedEvent
```

Typical event information:

```text
eventId
orderId
skuCode
quantity
eventTime
```

The event is published by a producer and consumed by one or more services.

```text
Producer
   │
   ▼
Kafka Topic
   │
   ├── Consumer 1
   ├── Consumer 2
   └── Consumer 3
```

This allows services to evolve independently.

---

# ⚠️ Kafka Serialization Consideration

Events shared between microservices must maintain a compatible contract.

For example, if one service publishes:

```text
OrderConfirmedEvent
```

the consumer must be able to deserialize the event correctly.

A production implementation should use a stable event contract rather than relying unnecessarily on producer-specific Java class names.

Recommended approaches include:

* Shared event contract module
* Stable JSON schemas
* Schema Registry
* Explicit event type headers
* Compatible serializer/deserializer configuration

This is especially important when producer and consumer packages differ.

---

# 🚨 Error Handling

The application handles common distributed-system failures such as:

* Service unavailable
* Invalid JWT
* Unauthorized request
* Insufficient inventory
* Payment failure
* Kafka consumer failure
* Feign communication failure
* Service discovery failure

For example:

```text
Payment Failed
      │
      ▼
Rollback Inventory
      │
      ▼
Mark Order Failed
      │
      ▼
Send Notification
```

---

# 🎯 Learning Objectives

QuickCart was developed to gain practical experience with:

1. Designing microservices
2. Implementing REST APIs
3. Securing APIs using JWT
4. Implementing API Gateway security
5. Service discovery using Eureka
6. Synchronous service communication using OpenFeign
7. Asynchronous communication using Kafka
8. Managing distributed workflows
9. Implementing compensating transactions
10. Integrating third-party payment systems
11. Implementing email notifications
12. Working with MongoDB and MySQL
13. Debugging distributed applications
14. Implementing distributed tracing
15. Documenting APIs using Swagger

---

# 🚀 Future Improvements

Possible improvements for production readiness:

* Centralized configuration using Spring Cloud Config
* Resilience4j Circuit Breaker
* Retry and timeout policies
* Dead Letter Topics for Kafka failures
* Kafka Schema Registry
* Idempotent event processing
* Outbox Pattern
* Refresh-token mechanism
* Rate limiting at API Gateway
* Centralized logging using ELK
* Prometheus + Grafana monitoring
* Docker Compose / Kubernetes deployment
* CI/CD using GitHub Actions
* Distributed correlation IDs
* Integration and contract testing
* Database migration using Flyway / Liquibase

---

# 📈 Architecture Summary

```text
                 ┌─────────────────────┐
                 │       Client        │
                 └──────────┬──────────┘
                            │
                            ▼
                 ┌─────────────────────┐
                 │    API Gateway      │
                 │ JWT + Routing       │
                 └──────────┬──────────┘
                            │
          ┌─────────────────┼──────────────────┐
          │                 │                  │
          ▼                 ▼                  ▼
     User Service     Product Service     Order Service
          │                 │                  │
      MongoDB             MySQL                │
                                               │
                              ┌────────────────┼───────────────┐
                              │                │               │
                              ▼                ▼               ▼
                         Inventory         Payment       Notification
                           Service          Service         Service
                              │                │               │
                              └────────────────┼───────────────┘
                                               │
                                               ▼
                                             Kafka
                                               │
                                               ▼
                                           Event Bus

                    Eureka → Service Discovery
                    Zipkin → Distributed Tracing
                    Razorpay → Payment Gateway
```

---

# 👨‍💻 Author

**Puja Patil**

Java Backend Developer | Spring Boot | Microservices | Kafka

### Technologies

```text
Java • Spring Boot • Spring Cloud • Microservices
Kafka • REST API • JWT • Spring Security
MongoDB • MySQL • OpenFeign • Docker
```

---

# ⭐ Project Highlights

* **Microservices-based E-Commerce backend**
* **JWT-secured API Gateway**
* **Eureka-based service discovery**
* **OpenFeign synchronous communication**
* **Kafka asynchronous event-driven communication**
* **Inventory reservation and rollback**
* **Razorpay payment integration**
* **Email notification service**
* **MongoDB + MySQL**
* **Distributed tracing with Zipkin**
* **Swagger/OpenAPI documentation**
* **Docker-based Kafka infrastructure**
* **Compensating transaction / Saga-style failure handling**

---

## 📄 License

This project is developed for **learning, portfolio, and demonstration purposes**.
