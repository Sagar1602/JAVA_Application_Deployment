# Build stage
FROM gradle:8.4-jdk17 AS builder

WORKDIR /app

# Copy gradle files
COPY build.gradle .
COPY settings.gradle .
COPY gradle/ gradle/
COPY gradlew .
COPY gradlew.bat .

# Copy source code
COPY src/ src/

# Build application
RUN gradle clean bootJar --no-daemon

# Runtime stage
FROM openjdk:17-jdk-slim

WORKDIR /app

# Copy JAR from builder
COPY --from=builder /app/build/libs/app.jar app.jar

# Create non-root user for security
RUN useradd -m -u 1000 appuser && \
    chown -R appuser:appuser /app

USER appuser

# Expose port
EXPOSE 8080

# Health check
HEALTHCHECK --interval=30s --timeout=3s --start-period=5s --retries=3 \
    CMD curl -f http://localhost:8080/health || exit 1

# Run application
ENTRYPOINT ["java", "-jar", "app.jar"]
