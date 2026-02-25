# Review Service

The **Review Service** is a Spring Boot microservice responsible for managing **user reviews and ratings** of books in the Book Store application.  
It allows users to add, update, view, and delete reviews, and communicates with other microservices (like the Book Service and Customer Service) using **OpenFeign** clients.  
This service is registered with the **Eureka Discovery Server** for dynamic service discovery.

---

## Features

- Add, update, delete, and retrieve book reviews  
- Store review data in **MongoDB**  
- Communicate with other services using **Feign Clients**  
- Registered with **Eureka Server** for service discovery  
- Uses **ModelMapper** and **DTOs** for clean data transfer  

---

## Tech Stack

- **Java 17**  
- **Spring Boot 3.5.6**  
- **Spring Cloud 2025.0.0**  
- **MongoDB**  
- **Spring Cloud OpenFeign**  
- **Eureka Client**  
- **ModelMapper**  
- **Lombok**




    
