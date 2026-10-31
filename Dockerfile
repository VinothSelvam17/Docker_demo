FROM eclipse-temurin:24-jre

WORKDIR /app

COPY target/docker-demo.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
