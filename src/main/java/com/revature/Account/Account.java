package com.revature.Account;

import java.math.BigDecimal;

public class Account {

    private int accountId;
    private int userId;
    private BigDecimal balance;
    private AccountType accountType;

    public enum AccountType {
        CHECKING,
        SAVINGS
    }

    // Getters
    public int getAccountId() { return accountId; }
    public int getUserId() { return userId; }
    public BigDecimal getBalance() { return balance; }
    public AccountType getAccountType() { return accountType; }
    public String getAccountTypeAsString() { return accountType != null ? accountType.name() : null; };

    // Setters
    public void setAccountId(int accountId) { this.accountId = accountId; }
    public void setUserId(int userId) { this.userId = userId; }
    public void setBalance(BigDecimal balance) { this.balance = balance; }
    public void setAccountType(AccountType accountType) { this.accountType = accountType; }
    public void setAccountType(String accountType) { this.accountType = AccountType.valueOf(accountType.toUpperCase()); }

}

