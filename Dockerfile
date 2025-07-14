# ---------- STAGE 1: Build with Maven ----------
FROM maven:3.9.6-eclipse-temurin-21 AS builder

WORKDIR /app
COPY . .

# Build the application (skipping tests for speed)
RUN mvn clean package -DskipTests


# ---------- STAGE 2: Create a runtime image ----------
FROM eclipse-temurin:21-jdk-jammy

WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
