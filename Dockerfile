# Use official OpenJDK 25 (or eclipse-temurin if you prefer)
FROM openjdk:25-jdk-slim

WORKDIR /app

# Copy maven executable jar (Spring Boot plugin creates this)
COPY target/FilthiBlog-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]