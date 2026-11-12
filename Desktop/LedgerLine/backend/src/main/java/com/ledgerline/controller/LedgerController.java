
package com.ledgerline.controller;

import com.ledgerline.dto.DepositRequest;
import com.ledgerline.dto.WithdrawalRequest;
import com.ledgerline.entity.Transaction;
import com.ledgerline.repository.TransactionRepository;
import com.ledgerline.service.LedgerService;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5174")
@RestController
@RequestMapping("/api/ledger")
public class LedgerController {

    private final LedgerService ledgerService;
    private final TransactionRepository transactionRepository;

    public LedgerController(
            LedgerService ledgerService,
            TransactionRepository transactionRepository) {
        this.ledgerService = ledgerService;
        this.transactionRepository = transactionRepository;
    }

    @PostMapping("/deposit")
    public ResponseEntity<Transaction> deposit(
            @Valid @RequestBody DepositRequest request) {
        return ResponseEntity.ok(ledgerService.deposit(request));
    }

    @PostMapping("/withdraw")
    public ResponseEntity<Transaction> withdraw(
            @Valid @RequestBody WithdrawalRequest request) {
        return ResponseEntity.ok(ledgerService.withdraw(request));
    }

    @GetMapping("/transactions")
    public ResponseEntity<List<Transaction>> getTransactions() {
        return ResponseEntity.ok(transactionRepository.findAll());
    }
}
