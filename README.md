# docker-demo

A small, beginner-friendly Spring Boot REST API used to practice building a JAR, packaging it in a Docker image, and building it with a Jenkins pipeline.

- Group: `com.demo` | Artifact: `docker-demo`
- Java 24, Maven, Spring Boot 3.5.6, JAR packaging

## API Endpoints

| Method | Path           | Response                                      |
|--------|----------------|-----------------------------------------------|
| GET    | `/api/hello`   | `{"message": "Hello, Spring Boot!"}`          |
| GET    | `/api/message` | `{"message": "Welcome to Docker!"}`           |
| GET    | `/api/status`  | `{"status": "Application is running"}`        |

## Prerequisites

- JDK 24
- Apache Maven 3.9+
- Docker Desktop (Windows/Mac) or Docker Engine
- Jenkins (optional, for CI) with Maven and Docker available on the agent's PATH

Check your setup:

```
java -version
mvn -version
docker --version
```

## Project Structure

```
docker-demo/
├── Dockerfile
├── .dockerignore
├── .gitignore
├── Jenkinsfile
├── pom.xml
├── README.md
└── src/
    ├── main/
    │   ├── java/com/demo/dockerdemo/
    │   │   ├── DockerDemoApplication.java      (main class)
    │   │   ├── controller/ApiController.java   (REST endpoints)
    │   │   ├── service/MessageService.java     (provides the text)
    │   │   └── dto/
    │   │       ├── MessageResponse.java
    │   │       └── StatusResponse.java
    │   └── resources/application.properties    (configuration)
    └── test/java/com/demo/dockerdemo/
        ├── DockerDemoApplicationTests.java
        └── controller/ApiControllerTest.java   (tests for all 3 endpoints)
```

## Run Locally

```
mvn spring-boot:run
```

The app starts on port 8080. Test it at `http://localhost:8080/api/hello`.

## Test the APIs

Run the unit tests:

```
mvn test
```

When running with Docker (port 8081), call the endpoints with a browser or curl:

```
curl http://localhost:8081/api/hello
curl http://localhost:8081/api/message
curl http://localhost:8081/api/status
```

On Windows PowerShell use `curl.exe` instead of `curl`, or `Invoke-RestMethod http://localhost:8081/api/hello`.

If running locally without Docker, use port 8080 instead of 8081.

## Build the JAR

```
mvn clean package
```

The JAR is created at `target/docker-demo.jar`. Run it with:

```
java -jar target/docker-demo.jar
```

## Build the Docker Image

Build the JAR first (the Dockerfile copies `target/docker-demo.jar`), then:

```
docker build -t docker-demo:latest .
```

## Run and Stop the Docker Container

```
docker run -d -p 8081:8080 --name docker-demo-container docker-demo:latest
```

Test: `http://localhost:8081/api/hello`

Useful commands:

```
docker ps
docker logs docker-demo-container
docker stop docker-demo-container
docker rm docker-demo-container
```

## Configure the Jenkins Pipeline

The `Jenkinsfile` is a declarative pipeline using Windows `bat` commands. It does not use registry credentials and does not push images.

1. Make sure the Jenkins server (Windows) has JDK 24, Maven, and Docker installed, and that `java`, `mvn`, and `docker` work from the command line for the account running Jenkins. Restart the Jenkins service after changing PATH.
2. Make sure Docker Desktop/Engine is running.
3. Push this project to a Git repository (or use a local path your Jenkins can reach).
4. In Jenkins: **New Item** > **Pipeline** > name it `docker-demo`.
5. Under **Pipeline**, choose **Pipeline script from SCM**, select **Git**, enter the repository URL and branch, and set **Script Path** to `Jenkinsfile`.
6. Save and click **Build Now**.

Pipeline stages:

1. Checkout: `checkout scm`
2. Build: `mvn -B clean compile`
3. Test: `mvn -B test` (results published via JUnit)
4. Package: `mvn -B package -DskipTests`
5. Docker Build: `docker build -t docker-demo:latest .`

### Troubleshooting

- `'mvn' is not recognized`: add Maven's `bin` folder to the system PATH, or configure Maven under *Manage Jenkins > Tools* and add a `tools { maven '...' }` block to the Jenkinsfile.
- Unsupported class version / release 24 errors: the JDK used by Maven is older than 24. Check `mvn -version`.
- Docker permission or connection errors: confirm Docker is running and the Jenkins service account can use it.
