package com.ledgerline.service;

import com.ledgerline.entity.Account;
import com.ledgerline.dto.AccountRequest;
import com.ledgerline.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account createAccount(AccountRequest request) {

        Account account = new Account();

        account.setAccountHolderName(request.getAccountHolderName());

        String accountNumber = "LL" +
                UUID.randomUUID().toString()
                .replace("-", "")
                .substring(0, 12)
                .toUpperCase();

        account.setAccountNumber(accountNumber);
        account.setBalance(BigDecimal.ZERO);
        account.setStatus("ACTIVE");

        return accountRepository.save(account);
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    public Optional<Account> getAccountById(Long id) {
        return accountRepository.findById(id);
    }
}
