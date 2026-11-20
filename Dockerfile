# ---- Stage 1: Build ----
FROM eclipse-temurin:17-jdk AS builder
WORKDIR /app

# Copy the pom.xml and source code
COPY pom.xml .
COPY src ./src

# Use Maven to compile and package (Maven will automatically download dependencies)
RUN apt-get update && apt-get install -y maven && \
    mvn clean package -DskipTests

# ---- Stage 2: Runtime ----
FROM eclipse-temurin:17-jre
WORKDIR /app

# Copy the built JAR from the previous stage
COPY --from=builder /app/target/music-app-1.0.0.jar app.jar

# Create a volume for the SQLite database
VOLUME ["/app/data"]
ENV DB_PATH=/app/data/musicapp.db

ENTRYPOINT ["java", "-jar", "app.jar"]
