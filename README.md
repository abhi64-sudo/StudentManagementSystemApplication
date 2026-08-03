# Student Management System

🔗 **Live demo:** [studentmanagementsystemapplication-2.onrender.com](https://studentmanagementsystemapplication-2.onrender.com/)

> Note: hosted on Render's free tier — the app may take 30–60 seconds to wake up on first load after a period of inactivity.

A full-stack Student Management System built with **Spring Boot**, **Spring Data JPA**, **MySQL**, and **Thymeleaf** (no React/Angular). Includes both a REST API and a server-rendered Bootstrap 5 web UI, deployed live via Docker on Render with a managed MySQL database (Aiven).

## Tech Stack

- Java 21
- Spring Boot 3.3
- Spring Data JPA / Hibernate
- MySQL
- Thymeleaf
- Bootstrap 5
- Maven
- Bean Validation (Jakarta Validation)
- Lombok
- Docker (deployment)

## Features

- Dashboard with total students, active students, and new admissions (last 30 days)
- Full CRUD: add, view, edit, delete students
- Search by name, email, or course (single search box across all three)
- Server-side validation (required fields, email format, 10-digit phone, past-date DOB) with inline error messages
- Duplicate-email protection
- Custom 404 page and centralized REST error handling (`GlobalExceptionHandler`)
- REST API for programmatic access, independent of the web UI

## Getting Started (Local Development)

### 1. Database

MySQL will auto-create the schema on first run because of `createDatabaseIfNotExist=true`, but MySQL itself must be running locally on port 3306 (or point at any MySQL-compatible instance, local or cloud).

### 2. Configure credentials

Database connection details are read from environment variables, with local defaults as a fallback — real credentials are **never** hardcoded in `application.properties`:

```properties
spring.datasource.url=${SPRING_DATASOURCE_URL:jdbc:mysql://localhost:3306/student_management_db?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC}
spring.datasource.username=${SPRING_DATASOURCE_USERNAME:root}
spring.datasource.password=${SPRING_DATASOURCE_PASSWORD:yourpassword}
```

For local development, either edit the fallback values directly, or set environment variables (e.g. in your IDE's Run Configuration) — the latter is recommended so you never commit real credentials.

### 3. Run the app

```bash
mvn spring-boot:run
```

The app starts on **http://localhost:8080**.

- Web UI: http://localhost:8080/dashboard
- REST API base: http://localhost:8080/api/students

### 4. Build a JAR (optional)

```bash
mvn clean package
java -jar target/student-management-system.jar
```

## Deployment

This project is deployed as a Docker container on [Render](https://render.com), connected to a managed MySQL instance on [Aiven](https://aiven.io). Render doesn't offer a native Java runtime, so the included `Dockerfile` handles the build using a multi-stage image (Maven build stage → slim JRE run stage).

To deploy your own copy:
1. Push this repo to your own GitHub account
2. Create a new Web Service on Render, pointing at your repo (it auto-detects the `Dockerfile`)
3. Set these environment variables in Render's dashboard:
   - `SPRING_DATASOURCE_URL`
   - `SPRING_DATASOURCE_USERNAME`
   - `SPRING_DATASOURCE_PASSWORD`
4. Deploy — Render assigns the port automatically via the `PORT` environment variable, which `application.properties` already reads

## REST API Reference

Base URL: `http://localhost:8080` (local) or `https://studentmanagementsystemapplication-2.onrender.com` (live)

| Method | Endpoint                        | Description                  |
|--------|----------------------------------|-------------------------------|
| GET    | `/api/students`                 | List all students             |
| GET    | `/api/students/{id}`            | Get one student                |
| POST   | `/api/students`                 | Create a student               |
| PUT    | `/api/students/{id}`            | Update a student               |
| DELETE | `/api/students/{id}`            | Delete a student                |
| GET    | `/api/students/search?keyword=` | Search by name/email/course     |
| GET    | `/api/students/dashboard-stats` | Dashboard counts (JSON)         |

Example `POST` body:

```json
{
  "name": "Priya Sharma",
  "email": "priya.sharma@example.com",
  "phone": "9876543210",
  "course": "B.Tech CSE",
  "department": "Computer Science",
  "gender": "Female",
  "dob": "2003-05-14",
  "address": "Bengaluru, India",
  "status": "ACTIVE"
}
```

## Web Pages

| Page             | URL                     |
|-------------------|--------------------------|
| Dashboard         | `/dashboard`             |
| Student list       | `/students`               |
| Add student         | `/students/new`            |
| Student details      | `/students/{id}`            |
| Edit student           | `/students/{id}/edit`        |

## Project Structure

```
src/main/java/com/abhay/studentmanagementsystem/
 ├── controller/    REST + Thymeleaf controllers
 ├── service/       Business logic
 ├── repository/    Spring Data JPA repositories
 ├── entity/        JPA entities
 └── exception/     Custom exceptions + global handler

src/main/resources/
 ├── templates/     Thymeleaf HTML pages
 ├── static/css/    Custom CSS
 └── application.properties
```

## Roadmap / Ideas for Phase 3

- Pagination and sorting on the student list
- Student photo upload
- Soft delete instead of hard delete
- Spring Security login
- Unit and integration tests for service/controller layers
