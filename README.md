# Instructions

## Prerequisites
- Docker installed and running on your system.

## Steps to Run

1. **Clone the Repository**
    ```bash
    git clone -b master https://github.com/sergiiogg7/competitionApp.git
    ```

2. **Navigate to Project Directory**
    ```bash
    cd competitionApp
    ```

3. **Build the Docker Image**
    ```bash
    docker build -t comp-prod -f .\Dockerfile .
    ```

4. **Run the Docker Container**
    ```bash
    docker run -p 8080:8080 comp-prod
    ```

5. **Access the Application**
   Open a web browser and go to: `http://localhost:8080`

## Project Description

- **Name:** CompetitionApp
- **Version:** 0.0.1-SNAPSHOT
- **Description:** Trading Competition Web API

This project, named CompetitionApp, is a web API designed to facilitate the creation and management of trading competitions. It's built using Java and the Spring Boot framework (version 3.1.4).

### Key Dependencies:

- **Spring Boot Starter Data JPA:** Enables working with databases using Java Persistence API.
- **Spring Boot Starter Security:** Provides security configurations for the application.
- **Spring Boot Starter Web:** Supports building web applications.
- **Project Lombok:** A library that helps reduce boilerplate code in Java.
- **Spring Boot DevTools:** Offers development-time enhancements.
- **MySQL Connector:** Allows the application to connect to a MySQL database.
- **JWT (JSON Web Tokens) Libraries:** Facilitates authentication and token-based security.
- **Spring Boot Starter Test:** Includes testing libraries for Spring Boot applications.
- **Spring Security Test:** Adds testing functionalities for Spring Security.
- **SpringDoc OpenAPI Starter:** Provides support for OpenAPI documentation generation.
- **Spring Boot Starter Actuator:** Offers production-ready features to help monitor and manage the application.

### Project Structure:

The project follows the standard Maven structure and employs Maven for dependency management and project build. It includes configurations for database access, security, API documentation using OpenAPI, and Actuator for monitoring purposes.

This API aims to simplify the process of organizing and managing trading competitions, providing secure access and interaction endpoints through RESTful services.

Please note, the project utilizes Java version 17 as specified in the properties.

## Additional Notes
- Ensure there are no other services using port 8080 on your system as it is exposed through the Docker container.
