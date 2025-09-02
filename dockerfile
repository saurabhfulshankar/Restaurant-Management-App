# Step 1: Build stage using Maven
FROM maven:3.6.3-jdk-11-slim AS builder

# Set working directory
WORKDIR /app

# Copy pom.xml and download dependencies first (cache optimization)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy the rest of the source
COPY src ./src

# Package the application
RUN mvn clean package -DskipTests

# Step 2: Run stage using minimal JRE base image
FROM eclipse-temurin:11-jre-alpine

WORKDIR /app

# Create non-root user
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

# Copy the WAR
COPY target/*.war app.war

ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75.0 -Djava.security.egd=file:/dev/./urandom"

EXPOSE 8080

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.war"]
