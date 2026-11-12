package com.ledgerline.repository;

import com.ledgerline.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository
        extends JpaRepository<Transaction, Long> {

    boolean existsByTransactionReference(String transactionReference);
}

