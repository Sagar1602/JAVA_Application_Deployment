package com.example.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @GetMapping("/")
    public String home() {
        return "Welcome to Java Gradle Application!";
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello from Spring Boot with Gradle!";
    }

    @GetMapping("/health")
    public HealthResponse health() {
        return new HealthResponse("UP", "Application is running");
    }

    @GetMapping("/info")
    public InfoResponse info() {
        return new InfoResponse(
            "Java Gradle App",
            "1.0.0",
            System.getProperty("java.version")
        );
    }

    // Inner class for health response
    static class HealthResponse {
        public String status;
        public String message;

        public HealthResponse(String status, String message) {
            this.status = status;
            this.message = message;
        }

        public String getStatus() { return status; }
        public String getMessage() { return message; }
    }

    // Inner class for info response
    static class InfoResponse {
        public String appName;
        public String version;
        public String javaVersion;

        public InfoResponse(String appName, String version, String javaVersion) {
            this.appName = appName;
            this.version = version;
            this.javaVersion = javaVersion;
        }

        public String getAppName() { return appName; }
        public String getVersion() { return version; }
        public String getJavaVersion() { return javaVersion; }
    }
}
