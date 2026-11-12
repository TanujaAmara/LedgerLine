
# LedgerLine
### Secure Money Movement with a Verifiable Ledger

LedgerLine is a secure digital banking application designed to manage bank accounts and financial transactions with reliable balance management and transaction records.

The application uses a React frontend, Java Spring Boot backend, and PostgreSQL database.

## 1. Problem Statement

Financial applications must maintain accurate account balances and protect transactions against common issues such as:

- Duplicate or invalid transactions
- Withdrawals exceeding available balance
- Negative or zero transaction amounts
- Concurrent account updates
- Incomplete transaction records

LedgerLine addresses these challenges through backend validation, database transactions, and ledger-based transaction recording.

## 2. Key Features

### Account Management
- Create new bank accounts.
- Generate unique account numbers.
- View registered accounts.
- Check available balances.

### Financial Transactions
- Deposit money.
- Withdraw money.
- Validate transaction amounts.
- Prevent withdrawals exceeding available balance.
- Maintain transaction references.

### Transaction Ledger
- Store transaction records.
- Record deposit and withdrawal types.
- Maintain transaction status and timestamps.
- Display transaction history.

### Security and Validation
- Backend amount validation.
- Insufficient balance validation.
- CORS configuration.
- Stateless Spring Security configuration.

## 3. Technology Stack

| Component | Technology |
|---|---|
| Frontend | React.js |
| Frontend Tool | Vite |
| Backend | Java 21 |
| Framework | Spring Boot |
| Database | PostgreSQL |
| ORM | Spring Data JPA / Hibernate |
| Security | Spring Security |
| Build Tool | Maven |

## 4. System Architecture

```text
       USER
        |
        v
  React Frontend
        |
        | REST API
        v
  Spring Boot Backend
        |
        v
  Service Layer
        |
        v
  Repository Layer
        |
        v
    PostgreSQL
        |
        v
 Accounts | Transactions | Ledger Entries
```

## 5. Project Structure

```text
LedgerLine/
|
|-- backend/
|   |-- src/
|   |   |-- main/
|   |       |-- java/com/ledgerline/
|   |       |   |-- config/
|   |       |   |-- controller/
|   |       |   |-- dto/
|   |       |   |-- entity/
|   |       |   |-- repository/
|   |       |   |-- service/
|   |       |-- resources/
|   |           |-- application.properties
|   |-- pom.xml
|
|-- frontend/
|   |-- src/
|   |   |-- App.jsx
|   |   |-- App.css
|   |-- package.json
|
|-- README.md
|-- THREATS.md
|-- PROMPTS.md
```

## 6. API Endpoints

| Method | Endpoint | Description |
|---|---|---|
| GET | /api/health | Backend health check |
| POST | /api/accounts | Create account |
| GET | /api/accounts | List accounts |
| GET | /api/accounts/{id} | Get account details |
| POST | /api/ledger/deposit | Deposit money |
| POST | /api/ledger/withdraw | Withdraw money |
| GET | /api/ledger/transactions | View transaction history |

## 7. Installation and Execution

### Prerequisites
- Java 21
- Node.js and npm
- PostgreSQL
- Maven (or included Maven Wrapper)

### Backend Setup

1. Create a PostgreSQL database named `ledgerline_db`.
2. Configure database connection details using local environment configuration.
3. Open the backend directory.

```bash
cd backend
```

4. Run the Spring Boot application.

```bash
./mvnw spring-boot:run
```

On Windows PowerShell:

```powershell
.\mvnw.cmd spring-boot:run
```

Backend URL:

```text
http://localhost:8080
```

### Frontend Setup

Open another terminal.

```bash
cd frontend
npm install
npm run dev
```

Frontend URL:

```text
http://localhost:5174
```

Use the actual Vite port displayed in the terminal if it differs.

## 8. Security Testing

The following tests were performed during development:

| Test Case | Expected Result | Observed Result |
|---|---|---|
| Account creation | Account created | Passed |
| Valid deposit | Balance increases | Passed |
| Valid withdrawal | Balance decreases | Passed |
| Insufficient balance | Withdrawal rejected | Passed |
| Negative amount | Request rejected | Passed |
| Zero amount | Request rejected | Passed |

## 9. Future Enhancements

- JWT-based authentication and authorization.
- Role-based access control.
- Idempotency keys for duplicate request prevention.
- Stronger double-entry accounting validation.
- Improved concurrent transaction handling.
- Audit logs and monitoring.
- Cloud deployment.

## 10. Disclaimer

LedgerLine is a hackathon prototype and demonstration application. It is not intended for processing real financial transactions or storing real banking credentials.

Authentication, authorization, production-grade auditing, and additional security controls must be implemented and independently tested before public deployment.

---

**LedgerLine – Secure Money Movement. Verifiable Ledger.**
