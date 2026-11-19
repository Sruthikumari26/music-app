# ---- Stage 1: Build ----
FROM eclipse-temurin:17-jdk AS builder
WORKDIR /app

# Copy source and libraries
COPY src ./src
COPY lib ./lib

# Compile all Java files
RUN mkdir -p out && \
    javac -cp "lib/*" -d out $(find src -name "*.java")

# Package into a runnable JAR
RUN jar cfe MusicWave.jar Main -C out . -C lib .

# ---- Stage 2: Runtime ----
FROM eclipse-temurin:17-jre
WORKDIR /app

# Copy the built JAR
COPY --from=builder /app/MusicWave.jar .

# Create a volume for the SQLite database
VOLUME ["/app/data"]
ENV DB_PATH=/app/data/musicapp.db

ENTRYPOINT ["java", "-jar", "MusicWave.jar"]
