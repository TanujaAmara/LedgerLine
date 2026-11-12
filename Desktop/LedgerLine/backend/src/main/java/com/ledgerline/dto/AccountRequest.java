package com.ledgerline.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class AccountRequest {

    @NotBlank(message = "Account holder name is required")
    @Size(min = 3, max = 100)
    private String accountHolderName;

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public void setAccountHolderName(String accountHolderName) {
        this.accountHolderName = accountHolderName;
    }
}