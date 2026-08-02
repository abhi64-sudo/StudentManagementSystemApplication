# Student Management System

A full-stack Student Management System built with **Spring Boot**, **Spring Data JPA**, **MySQL**, and **Thymeleaf** (no React/Angular). Includes both a REST API and a server-rendered Bootstrap 5 web UI.

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

## Features

- Dashboard with total students, active students, and new admissions (last 30 days)
- Full CRUD: add, view, edit, delete students
- Search by name, email, or course (single search box across all three)
- Server-side validation (required fields, email format, 10-digit phone, past-date DOB) with inline error messages
- Duplicate-email protection
- Custom 404 page and centralized REST error handling (`GlobalExceptionHandler`)
- REST API for programmatic access, independent of the web UI

## Getting Started

### 1. Create the database

MySQL will auto-create the schema on first run because of `createDatabaseIfNotExist=true` in `application.properties`, but MySQL itself must be running locally on port 3306.

Update the credentials in `src/main/resources/application.properties`:

```properties
spring.datasource.username=root
spring.datasource.password=yourpassword
```

### 2. Run the app

```bash
mvn spring-boot:run
```

The app starts on **http://localhost:8080**.

- Web UI: http://localhost:8080/dashboard
- REST API base: http://localhost:8080/api/students

### 3. Build a JAR (optional)

```bash
mvn clean package
java -jar target/student-management-system.jar
```

## REST API Reference

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
