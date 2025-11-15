# === Stage 1: Build the JAR ===
FROM eclipse-temurin:21-jdk AS builder
WORKDIR /app

# Copy pom.xml and source code
COPY . .

# Build the jar
RUN ./mvnw clean package -DskipTests

# === Stage 2: Run the JAR ===
FROM eclipse-temurin:21-jdk
WORKDIR /app

# Copy built jar from stage 1
COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
