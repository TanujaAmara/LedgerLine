package com.ledgerline.service;

import com.ledgerline.dto.DepositRequest;
import com.ledgerline.dto.WithdrawalRequest;
import com.ledgerline.entity.Account;
import com.ledgerline.entity.LedgerEntry;
import com.ledgerline.entity.Transaction;
import com.ledgerline.repository.AccountRepository;
import com.ledgerline.repository.LedgerEntryRepository;
import com.ledgerline.repository.TransactionRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;

@Service
public class LedgerService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final LedgerEntryRepository ledgerEntryRepository;

    public LedgerService(
            AccountRepository accountRepository,
            TransactionRepository transactionRepository,
            LedgerEntryRepository ledgerEntryRepository) {

        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.ledgerEntryRepository = ledgerEntryRepository;
    }

    @Transactional
    public Transaction deposit(DepositRequest request) {

        if (request.getAmount() == null ||
                request.getAmount().compareTo(new BigDecimal("0.01")) < 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Invalid deposit amount");
        }

        Account account = accountRepository
                .findByIdForUpdate(request.getAccountId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Account not found"));

        if (!"ACTIVE".equals(account.getStatus())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Account is not active");
        }

        account.setBalance(
                account.getBalance().add(request.getAmount()));

        Transaction transaction = new Transaction();
        transaction.setTransactionType("DEPOSIT");
        transaction.setAmount(request.getAmount());
        transaction.setStatus("SUCCESS");
        transaction.setDescription("Cash deposit");

        transactionRepository.save(transaction);

        LedgerEntry credit = new LedgerEntry(
                transaction, account, "CREDIT", request.getAmount());

        ledgerEntryRepository.save(credit);
        accountRepository.save(account);

        return transaction;
    }

    @Transactional
    public Transaction withdraw(WithdrawalRequest request) {

        if (request.getAmount() == null ||
                request.getAmount().compareTo(new BigDecimal("0.01")) < 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Invalid withdrawal amount");
        }

        Account account = accountRepository
                .findByIdForUpdate(request.getAccountId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Account not found"));

        if (!"ACTIVE".equals(account.getStatus())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Account is not active");
        }

        if (account.getBalance().compareTo(request.getAmount()) < 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Insufficient account balance");
        }

        account.setBalance(
                account.getBalance().subtract(request.getAmount()));

        Transaction transaction = new Transaction();
        transaction.setTransactionType("WITHDRAWAL");
        transaction.setAmount(request.getAmount());
        transaction.setStatus("SUCCESS");
        transaction.setDescription("Cash withdrawal");

        transactionRepository.save(transaction);

        LedgerEntry debit = new LedgerEntry(
                transaction, account, "DEBIT", request.getAmount());

        ledgerEntryRepository.save(debit);
        accountRepository.save(account);

        return transaction;
    }
}

