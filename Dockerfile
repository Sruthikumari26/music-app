FROM eclipse-temurin:17-jdk AS builder

WORKDIR /app

COPY src ./src
COPY lib ./lib

RUN mkdir -p out && \
    javac --add-modules jdk.httpserver \
    -cp "lib/*" \
    -d out \
    $(find src -name "*.java")

RUN jar cfe MusicApp.jar Main -C out . -C lib .

FROM eclipse-temurin:17-jre

WORKDIR /app

COPY --from=builder /app/MusicApp.jar .

VOLUME ["/app/data"]

ENV DB_PATH=/app/data/musicapp.db

EXPOSE 8080

ENTRYPOINT ["java", "--add-modules", "jdk.httpserver", "-jar", "MusicApp.jar"]
