# 📚 Complete Setup Guide: Java Gradle Application with Docker

This guide walks you through setting up the Java Gradle application with Docker and GitHub Actions CI/CD.

---

## 🎯 What You'll Get

- ✅ Spring Boot application with Gradle
- ✅ Docker containerization
- ✅ GitHub Actions CI/CD pipeline
- ✅ Automated Docker image build & push
- ✅ Image vulnerability scanning
- ✅ Email notifications

---

## 📋 Prerequisites

### Required Software

```bash
# Check Java version (must be 17+)
java -version

# Install Docker
# https://www.docker.com/products/docker-desktop

# Git
git --version

# GitHub Account
# https://github.com/signup
```

### Docker Hub Account

1. Create account at https://hub.docker.com
2. Create repository: `java-gradle-app`
3. Generate access token:
   - Settings → Security → New Access Token
   - Copy the token

---

## 🚀 Step 1: Create Repository

### Option A: Create New Repository

```bash
# Create directory
mkdir java-gradle-app
cd java-gradle-app

# Initialize git
git init
git branch -M main

# Create remote repository on GitHub
# https://github.com/new

# Add remote
git remote add origin https://github.com/yourusername/java-gradle-app.git
```

### Option B: Clone Existing

```bash
git clone https://github.com/yourusername/java-gradle-app.git
cd java-gradle-app
```

---

## 📁 Step 2: Create Project Structure

### Create Directories

```bash
# Create directory structure
mkdir -p src/main/java/com/example/app
mkdir -p src/main/resources
mkdir -p src/test/java/com/example/app
mkdir -p gradle/wrapper
mkdir -p .github/workflows
```

### Copy Files

Copy these files to your repository:

```
1. build.gradle                    → Root directory
2. settings.gradle                 → Root directory
3. Application.java                → src/main/java/com/example/app/
4. application.properties           → src/main/resources/
5. ApplicationTest.java             → src/test/java/com/example/app/
6. Dockerfile                       → Root directory
7. .dockerignore                    → Root directory
8. .gitignore                       → Root directory
9. gradle-docker-workflow.yml       → .github/workflows/build.yml
```

---

## 🔧 Step 3: Download Gradle Wrapper

```bash
# Download gradle wrapper
curl -s "https://gradle.org/next-steps/?version=8.4&format=bin" \
  | tar xz -C gradle/wrapper --strip-components=2 gradle-8.4/lib/gradle-wrapper.jar

# Or download manually from:
# https://gradle.org/release-checksums/

# Make script executable
chmod +x gradlew

# Verify
./gradlew --version
```

---

## ✅ Step 4: Test Local Build

### Build Application

```bash
# Clean and build
./gradlew clean bootJar

# Output should show:
# BUILD SUCCESSFUL in 15s
# Check build/libs/app.jar exists
ls -lh build/libs/app.jar
```

### Run Application

```bash
# Option 1: Using Gradle
./gradlew bootRun

# Option 2: Run JAR directly
java -jar build/libs/app.jar
```

### Test Endpoints

Open new terminal:

```bash
# Test home endpoint
curl http://localhost:8080/

# Test hello endpoint
curl http://localhost:8080/hello

# Test health endpoint
curl http://localhost:8080/health

# Test info endpoint
curl http://localhost:8080/info
```

Stop application:
```bash
# Press Ctrl+C in gradle terminal
```

---

## 🐳 Step 5: Build Docker Image (Local)

### Build Image

```bash
# Build Docker image
docker build -t java-gradle-app:1.0.0 .

# Verify image created
docker images | grep java-gradle-app
```

### Run Container

```bash
# Run container
docker run -d \
  --name java-app \
  -p 8080:8080 \
  java-gradle-app:1.0.0

# Check if running
docker ps | grep java-app
```

### Test Container

```bash
# Test endpoint
curl http://localhost:8080/

# View logs
docker logs java-app

# Stop container
docker stop java-app
docker rm java-app
```

---

## 📤 Step 6: Push to GitHub

### Commit Files

```bash
# Add all files
git add .

# Commit
git commit -m "Initial commit: Java Gradle app with Docker"

# Push to GitHub
git push origin main

# Verify on GitHub
# https://github.com/yourusername/java-gradle-app
```

---

## 🔐 Step 7: Add GitHub Secrets

### Add Docker Credentials

1. Go to your GitHub repository
2. Click **Settings**
3. Click **Secrets and variables** → **Actions**
4. Click **New repository secret**

Add these secrets:

#### Secret 1: DOCKER_USERNAME
```
Name: DOCKER_USERNAME
Value: your-docker-username
```

#### Secret 2: DOCKER_PASSWORD
```
Name: DOCKER_PASSWORD
Value: your-docker-access-token (from Docker Hub)
```

#### Secret 3: EMAIL_USER
```
Name: EMAIL_USER
Value: your-email@gmail.com
```

#### Secret 4: EMAIL_PASS
```
Name: EMAIL_PASS
Value: your-app-password (from Gmail settings)
```

---

## 🔄 Step 8: Setup GitHub Actions

### Create Workflow File

```bash
# Already created if you copied .github/workflows/build.yml
# If not:
mkdir -p .github/workflows
cp gradle-docker-workflow.yml .github/workflows/build.yml
```

### Commit and Push

```bash
git add .github/workflows/build.yml
git commit -m "Add GitHub Actions CI/CD pipeline"
git push origin main
```

### Monitor Workflow

1. Go to your GitHub repository
2. Click **Actions** tab
3. Watch workflow run:
   - BUILD_WITH_GRADLE
   - BUILD_DOCKER_IMAGE
   - SCAN_IMAGE
   - DEPLOY
   - NOTIFY

---

## 📊 Step 9: Verify Everything Works

### Check Workflow Status

- ✅ All jobs should show green checkmarks
- ✅ Docker image pushed to Docker Hub
- ✅ Email notification received

### Verify Docker Hub

1. Go to https://hub.docker.com/
2. Login
3. Find `java-gradle-app` repository
4. See tags: `1.0.0`, `1.0.0-1`, `latest`

### Pull and Run Image

```bash
# Pull from Docker Hub
docker pull yourusername/java-gradle-app:latest

# Run
docker run -d -p 8080:8080 yourusername/java-gradle-app:latest

# Test
curl http://localhost:8080/

# Cleanup
docker stop $(docker ps -q)
```

---

## 🎓 Next Steps

### 1. Customize Application

Edit `src/main/java/com/example/app/Application.java`:
- Add more endpoints
- Add business logic
- Add database integration

### 2. Add Features

```gradle
// In build.gradle, add dependencies:
implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
implementation 'org.springframework.boot:spring-boot-starter-security'
implementation 'io.jsonwebtoken:jjwt:0.11.5'
```

### 3. Setup Database

```properties
# In application.properties
spring.datasource.url=jdbc:mysql://localhost:3306/appdb
spring.datasource.username=root
spring.datasource.password=password
spring.jpa.hibernate.ddl-auto=update
```

### 4. Add Tests

Create more test files in `src/test/java/com/example/app/`

### 5. Deploy to Cloud

- AWS ECS/EKS
- Azure Container Instances
- Google Cloud Run
- Heroku

---

## 🐛 Troubleshooting

### Issue: Gradle Build Fails

**Solution:**
```bash
# Clear cache
./gradlew clean --no-daemon

# Try again
./gradlew clean bootJar
```

### Issue: Docker Build Fails

**Solution:**
```bash
# Check Dockerfile syntax
docker build --check .

# Build with verbose output
docker build -t java-gradle-app:1.0.0 --progress=plain .
```

### Issue: GitHub Actions Fails

**Solution:**
1. Check **Actions** tab for error messages
2. Verify all secrets are added correctly
3. Check Docker Hub credentials
4. Check email credentials

### Issue: Application Won't Start

**Solution:**
```bash
# Check logs
docker logs <container-id>

# Verify port is available
lsof -i :8080

# Try different port
docker run -p 9090:8080 java-gradle-app:1.0.0
```

---

## 📚 Useful Commands

### Gradle Commands

```bash
# List all tasks
./gradlew tasks

# View dependencies
./gradlew dependencies

# Run specific test
./gradlew test --tests ApplicationTest

# Build without running tests
./gradlew clean bootJar -x test
```

### Docker Commands

```bash
# View image details
docker inspect java-gradle-app:1.0.0

# Push to Docker Hub
docker push yourusername/java-gradle-app:1.0.0

# Pull from Docker Hub
docker pull yourusername/java-gradle-app:1.0.0

# View container logs
docker logs -f <container-id>

# Execute command in container
docker exec -it <container-id> /bin/bash
```

### GitHub Commands

```bash
# View remote
git remote -v

# Check branch
git branch

# Create new branch
git checkout -b feature/new-feature

# Merge branch
git merge feature/new-feature

# Push branch
git push origin feature/new-feature
```

---

## 📖 Learning Resources

- **Gradle**: https://gradle.org/guides/
- **Spring Boot**: https://spring.io/guides
- **Docker**: https://docs.docker.com/
- **GitHub Actions**: https://docs.github.com/en/actions/learn-github-actions

---

## ✨ Success Checklist

- [ ] Repository created on GitHub
- [ ] All files copied to correct locations
- [ ] Gradle build successful locally
- [ ] Application runs locally on port 8080
- [ ] Docker image builds successfully
- [ ] Docker container runs successfully
- [ ] Pushed to GitHub
- [ ] Secrets added to GitHub
- [ ] GitHub Actions workflow runs successfully
- [ ] Docker image pushed to Docker Hub
- [ ] Email notification received
- [ ] Can pull image from Docker Hub and run

---

## 🎉 Congratulations!

You now have a complete Java Gradle application with:
- ✅ Local development setup
- ✅ Docker containerization
- ✅ Automated CI/CD pipeline
- ✅ Image vulnerability scanning
- ✅ Automated deployments
- ✅ Email notifications

---

## 💡 Pro Tips

1. **Keep Dockerfile simple** - Multi-stage builds reduce image size
2. **Use latest versions** - Keep Spring Boot and Gradle updated
3. **Tag images properly** - Use semantic versioning (1.0.0, 1.0.1, etc.)
4. **Scan for vulnerabilities** - Always scan images before pushing
5. **Monitor logs** - Check GitHub Actions logs for debugging
6. **Test locally first** - Build and test locally before pushing
7. **Document changes** - Update README when adding features
8. **Use environment variables** - Keep secrets out of code

---

## 📞 Need Help?

- Check GitHub Actions logs for errors
- Review Dockerfile syntax
- Verify all secrets are configured
- Check Docker Hub credentials
- Review application logs: `docker logs <id>`

Good luck! 🚀
