# --- Etapa 1: Construcción (Build) ---
FROM maven:3.9.6-eclipse-temurin-17-alpine AS builder
WORKDIR /app

COPY . .
RUN mvn clean package -DskipTests

# --- Etapa 2: Imagen final ---
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

COPY --from=builder /app/boot/target/app.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
