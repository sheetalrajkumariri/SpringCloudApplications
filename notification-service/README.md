# Notification Service

The **Notification Service** is a Spring Boot microservice responsible for sending **email notifications** related to various user and order activities in the Book Store application.  
It listens to events from other services (such as Order Service or Review Service) through **Kafka** or **Feign clients** and delivers messages like order confirmations, shipment updates, or review acknowledgments to users.  
This service is registered with the **Eureka Discovery Server** for dynamic service discovery.

---

## Tech Stack

- **Java 17**  
- **Spring Boot 3.5.6**  
- **Spring Cloud 2025.0.0**  
- **Spring Boot Starter Mail**  
- **Kafka / OpenFeign**  
- **Eureka Client**  
- **Lombok**
