# Use Eclipse Temurin (OpenJDK 21)
FROM eclipse-temurin:21-jdk-alpine

# Copy ANY JAR from target/
COPY target/*.jar app.jar

# Run
ENTRYPOINT ["java", "-jar", "/app.jar"]