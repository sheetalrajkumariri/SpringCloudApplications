📚 Book Store Microservices Project

This project is a Book Store system built using Spring Boot and Spring Cloud Microservices.

Each feature is developed as a separate microservice.
All services communicate using:

Eureka Discovery Server

API Gateway

Feign Client

Kafka (Event Messaging)

Flyway (Database Migration)

🏗 Architecture Flow

Client → API Gateway → Microservices
Services → Register in Discovery Server
Services → Communicate using Feign
Events → Communicate using Kafka

📦 Services in This Project
1️⃣ Discovery Service

📁 discovery-service

Purpose:

Registers all services

Helps services find each other

No hardcoded URLs needed

All services register here when they start.

2️⃣ API Gateway Service

📁 api-gateway-service

Purpose:

Single entry point for client

Routes request to correct service

Hides internal service URLs

Flow:

Client → API Gateway → Required Service

3️⃣ Book Store Service

📁 book-store-service

This is the main business service.

Manages:

Books

Customers

Orders

Features:

✔ Add / Update / Delete Book
✔ Create / Get Customer
✔ Create / Get Order

🗄 Database

MySQL or H2

Spring Data JPA

Hibernate

Each service can have its own database.

🛠 Flyway

Flyway is used for database version control.

Why Flyway?

Automatically creates tables

Manages schema versions

Runs SQL scripts on application startup

Example:

V1__create_book_table.sql

V2__create_customer_table.sql

🔗 Feign Client

Feign is used for service-to-service communication.

Why Feign?

Easy REST communication

Uses service name from Discovery Server

No need to manually write RestTemplate

Example:

Order calls Customer service using Feign

📝 Review Service

📁 review-service

Purpose:

Add review

Get reviews by book

This service is separate to keep review logic independent.

🔔 Notification Service

📁 notification-service

Purpose:

Sends notifications when events happen

📢 Kafka Integration

Kafka is used for event-based communication between services.

In this project:

✅ Producer:

Book Store Service

When a new Book is created

When a new Order is placed

When a Customer is created

Book Store Service sends message to Kafka topic.

✅ Consumer:

Notification Service

Listens to Kafka topic

Receives messages

Sends notification (log/email/etc.)

🔄 Kafka Flow

Book Store Service (Producer)
→ Sends message to Kafka Topic
→ Notification Service (Consumer) reads message
→ Notification is processed

This is called Asynchronous Communication
Services do not directly call each other.

📘 Kafka Reference Tutorial

Kafka implementation is based on this guide:

👉 https://www.javaguides.net/2022/06/spring-boot-apache-kafka-tutorial.html

This tutorial explains:

Kafka Producer

Kafka Consumer

Kafka Configuration

Topic setup in Spring Boot

🔄 How Everything Connects

All services register in Discovery Server.

Client calls API Gateway.

Gateway routes request to correct service.

Services communicate using Feign Client.

Book Store Service sends events to Kafka.

Notification Service consumes Kafka events.

Flyway manages database schema.

JPA connects service with database.

🧰 Technologies Used

Java

Spring Boot

Spring Cloud

Eureka Discovery

Spring Cloud Gateway

Spring Data JPA

Hibernate

Flyway

Feign Client

Apache Kafka

REST APIs

MySQL / H2

🎯 Why This Project?

This project demonstrates:

✔ Microservices Architecture
✔ Service Discovery
✔ API Gateway Pattern
✔ Inter-service Communication using Feign
✔ Event-driven Architecture using Kafka
✔ Database Migration using Flyway

🚀 Final Summary

This is a complete Book Store Microservices system where:

Discovery handles service registration

Gateway handles routing

Book Store Service manages business logic and produces Kafka events

Notification Service consumes Kafka events

Review Service handles reviews

Feign connects services

Flyway manages database changes
