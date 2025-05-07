# Loan Eligibility System

A full-stack application with Angular frontend and Spring Boot backend that determines loan eligibility using Drools rules engine.

## Prerequisites

- Node.js (v16+) & npm (for Angular)
- Java JDK 17 (for Spring Boot)
- MySQL 8.0+
- Angular CLI (`npm install -g @angular/cli`)
- Maven (for Spring Boot)

## Project Structure

```
loan-eligibility-system/
├── frontend/              # Angular application
└── backend/               # Spring Boot application
```

## Setup Instructions

### 1. MySQL Database Configuration

1. Install MySQL and start the server
2. Create a new database:
   ```sql
   CREATE DATABASE loan_eligibility;
   ```
3. Update Spring Boot configuration:
   - Open `backend/src/main/resources/application.properties`
   - Modify these values:
     ```properties
     spring.datasource.url=jdbc:mysql://localhost:3306/loan_eligibility
     spring.datasource.username=your_username
     spring.datasource.password=your_password
     ```
   - Hibernate will automatically create tables on startup

### 2. Backend (Spring Boot) Setup

1. Navigate to the backend folder:
   ```bash
   cd backend
   ```
2. Build and run the application:
   ```bash
   mvn spring-boot:run
   ```
   - The backend will run on `http://localhost:8080`
   - Drools rules are loaded from `src/main/resources/rules/eligibility_rules.drl`

### 3. Frontend (Angular) Setup

1. Navigate to the frontend folder:
   ```bash
   cd frontend
   ```
2. Install dependencies:
   ```bash
   npm install
   ```
3. Start the development server:
   ```bash
   ng serve
   ```
   - The frontend will run on `http://localhost:4200`

## Key Features

- **Drools Rules Engine**: Business rules defined in `eligibility_rules.drl`
- **Automatic Schema Generation**: Hibernate creates database tables
- **REST API**: Spring Boot exposes endpoints for:
  - User management
  - Loan eligibility checks
  - Application processing

## Development

### Angular Commands

| Command    | Description          |
| ---------- | -------------------- |
| `ng serve` | Start dev server     |
| `ng build` | Production build     |
| `ng test`  | Run unit tests       |
| `ng e2e`   | Run end-to-end tests |

### Spring Boot Commands

| Command               | Description           |
| --------------------- | --------------------- |
| `mvn spring-boot:run` | Run Spring Boot app   |
| `mvn test`            | Run unit tests        |
| `mvn package`         | Create executable JAR |

## Configuration

### Backend Configuration

Edit `application.properties` for:

- Database connection
- Server port
- Hibernate settings
- Drools rule file path

### Frontend Configuration

Edit `environment.ts` for:

- API base URL
- Feature flags
- Application settings

## Troubleshooting

- **Database connection issues**: Verify MySQL credentials in `application.properties`
- **CORS errors**: Ensure backend allows requests from `http://localhost:4200`
- **Rule engine errors**: Check `eligibility_rules.drl` syntax
