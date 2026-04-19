# Transaction System

A Spring Boot–based backend system for managing **Accounts** and **Transactions**, built with a focus on clean architecture, database migrations, and containerized deployment.

---

# 🚀 Features

* Account creation and retrieval
* Transaction creation
* PostgreSQL as the database
* Flyway for schema migrations
* Dockerized setup for easy local development
* Swagger/OpenAPI for API documentation

---

# 🏗️ Tech Stack

* Java 17
* Spring Boot 3.x
* Spring Data JPA
* PostgreSQL
* Flyway
* Docker & Docker Compose
* Gradle
* Springdoc OpenAPI (Swagger)

---

# 📦 Project Structure

```
src/main/java        → Application source code
src/main/resources
  ├── application.properties
  └── db/migration   → Flyway migration scripts

Dockerfile
docker-compose.yml
build.gradle
```

---

# ⚙️ Prerequisites

Make sure you have:

* Java 17+
* Docker & Docker Compose installed
* Gradle (optional, wrapper included)

---

# 🐳 Running with Docker (Recommended)

## 1. Build & Start Services

```bash
docker-compose up --build
```

This will:

* Start PostgreSQL
* Build the Spring Boot app
* Run the application on port **8080**

---

## 2. Stop Services

```bash
docker-compose down
```

---

## 3. Clean Start (reset DB)

```bash
docker-compose down -v
docker-compose up --build
```

---

# 🐘 Database Configuration

PostgreSQL runs with:

* DB: `transaction_db`
* User: `postgres`
* Password: `postgres`
* Port: `5432`

---

# 🛠️ Flyway Migrations

Migration scripts are located at:

```
src/main/resources/db/migration
```

Example:

* `V1__create_accounts.sql`
* `V2__create_transactions.sql`
* `V3__add_transactions_account_fk.sql`

These are automatically executed on application startup.

---

# 🌐 API Documentation (Swagger)

Swagger UI is available at:

```
http://localhost:8080/swagger-ui/index.html
```

---

# 📘 API Overview

## 🔹 Accounts

### Create Account

```
POST /accounts
```

Request:

```json
{
  "document_number": "123456789001"
}
```

---

### Get Account

```
GET /accounts/{id}
```

---

## 🔹 Transactions

### Create Transaction

```
POST /transactions
```

Request:

```json
{
  "accountId": "3031db51-e275-4031-81e1-ad92c222a96c",
  "operationTypeId": 4,
  "amount": 100.00
}
```


# 📄 OpenAPI Spec (Reference)

```yaml
openapi: 3.0.1
info:
  title: Transaction System API
  version: 1.0

paths:
  /accounts:
    post:
      summary: Create account
      requestBody:
        required: true
        content:
          application/json:
            schema:
              type: object
              properties:
                name:
                  type: string
                balance:
                  type: number
      responses:
        '200':
          description: Account created

  /accounts/{id}:
    get:
      summary: Get account
      parameters:
        - name: id
          in: path
          required: true
          schema:
            type: string
            format: uuid
      responses:
        '200':
          description: Account details

  /transactions:
    post:
      summary: Create transaction
      requestBody:
        required: true
        content:
          application/json:
            schema:
              type: object
              properties:
                accountId:
                  type: string
                  format: uuid
                amount:
                  type: number
      responses:
        '200':
          description: Transaction created

  /transactions/account/{accountId}:
    get:
      summary: Get transactions by account
      parameters:
        - name: accountId
          in: path
          required: true
          schema:
            type: string
            format: uuid
      responses:
        '200':
          description: List of transactions
```

---

# 🧪 Sample Curl Commands

## Create Account

```bash
curl -X POST "http://localhost:8080/accounts" \
  -H "Content-Type: application/json" \
  -d '{
    "document_number": "123456789001"
  }'
```

---

## Create Transaction

```bash
curl -X POST "http://localhost:8080/transactions" \
  -H "Content-Type: application/json" \
  -d '{
    "accountId": "3031db51-e275-4031-81e1-ad92c222a96c",
    "operationTypeId": 4,
    "amount": 100.00
  }'
```