
# LedgerLine – Security Threat Analysis

## 1. Introduction

LedgerLine is a digital banking prototype that handles account creation, deposits, withdrawals, and transaction records.

Financial applications must protect transaction integrity, account balances, and sensitive information.

This document identifies potential security threats, their impact, and the implemented or planned mitigation strategies.

## 2. Identified Security Threats

### Threat 1: Negative or Zero Amount Transactions

**Risk:** An attacker may attempt to deposit or withdraw zero or negative amounts.

**Impact:** Incorrect balance calculations and invalid transaction records.

**Mitigation:**
- Validate transaction amounts on the backend.
- Reject amounts below ₹0.01.
- Use BigDecimal for monetary calculations.

**Testing:** Negative and zero amount requests were rejected.

### Threat 2: Insufficient Balance Withdrawal

**Risk:** A user may attempt to withdraw more money than the available account balance.

**Impact:** Account balance may become negative.

**Mitigation:**
- Check available balance before withdrawal.
- Reject withdrawals exceeding the available balance.
- Update balance within a database transaction.

**Testing:** An attempted withdrawal exceeding the available balance was rejected with HTTP 400.

### Threat 3: Concurrent Transactions

**Risk:** Multiple transactions may update the same account simultaneously.

**Impact:** Incorrect balances or lost updates.

**Mitigation:**
- Use database transactions with @Transactional.
- Use pessimistic write locking when retrieving accounts for deposit and withdrawal.

**Status:** Locking implemented in the account repository and used by deposit and withdrawal services. Concurrent stress testing remains pending.

### Threat 4: Duplicate Transaction Requests

**Risk:** A client may accidentally submit the same transaction multiple times.

**Impact:** Duplicate deposits or withdrawals.

**Mitigation / Future Improvement:**
- Introduce idempotency keys.
- Enforce uniqueness of transaction request identifiers.
- Test repeated requests.

**Status:** Dedicated idempotency protection is not yet implemented.

### Threat 5: Unauthorized Account Access

**Risk:** A user may attempt to access or modify another user's account.

**Impact:** Unauthorized financial activity and exposure of account information.

**Mitigation / Future Improvement:**
- Implement user authentication.
- Add account ownership verification.
- Introduce role-based authorization.

**Status:** Authentication and account-level authorization are pending. Current API permissions are intended only for local demonstration.

### Threat 6: Cross-Origin Requests

**Risk:** Untrusted websites may attempt to send requests to the backend from a browser.

**Impact:** Unauthorized cross-origin interactions.

**Mitigation:**
- Configure allowed frontend origins.
- Restrict allowed HTTP methods and headers.
- Use Spring Security CORS configuration.

**Status:** Local frontend origin is configured for development.

### Threat 7: Database Credential Exposure

**Risk:** Database credentials may be exposed through source code or repository uploads.

**Impact:** Unauthorized database access.

**Mitigation / Future Improvement:**
- Store credentials in environment variables.
- Never commit secrets to GitHub.
- Rotate exposed credentials.
- Use a secrets manager in production.

**Status:** Production-grade secret management is pending.

## 3. Security Testing Summary

| Test | Expected Result | Observed Result |
|---|---|---|
| Valid deposit | Transaction succeeds | Passed |
| Valid withdrawal | Transaction succeeds | Passed |
| Insufficient balance | Reject withdrawal | Passed |
| Negative amount | Reject request | Passed |
| Zero amount | Reject request | Passed |

## 4. Known Limitations

The current application is a hackathon prototype.

The following protections require further development and testing:

- User authentication and authorization.
- Idempotency protection.
- Independent double-entry ledger balance verification.
- Concurrent transaction stress testing.
- Production-grade audit logging.
- Secure deployment and secrets management.

## 5. Conclusion

LedgerLine incorporates backend transaction validation, database transaction management, account locking, and transaction history.

These controls provide a foundation for transaction integrity. Additional authentication, authorization, testing, and production security controls are necessary before handling real financial data.

---

**LedgerLine – Secure Money Movement. Verifiable Ledger.**
