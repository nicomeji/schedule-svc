# --- Etapa 1: Construcción (Build) ---
FROM eclipse-temurin:17-jdk-alpine AS builder
WORKDIR /app

# Copia los archivos del proyecto
COPY pom.xml .
COPY src ./src

# Si usas el wrapper de Maven
COPY .mvn .mvn
COPY mvnw .
RUN ./mvnw clean package -DskipTests

# --- Etapa 2: Imagen final ---
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copia el JAR generado
COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
