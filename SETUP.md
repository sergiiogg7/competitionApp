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
    docker build -t comp-dev -f .\Dockerfile .
    ```

4. **Run the Docker Container**
    ```bash
    docker run -p 8080:8080 comp-dev
    ```

5. **Access the Application**
   Open a web browser and go to: `http://localhost:8080`

## Additional Notes
- Ensure there are no other services using port 8080 on your system as it is exposed through the Docker container.

