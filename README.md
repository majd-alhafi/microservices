# Spring Boot Microservices 🚀

This project consists of three microservices: Product, Order, and Inventory, designed to showcase the power of Spring Boot and the microservices architecture.

## Microservices 🔍

### Product Microservice 📦
- This microservice is responsible for managing product-related operations.
- It uses **MongoDB** as the database to store product information.

### Order Microservice 🛒
- This microservice handles order-related functionalities.
- It uses **MySQL** as the database to store order details.
- It includes a **Circuit Breaker** pattern to handle failures when communicating with the Inventory microservice.

### Inventory Microservice 📚
- This microservice manages the inventory of products.
- It uses **MySQL** as the database to store inventory data.

## Architecture 🏗️
- **API Gateway**: The application uses an API Gateway, which is responsible for routing requests to the appropriate microservice. In this project, **Keycloak** is used as the authenticator for the API Gateway.
- **Service Discovery**: The microservices use a service discovery mechanism to locate and communicate with each other.
- **Circuit Breaker**: The Order microservice utilizes the **Circuit Breaker** pattern to handle failures when communicating with the Inventory microservice, ensuring resilient and fault-tolerant behavior.

## Features ✨

- **Product Management**: Create, read, update, and delete products.
- **Order Management**: Place orders, view order history, and manage order status.
- **Inventory Management**: Track and manage product inventory levels.
- **Authentication and Authorization**: Secure access to the microservices using Keycloak.
- **Resilient Communication**: The Order microservice uses a Circuit Breaker pattern to handle failures when communicating with the Inventory microservice.

## Technologies Used 🛠️

- Spring Boot
- Spring Cloud
- MongoDB
- MySQL
- Keycloak
- Kafka
- Circuit Breaker (Resilience4j)
- Maven
