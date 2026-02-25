# Discovery Service (Eureka Server)

The **Discovery Service** is a Spring Boot microservice that acts as the **Eureka Server** in the Book Store microservices architecture.  
It enables **service registration and discovery**, allowing other microservices (like Book Service, Review Service, Order Service, etc.) to find and communicate with each other dynamically without hardcoding network locations.

---

##  Features

- Acts as a **Service Registry** using **Netflix Eureka Server**  
- Allows **automatic registration** of client microservices  
- Provides a **web dashboard** to view all registered services  
- Supports **load balancing** and **fault tolerance** through service discovery  
- Integrated with **Spring Cloud Netflix Eureka**
