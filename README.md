#  SCM 2.0 - Secure Cloud Contact Management System

SCM 2.0 is a robust, production-ready, full-stack web application designed to act as a secure and private digital address book. It enables users to register, authenticate, and securely manage their private contact directories.

The application focuses on clean architecture, secure data isolation between user accounts, and a responsive user experience.

---

#  Key Features

### Secure Authentication & Authorization
- User registration and login using Spring Security.
- Session-based authentication.
- Protected routes for authenticated users.
- CSRF protection for secure form submissions.

###  User Data Isolation
- Every user has their own private contact directory.
- Contacts are linked to their respective owners.
- Users cannot access or modify another user's contacts.

###  Server-Side Pagination
- Efficient loading of contacts using Spring Data `Pageable`.
- Optimized database queries for better performance.
- Clean pagination support for large contact lists.

###  Profile Image Upload
- Upload profile images using multipart form data.
- Display uploaded images inside contact profiles.

### Responsive UI
- Fully responsive interface.
- Built with Tailwind CSS and Flowbite.
- Client-side Dark Mode support.
- Search and filter functionality for contacts.

---

# Tech Stack

## Backend
- Java 17+
- Spring Boot 3.x
- Spring Security
- Spring Data JPA
- Hibernate ORM
- Maven

## Frontend
- Thymeleaf
- Tailwind CSS
- Flowbite
- Font Awesome

## Database
- MySQL

---

# Database Architecture

The application follows a **One-to-Many (1:N)** relationship between **User** and **Contact**.

```text
+--------------+               +-----------------+
|  USER ENTITY | 1           N | CONTACT ENTITY  |
+--------------+               +-----------------+
| id (PK)      |<------------->| id (PK)         |
| name         |               | name            |
| email        |               | phone_number    |
| password     |               | email           |
| roles        |               | picture_url     |
+--------------+               | favorite        |
                               | user_id (FK)    |
                               +-----------------+
```

- One **User** can have multiple **Contacts**.
- Every **Contact** belongs to exactly one **User**.
- The `user_id` foreign key ensures complete data isolation.

---

# Project Structure

```text
src/main/java/com/scm20
│
├── controllers/      # Request handling layer
├── entities/         # JPA Entity classes
├── forms/            # DTOs & Validation classes
├── helpers/          # Utility classes
├── repositories/     # Spring Data JPA repositories
├── services/         # Business logic
├── config/           # Security & application configuration
└── Scm20Application.java
```

---

# ⚙️ Getting Started

## Prerequisites

- Java JDK 17 or higher
- Maven 3.x
- MySQL
- IntelliJ IDEA / Eclipse / VS Code

---

## 1. Clone the Repository

```bash
git clone https://github.com/YOUR_GITHUB_USERNAME/scm20.git
cd scm20
```

---

## 2. Configure Database

Open:

```text
src/main/resources/application.properties
```

Update the database configuration:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/scm20
spring.datasource.username=your_db_username
spring.datasource.password=your_db_password

spring.jpa.hibernate.ddl-auto=update
```

---

## 3. Run the Application

Using Maven:

```bash
mvn clean spring-boot:run
```

Or using the Maven Wrapper:

```bash
./mvnw spring-boot:run
```

The application will start on:

```
http://localhost:8080
```

---

# Security Features

- Password hashing using Spring Security's `PasswordEncoder`.
- CSRF protection enabled.
- Session-based authentication.
- Server-side validation using `@Valid`.
- Protected endpoints using Spring Security.
- User-specific access control for contact management.

---

# Highlights

- Secure Authentication
- Contact CRUD Operations
- User-specific Data Isolation
- Server-side Pagination
- Profile Image Upload
- Responsive Design
- Dark Mode
- Search & Filter Contacts
- Clean MVC Architecture
- Spring Security Integration

---

# Future Enhancements

- Email Verification
- Forgot Password Functionality
- OAuth2 Login (Google/GitHub)
- Contact Import/Export
- Contact Categories & Tags
- Cloud Image Storage
- REST API Support
- Docker Deployment

---

# Author

**Anik Das**

GitHub: **https://github.com/AnikDas1999**

---

## ⭐ If you like this project, consider giving it a star on GitHub!
