
# LedgerLine – AI Usage Documentation

## 1. Introduction

AI assistance was used during the development of LedgerLine to support implementation, debugging, code understanding, testing, and documentation.

AI-generated suggestions were reviewed, adapted, and tested during development.

## 2. AI-Assisted Development Areas

### 2.1 Project Architecture

**Prompt:**

"Suggest a suitable architecture for a secure digital banking application using React, Java Spring Boot, and PostgreSQL."

**Usage:**

Used to plan the frontend, backend, database, controller, service, and repository layers.

### 2.2 Backend Development

**Prompt:**

"Generate Spring Boot REST APIs for bank account creation, account retrieval, deposits, and withdrawals."

**Usage:**

Assisted in developing account management and transaction APIs.

### 2.3 Database Design

**Prompt:**

"Design JPA entities for Account, Transaction, and LedgerEntry using appropriate relationships and monetary data types."

**Usage:**

Assisted in creating database entities and repository interfaces.

### 2.4 Transaction Integrity

**Prompt:**

"How can I prevent negative account balances and concurrent transaction conflicts in a Spring Boot banking application?"

**Usage:**

Helped implement:
- Transactional service methods.
- Account balance validation.
- Pessimistic database locking.
- BigDecimal for monetary calculations.

### 2.5 Security Configuration

**Prompt:**

"Configure Spring Security for REST APIs with stateless sessions and CORS support for a React frontend."

**Usage:**

Assisted in configuring CORS, CSRF settings, and API access rules for the local prototype.

### 2.6 Frontend Development

**Prompt:**

"Create a React banking dashboard with account creation, deposits, withdrawals, account balances, and transaction history."

**Usage:**

Assisted in building the LedgerLine frontend using React and Vite.

### 2.7 Debugging

**Prompt:**

"Help diagnose and resolve CORS errors, HTTP 403 Forbidden responses, and React invalid hook call errors."

**Usage:**

AI assistance helped identify configuration and code placement issues. Fixes were applied and tested in the local application.

### 2.8 Security Testing

**Prompt:**

"Suggest test cases for insufficient balance, negative transaction amounts, zero amounts, and invalid financial transactions."

**Usage:**

Helped prepare and perform basic validation tests.

### 2.9 Documentation

**Prompt:**

"Prepare README, security threat analysis, and AI usage documentation for a digital banking prototype."

**Usage:**

Assisted in preparing project documentation.

## 3. Human Verification

AI-generated code and recommendations were reviewed and tested during implementation.

The following functionality was verified in the local application:

- Account creation.
- Deposit operations.
- Withdrawal operations.
- Balance updates.
- Transaction history.
- Insufficient balance rejection.
- Negative amount rejection.
- Zero amount rejection.

## 4. AI Limitations and Corrections

During development, some AI-generated code required corrections.

Examples:
- Updated CORS configuration to match the actual Vite frontend port.
- Corrected React Hook placement inside the component function.
- Adjusted Spring Security configuration to resolve local API access issues.
- Tested invalid transaction requests and verified backend rejection.

## 5. Responsible AI Usage

AI was used as a development assistant, not as a replacement for testing or engineering decisions.

All reported test results are based on observed local application behavior.

Production security and deployment requirements still require additional implementation and verification.

---

**LedgerLine – Secure Money Movement. Verifiable Ledger.**
