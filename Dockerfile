FROM eclipse-temurin:17
LABEL authors="wan2daaa"

EXPOSE 8080
ARG JAR_FILE
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]