
FROM maven:3.9.9-eclipse-temurin-17 AS builder

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jre

WORKDIR /app

COPY --from=builder /app/target/music-app-1.0.0.jar app.jar

VOLUME ["/app/data"]

ENV DB_PATH=/app/data/musicapp.db

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]

