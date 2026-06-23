# Full Stack Customer Profile Management App

A production-grade full-stack application for managing customer profiles with image uploads, built with **Java Spring Boot**, **React**, and **PostgreSQL**, deployed on **AWS**.

## Architecture

```
React (Vite)  ──REST/JWT──>  Spring Boot API  ──JPA──>  PostgreSQL
                                   │
                                   └──AWS SDK──>  S3 (Image Storage)
```

| Layer      | Technology                                           |
|------------|------------------------------------------------------|
| Frontend   | React 18, Vite, Chakra UI, Axios, Formik, React Dropzone |
| Backend    | Java 21, Spring Boot 3, Spring Security, Spring Data JPA |
| Database   | PostgreSQL 14, Flyway Migrations                     |
| Auth       | JWT (HMAC-SHA256), BCrypt                            |
| Cloud      | AWS S3, Elastic Beanstalk, RDS                       |
| CI/CD      | GitHub Actions, Docker, Jib, Slack Notifications     |

## Features

- **JWT Authentication** -- Stateless auth with token-based login/registration and protected routes
- **Customer CRUD** -- Create, read, update, and delete customer profiles with form validation (Formik + Yup)
- **Profile Image Upload** -- Drag-and-drop image upload via React Dropzone, stored in AWS S3
- **Flyway Migrations** -- Version-controlled database schema changes (`ddl-auto: validate`)
- **DAO Abstraction Layer** -- Swappable data access (JPA, JDBC, in-memory) via dependency injection
- **CI/CD Pipeline** -- Automated testing, Docker image builds (Jib), and deployment to AWS Elastic Beanstalk
- **Dual Frontend** -- Both React and Angular implementations available

## Getting Started

### Prerequisites

- Java 21
- Node.js 18+
- Docker
- AWS credentials (for S3 image uploads)

### Run Locally

```bash
# 1. Start PostgreSQL
docker-compose up -d

# 2. Start the backend
cd backend
mvn spring-boot:run

# 3. Start the React frontend
cd frontend/react
npm install
npm run dev
```

The API runs on `http://localhost:8080` and the frontend on `http://localhost:5173`.

## API Endpoints

| Method | Endpoint                                      | Auth     | Description              |
|--------|-----------------------------------------------|----------|--------------------------|
| POST   | `/api/v1/auth/login`                          | Public   | Login, returns JWT       |
| POST   | `/api/v1/customers`                           | Public   | Register new customer    |
| GET    | `/api/v1/customers`                           | Required | List all customers       |
| GET    | `/api/v1/customers/{id}`                      | Required | Get customer by ID       |
| PUT    | `/api/v1/customers/{id}`                      | Required | Update customer          |
| DELETE | `/api/v1/customers/{id}`                      | Required | Delete customer          |
| POST   | `/api/v1/customers/{id}/profile-image`        | Required | Upload profile image     |
| GET    | `/api/v1/customers/{id}/profile-image`        | Required | Get profile image        |

## Project Structure

```
backend/
├── src/main/java/com/amigoscode/
│   ├── auth/           # Authentication service & login endpoint
│   ├── customer/       # Customer controller, service, DAO, repository
│   ├── jwt/            # JWT filter, token utility
│   ├── s3/             # AWS S3 client & operations
│   ├── security/       # Security filter chain, CORS, password encoding
│   └── exception/      # Global exception handler & custom exceptions
├── src/main/resources/
│   └── db/migration/   # Flyway SQL migrations

frontend/react/
├── src/
│   ├── components/     # UI components (Login, Signup, Cards, Drawer)
│   └── services/       # Axios API client with JWT interceptor
```

## Key Design Decisions

- **Stateless JWT auth** over sessions for horizontal scalability
- **Flyway** over Hibernate auto-DDL for safe, auditable schema evolution
- **DAO interface pattern** to decouple business logic from data access implementation
- **S3 for images** instead of database BLOBs to keep the database lean and leverage CDN-ready storage
- **Spring Security filter chain** with a custom JWT filter running before `UsernamePasswordAuthenticationFilter`

## CI/CD Pipeline

```
PR opened  ──>  GitHub Actions CI (mvn verify + PostgreSQL service)
                        │
Push to main  ──>  Build Docker image (Jib)  ──>  Docker Hub
                                                      │
                                              AWS Elastic Beanstalk deploy
                                                      │
                                                Slack notification
```
