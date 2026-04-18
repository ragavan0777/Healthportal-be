# Stage 1: Build the application
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Copy the pom.xml and download dependencies (cached layer)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy the source code and build the application
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Run the application
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Create a non-root user for security
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# Copy only the built jar file from the build stage
COPY --from=build /app/target/*.jar app.jar

# Environment variables with sensible defaults
ENV PORT=5000
ENV DB_HOST=localhost
ENV DB_PORT=3306
ENV DB_NAME=todo_db
ENV DB_USERNAME=root
ENV DB_PASSWORD=password

# JVM memory limits for small instances (512MB)
# -XX:MaxRAMPercentage=75.0 ensures Java doesn't use more than 75% of available RAM
ENTRYPOINT ["java", "-XX:MaxRAMPercentage=75.0", "-jar", "app.jar"]

# Expose the application port
FROM openjdk:17-jdk-slim

WORKDIR /app

COPY . .

RUN chmod +x mvnw

RUN ./mvnw clean package -DskipTests

EXPOSE 5000

CMD ["java", "-jar", "target/*.jar"]
