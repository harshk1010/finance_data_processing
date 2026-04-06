# Finance Data Processing — Backend API

A Spring Boot 3 REST backend for managing financial records with role-based access control, JWT authentication, and dashboard summary APIs.

---

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 21 |
| Framework | Spring Boot 3.5.13 |
| Security | Spring Security + JWT (jjwt 0.12.5) |
| Persistence | Spring Data JPA + Hibernate 6 |
| Database | PostgreSQL 16 |
| Validation | Jakarta Bean Validation |
| Documentation | Springdoc OpenAPI (Swagger UI) |
| Build Tool | Maven |
| Utilities | Lombok |

---

## Architecture

The project follows a clean layered architecture separating frontend (HTTP) concerns from backend (business logic) concerns.

```
HTTP Request
    ↓
Controller  (frontend/controller)
    ↓
FinanceService  (backend/service)
    ↓
Activity  →  Validator + Processor  (backend/service/activity, validator, processor)
    ↓
Accessor  (backend/data/accessor)
    ↓
Repository  (backend/data/repository)
    ↓
PostgreSQL
```

Each operation flows through:
- **Validator** — validates input, checks business rules
- **Processor** — executes the business logic and calls the accessor
- **Accessor** — data access abstraction over the JPA repository
- **Transformer** — maps between service models and JPA entities

---

## Project Structure

```
src/main/java/com/zorvyn/finance/
├── FinanceApplication.java
├── backend/
│   ├── data/
│   │   ├── accessor/           — IUserDataAccessor, IFinancialRecordAccessor interfaces
│   │   │   ├── impl/           — UserDataSqlAccessor, FinancialRecordSqlAccessor
│   │   │   └── transformer/    — ServiceToDataModelTransformer
│   │   ├── constants/          — RoleType, UserStatus, RecordType, RecordCategory
│   │   ├── model/              — JPA entities (User, FinancialRecord)
│   │   └── repository/         — Spring Data JPA repositories
│   ├── module/                 — Spring @Bean wiring (ActivityModule, ProcessorModule, etc.)
│   └── service/
│       ├── activity/           — AbstractActivity and concrete activities
│       ├── model/              — Request/Response/Common service models
│       ├── processor/          — Business logic processors
│       ├── validator/          — Input validators
│       └── exception/          — Custom exception classes
└── frontend/
    ├── auth/                   — JWT filter, token provider, @CurrentUser annotation
    ├── controller/             — REST controllers + GlobalExceptionHandler
    └── module/                 — SecurityModule, PasswordEncoderConfig, OpenApiConfig, ServiceModule
```

---

## Prerequisites

- Java 21+
- Maven 3.8+
- PostgreSQL 14+

---

## Database Setup

Create the database before starting the application:

```sql
CREATE DATABASE dashboard;
```

The schema is auto-managed by Hibernate (`spring.jpa.hibernate.ddl-auto=update`). Tables are created automatically on first startup.

If you are running the application for the first time on an existing database that has a `users` table without `updated_by_user_id`, run:

```sql
ALTER TABLE users ADD COLUMN IF NOT EXISTS updated_by_user_id BIGINT;
ALTER TABLE financial_records ADD COLUMN IF NOT EXISTS updated_by_user_id BIGINT;
```

---

## Configuration

Edit `src/main/resources/application.properties`:

```properties
# Database
spring.datasource.url=jdbc:postgresql://localhost:5432/dashboard
spring.datasource.username=postgres
spring.datasource.password=your_password

# JWT
app.jwt.secret=finance-dashboard-super-secure-secret-key-123456789
app.jwt.expiration-ms=86400000   # 24 hours

# Server
server.port=8080
```

> **Note:** The default config connects to port `1025`. Change to `5432` if using a standard PostgreSQL installation.

---

## Running the Application

```bash
# Clone and navigate to project
cd finance-dashboard

# Build
mvn clean compile

# Run
mvn spring-boot:run
```

Application starts on `http://localhost:8080`.

Swagger UI available at: `http://localhost:8080/swagger-ui.html`

API docs (JSON) available at: `http://localhost:8080/api-docs`

---

## Authentication

The API uses JWT Bearer token authentication.

### Flow

```
POST /api/user/create   →  create first admin (no token required)
POST /api/auth/login    →  get JWT token
Authorization: Bearer <token>  →  pass on all subsequent requests
```

### Login

```http
POST /api/auth/login
Content-Type: application/json

{
    "email": "admin@example.com",
    "password": "Admin@1234"
}
```

**Response:**
```json
{
    "token": "eyJhbGciOiJIUzI1NiJ9...",
    "expiresIn": 86400000,
    "role": "ADMIN"
}
```

---

## Roles

| Role | Description |
|---|---|
| `ADMIN` | Full access — create, read, update, delete users and records |
| `ANALYST` | Can read financial records and access dashboard summaries |
| `VIEWER` | Can only view dashboard summary data |

---

## API Reference

### Auth

| Method | Endpoint | Auth Required | Description |
|---|---|---|---|
| POST | `/api/auth/login` | No | Login and get JWT token |

### User Management

| Method | Endpoint | Required Role | Description |
|---|---|---|---|
| POST | `/api/user/create` | None (open) | Create a new user |
| GET | `/api/user/get` | ADMIN | List users with optional filters |
| POST | `/api/user/update` | ADMIN | Update user details |
| POST | `/api/user/delete` | ADMIN | Soft delete a user (sets status to INACTIVE) |

### Financial Records

| Method | Endpoint | Required Role | Description |
|---|---|---|---|
| POST | `/api/finance/create` | ADMIN | Create a financial record |
| GET | `/api/finance/get` | ADMIN, ANALYST | List records with filters |
| POST | `/api/finance/update` | ADMIN | Update a financial record |
| POST | `/api/finance/delete` | ADMIN | Soft delete a record |

### Dashboard Summary

| Method | Endpoint | Required Role | Description |
|---|---|---|---|
| GET | `/api/analysis` | ADMIN, ANALYST, VIEWER | Get financial summary |

---

## Request & Response Examples

### Create User
```http
POST /api/user/create
Content-Type: application/json

{
    "email": "admin@zorvyn.com",
    "password": "Admin@1234",
    "firstName": "Super",
    "lastName": "Admin",
    "roleType": "ADMIN"
}
```

### Create Financial Record
```http
POST /api/finance/create
Authorization: Bearer <token>
Content-Type: application/json

{
    "amount": 75000.00,
    "type": "INCOME",
    "category": "SALARY",
    "transactionTime": "2026-04-01T09:00:00",
    "description": "Monthly salary April 2026"
}
```

Valid values for `type`: `INCOME`, `EXPENSE`

Valid values for `category`: `SALARY`, `UTILITIES`, `TRAVEL`, `BONUS`

### Get Financial Records (with filters)
```http
GET /api/finance/get?recordTypes=INCOME&startTime=2026-04-01T00:00:00&endTime=2026-04-30T23:59:59&pageSize=10
Authorization: Bearer <token>
```

### Dashboard Summary
```http
GET /api/analysis?startTime=2026-04-01T00:00:00&endTime=2026-04-30T23:59:59
Authorization: Bearer <token>
```

**Response:**
```json
{
    "totalIncome": 90000.00,
    "totalExpenses": 11700.00,
    "netBalance": 78300.00,
    "categoryTotals": {
        "SALARY": 75000.00,
        "BONUS": 15000.00,
        "UTILITIES": 3500.00,
        "TRAVEL": 8200.00
    },
    "recentActivity": [
        {
            "id": 4,
            "amount": 8200.00,
            "type": "EXPENSE",
            "category": "TRAVEL",
            "transactionTime": "2026-04-04T14:00:00",
            "description": "Business trip to Mumbai"
        }
    ],
    "monthlyTrends": {
        "2026-04": {
            "income": 90000.00,
            "expenses": 11700.00,
            "net": 78300.00
        }
    }
}
```

### Update User
```http
POST /api/user/update
Authorization: Bearer <token>
Content-Type: application/json

{
    "id": 2,
    "firstName": "Updated",
    "lastName": "Name",
    "roleType": "ANALYST"
}
```

### Delete Financial Record
```http
POST /api/finance/delete
Authorization: Bearer <token>
Content-Type: application/json

{
    "id": 3
}
```

---

## Pagination

All list endpoints support cursor-based pagination via `lastAccessedKey` and `pageSize`.

```http
GET /api/finance/get?pageSize=10
# Returns first 10 records and lastAccessedKey in response

GET /api/finance/get?pageSize=10&lastAccessedKey=10
# Returns next 10 records after id=10
```

---

## Error Handling

All errors are returned as RFC 7807 `ProblemDetail` JSON:

```json
{
    "type": "about:blank",
    "title": "Bad Request",
    "status": 400,
    "detail": "Email already registered",
    "instance": "/api/user/create"
}
```

| Status | Cause |
|---|---|
| 400 | Validation failure (missing fields, invalid format, duplicate email, etc.) |
| 401 | Missing or invalid JWT token |
| 403 | Authenticated but insufficient role |
| 500 | Internal server error or data access failure |

---

## Data Persistence Notes

- **Soft deletes** are used for both users and financial records. Deleting a user sets `status = INACTIVE`. Deleting a financial record sets `deleted = true`. Neither operation removes data from the database.
- **Password hashing** — all passwords are hashed with BCrypt before storage. Plain text passwords are never persisted.
- **Auto-generated IDs** — both `users` and `financial_records` use `IDENTITY` strategy for primary key generation.

---

## Optional Features Implemented

| Feature | Details |
|---|---|
| JWT Authentication | Stateless token-based auth, 24-hour expiry |
| Role-based access | Three roles enforced via `@PreAuthorize` on every endpoint |
| Pagination | Cursor-based with `lastAccessedKey` + `pageSize` |
| Soft delete | Users and records are never hard deleted |
| Input validation | All requests validated before processing |
| API documentation | Swagger UI at `/swagger-ui.html` with Bearer auth support |
| Dashboard summaries | Total income/expenses, net balance, category totals, recent activity, monthly trends |
| Search/filtering | Filter records by type, category, amount range, date range |
