# Java Gradle Application with Docker

A complete Spring Boot application built with **Gradle** and containerized with **Docker**. Includes CI/CD pipeline using GitHub Actions.

---

## 📋 Project Structure

```
your-repo/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/app/
│   │   │       └── Application.java       (Main Spring Boot app)
│   │   └── resources/
│   │       └── application.properties     (Spring config)
│   └── test/
│       └── java/
│           └── com/example/app/
│               └── ApplicationTest.java   (Unit tests)
├── gradle/                                (Gradle wrapper files)
│   └── wrapper/
│       ├── gradle-wrapper.jar
│       └── gradle-wrapper.properties
├── build.gradle                           (Gradle build file)
├── settings.gradle                        (Gradle settings)
├── gradlew                                (Gradle wrapper script)
├── gradlew.bat                            (Gradle wrapper for Windows)
├── Dockerfile                             (Docker configuration)
├── .github/
│   └── workflows/
│       └── build.yml                      (GitHub Actions workflow)
└── README.md
```

---

## 🚀 Quick Start

### Prerequisites
- Java 17+
- Docker
- Git
- GitHub account (for CI/CD)

### 1. Clone the Repository

```bash
git clone https://github.com/yourusername/your-repo.git
cd your-repo
```

### 2. Build with Gradle (Local)

```bash
# Make gradlew executable
chmod +x gradlew

# Clean and build
./gradlew clean bootJar

# Verify JAR was created
ls -lh build/libs/app.jar
```

### 3. Run the Application Locally

```bash
# Using Gradle
./gradlew bootRun

# Or run the JAR directly
java -jar build/libs/app.jar
```

Access the application:
- http://localhost:8080/ → Welcome message
- http://localhost:8080/hello → Hello message
- http://localhost:8080/health → Health check
- http://localhost:8080/info → Application info

---

## 🐳 Docker Build & Run

### Build Docker Image

```bash
# Build image
docker build -t java-gradle-app:1.0.0 .

# Verify image
docker images | grep java-gradle-app
```

### Run Docker Container

```bash
# Run container
docker run -d \
  --name java-app \
  -p 8080:8080 \
  java-gradle-app:1.0.0

# Check container status
docker ps | grep java-app

# View logs
docker logs java-app

# Test application
curl http://localhost:8080/

# Stop container
docker stop java-app
docker rm java-app
```

---

## 🔧 Gradle Commands

```bash
# Build
./gradlew clean build

# Build without tests
./gradlew clean bootJar

# Run tests
./gradlew test

# Run application
./gradlew bootRun

# Check dependencies
./gradlew dependencies

# Build docker image
./gradlew build docker
```

---

## 🔄 GitHub Actions CI/CD Pipeline

### Workflow Steps

```
1. CHECKOUT CODE
   └─ Clone repository

2. BUILD WITH GRADLE
   ├─ Setup Java 17
   ├─ Build with Gradle
   ├─ Run tests
   └─ Upload artifacts

3. BUILD DOCKER IMAGE
   ├─ Login to Docker Hub
   ├─ Build image
   └─ Push to registry

4. SCAN IMAGE
   ├─ Trivy vulnerability scan
   └─ Upload results

5. DEPLOY
   ├─ Pull image
   ├─ Run container
   └─ Health checks

6. NOTIFY
   └─ Send email notification
```

### Setup GitHub Actions

#### Step 1: Add Secrets to Repository

Go to **Settings → Secrets and variables → Actions**

Add these secrets:

```
DOCKER_USERNAME = your-docker-username
DOCKER_PASSWORD = your-docker-access-token
EMAIL_USER = your-email@gmail.com
EMAIL_PASS = your-app-password
```

#### Step 2: Create Workflow File

Create `.github/workflows/build.yml`:

```bash
mkdir -p .github/workflows
cp gradle-docker-workflow.yml .github/workflows/build.yml
```

#### Step 3: Commit and Push

```bash
git add .
git commit -m "Add Gradle build and Docker CI/CD pipeline"
git push origin main
```

Workflow will automatically run on push!

---

## 📊 Build Output

### Local Build

```
BUILD SUCCESSFUL in 15s
11 actionable tasks: 11 executed

Output: build/libs/app.jar
```

### Docker Build

```
Step 1/10 : FROM gradle:8.4-jdk17 AS builder
 ---> [cache]
Step 2/10 : WORKDIR /app
 ---> Using cache
...
Successfully built abc123def456
```

### GitHub Actions Output

```
✓ BUILD_WITH_GRADLE
  - Setup Java 17
  - Build with Gradle
  - Run tests
  - Upload artifacts

✓ BUILD_DOCKER_IMAGE
  - Login to Docker Hub
  - Push to registry

✓ SCAN_IMAGE
  - Trivy vulnerability scan

✓ DEPLOY
  - Health checks passed

✓ NOTIFY
  - Email sent
```

---

## 🧪 Testing

### Unit Tests

```bash
./gradlew test

# View test results
open build/reports/tests/test/index.html
```

### Integration Tests

```bash
# Build and run
./gradlew bootJar
java -jar build/libs/app.jar &

# Test endpoints
curl http://localhost:8080/health
curl http://localhost:8080/info

# Kill process
kill %1
```

### Docker Tests

```bash
# Run container
docker run -d -p 8080:8080 java-gradle-app:1.0.0

# Test endpoints
for i in {1..5}; do curl http://localhost:8080/; done

# Load test
docker run --rm --network host httpd:latest \
  ab -n 100 -c 10 http://localhost:8080/

# Cleanup
docker stop $(docker ps -q)
```

---

## 📈 Gradle Wrapper

The Gradle Wrapper allows building without installing Gradle:

```bash
# Wrapper version
./gradlew --version

# Update wrapper
./gradlew wrapper --gradle-version=8.4
```

Files:
- `gradlew` - Unix shell script
- `gradlew.bat` - Windows batch script
- `gradle/wrapper/gradle-wrapper.jar` - Wrapper JAR
- `gradle/wrapper/gradle-wrapper.properties` - Configuration

---

## 🔐 Security

### Docker Security

```dockerfile
# Non-root user
RUN useradd -m -u 1000 appuser
USER appuser

# Health checks
HEALTHCHECK --interval=30s --timeout=3s

# Multi-stage build (smaller image)
FROM gradle:8.4-jdk17 AS builder
FROM openjdk:17-jdk-slim
```

### Image Scanning

```bash
# Scan with Trivy
docker run --rm -v /var/run/docker.sock:/var/run/docker.sock \
  aquasec/trivy image java-gradle-app:1.0.0

# Scan with Grype
docker run --rm -v /var/run/docker.sock:/var/run/docker.sock \
  anchore/grype:latest java-gradle-app:1.0.0
```

---

## 📦 Docker Hub Upload

### Prerequisites

```bash
# Login to Docker Hub
docker login -u your-username -p your-access-token

# Create repository at hub.docker.com
# Name: java-gradle-app
```

### Tag and Push

```bash
# Tag image
docker tag java-gradle-app:1.0.0 your-username/java-gradle-app:1.0.0
docker tag java-gradle-app:1.0.0 your-username/java-gradle-app:latest

# Push to Docker Hub
docker push your-username/java-gradle-app:1.0.0
docker push your-username/java-gradle-app:latest

# Pull and run
docker pull your-username/java-gradle-app:latest
docker run -p 8080:8080 your-username/java-gradle-app:latest
```

---

## 🐛 Troubleshooting

### Gradle Build Fails

```bash
# Clear gradle cache
./gradlew clean --no-daemon

# Check Java version
java -version

# Rebuild
./gradlew clean bootJar
```

### Docker Build Fails

```bash
# Build with verbose output
docker build -t java-gradle-app:1.0.0 --progress=plain .

# Check Dockerfile syntax
docker build --check .

# View logs
docker logs <container-id>
```

### Application Won't Start

```bash
# Check logs
docker logs java-app

# Test locally
./gradlew bootRun

# Verify port
lsof -i :8080
```

---

## 📚 Resources

- **Gradle**: https://gradle.org/
- **Spring Boot**: https://spring.io/projects/spring-boot
- **Docker**: https://www.docker.com/
- **GitHub Actions**: https://docs.github.com/en/actions

---

## 📝 License

MIT License - See LICENSE file for details

---

## 👤 Author

Your Name / Your Organization

---

## 🤝 Contributing

1. Fork the repository
2. Create feature branch (`git checkout -b feature/amazing-feature`)
3. Commit changes (`git commit -m 'Add amazing feature'`)
4. Push to branch (`git push origin feature/amazing-feature`)
5. Open Pull Request

---

## 📞 Support

For issues or questions:
- Create GitHub Issue
- Email: support@example.com
- Documentation: https://docs.example.com
