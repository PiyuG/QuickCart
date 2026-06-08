# QuickCart

QuickCart is a production-oriented E-Commerce application built using Spring Boot Microservices Architecture. The project demonstrates enterprise-level concepts such as API Gateway Security, JWT Authentication, Apache Kafka Event-Driven Communication, Distributed Tracing, Payment Integration, Email/SMS Notifications, and Inter-Service Communication.

---

# 🚀 Features

## Authentication & Authorization

* User Registration
* User Login
* JWT Authentication
* JWT Authorization
* Role-Based Access Control (RBAC)
* API Gateway Security
* Password Encryption using BCrypt

---

## Product Management

* Create Product
* Update Product
* Delete Product
* View Products
* Search Products
* Product Filtering

---

## Category Management

* Create Category
* Update Category
* Delete Category
* Category-Based Product Organization

---

## Image Management

* Upload Product Images
* Retrieve Product Images
* Product Image Association

---

## Order Management

* Create Orders
* Order Validation
* Order Tracking
* Order Status Management

---

## Inventory Management

* Inventory Availability Check
* Inventory Update
* Stock Validation
* Order-Based Inventory Processing

---

## Payment Integration

* Razorpay Payment Integration
* Payment Verification
* Payment Status Tracking
* Razorpay Webhook Handling
* Payment Failure Handling

---

## Notification System

### Email Notifications

* Order Confirmation Email
* Payment Success Email
* Order Status Updates

### SMS Notifications

* Twilio SMS Integration
* Order Notifications
* Payment Notifications

---

# 🏗 Microservices Architecture

The application follows a distributed microservices architecture.

### Services

* API Gateway
* User Service
* Product Service
* Category Service
* Order Service
* Inventory Service
* Payment Service
* Notification Service

---

# 🔄 Communication Pattern

## Synchronous Communication

Implemented using OpenFeign Client.

Examples:

* Order Service → Inventory Service
* Payment Service → Order Service

---

## Asynchronous Communication

Implemented using Apache Kafka.

Examples:

* Order Created Event
* Inventory Update Event
* Notification Event

---

# 📨 Kafka Implementation

### Kafka Features

* Kafka Producer
* Kafka Consumer
* Event-Driven Architecture
* Kafka KRaft Mode
* Consumer Retry Handling
* Consumer Idempotency
* Duplicate Event Prevention

### Kafka Workflow

1. User places an order.
2. Order Service publishes an OrderCreatedEvent.
3. Kafka Topic receives the event.
4. Inventory Service consumes the event.
5. Inventory gets updated.
6. Notification Service receives notification event.
7. Email/SMS notification is sent.
8. Order status is updated.

---

# 🔐 Security Flow

1. User logs in.
2. JWT Token is generated.
3. Token is sent in Authorization Header.
4. API Gateway validates token.
5. User role is verified.
6. Request is forwarded to the target microservice.

---

# 💳 Payment Flow

1. User places an order.
2. Payment Service creates Razorpay Order.
3. User completes payment.
4. Razorpay sends webhook event.
5. Payment Service validates webhook.
6. Payment status is updated.
7. Order status is updated.

---

# 📊 Distributed Tracing

Monitoring and tracing are implemented using:

* Micrometer
* Zipkin

### Benefits

* Request Tracking
* Performance Monitoring
* Service Dependency Tracking
* Distributed Logging

---

# 🛠 Technology Stack

## Backend

* Java 17
* Spring Boot
* Spring Security
* Spring Data JPA
* Spring Data MongoDB
* Spring Cloud Gateway
* OpenFeign

## Database

* MySQL
* MongoDB

## Messaging

* Apache Kafka

## Security

* JWT Authentication
* BCrypt Password Encoder

## Notification

* Spring Mail
* Twilio

## Monitoring

* Micrometer
* Zipkin

## Build Tool

* Maven

## Testing

* Postman

---

# 📂 Project Structure

```text
QuickCart
│
├── api-gateway
├── user-service
├── product-service
├── category-service
├── image-service
├── order-service
├── inventory-service
├── payment-service
├── notification-service
└── README.md
```

---

# ⚙️ Prerequisites

Before running the project, ensure the following are installed:

* Java 17+
* Maven 3+
* MySQL
* MongoDB
* Apache Kafka

---

# ▶️ Running the Application

### Clone Repository

```bash
git clone https://github.com/PiyuG/QuickCart.git
cd QuickCart
```

### Build Project

```bash
mvn clean install
```

### Start Required Services

* MySQL
* MongoDB
* Kafka

### Run Applications

```bash
mvn spring-boot:run
```

---

# 🎯 Key Concepts Demonstrated

* Microservices Architecture
* API Gateway
* JWT Authentication
* Role-Based Authorization
* OpenFeign Client
* Apache Kafka
* Event-Driven Architecture
* Kafka Consumer Idempotency
* Distributed Tracing
* Payment Gateway Integration
* Email Notifications
* SMS Notifications
* Production Testing
* Debugging Techniques

---

# 📚 Learning Outcomes

This project helped in understanding:

* Designing Microservices
* Securing APIs using JWT
* Implementing Gateway Authentication
* Synchronous vs Asynchronous Communication
* Kafka Event Processing
* Handling Duplicate Kafka Events
* Payment Integration with Razorpay
* Email & SMS Notification Systems
* Distributed Tracing using Zipkin
* Real-World E-Commerce Workflows

---

# 👨‍💻 Author

**Puja G**

GitHub: https://github.com/PiyuG

---

# 📄 License

This project is developed for learning and educational purposes.
