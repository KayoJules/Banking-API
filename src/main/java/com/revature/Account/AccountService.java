package com.revature.Account;
import com.revature.util.exceptions.DataNotFoundException;
import com.revature.util.interfaces.Serviceable;

import java.math.BigDecimal;
import java.util.function.Predicate;
import java.util.List;

public class AccountService implements Serviceable<Account> {
    private Predicate<String> isNotEmpty = str -> str != null && !str.isBlank();
    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public List<Account> findAll() {
        List<Account> accounts = accountRepository.findAll();
        if (accounts.isEmpty()) {
            throw new DataNotFoundException("No account information available");
        } else {
            return accounts;
        }
    }

    @Override
    public Account create(Account newAccount) {
        return accountRepository.create(newAccount);
    }

    @Override
    public Account findById(int accountId) {
        return accountRepository.findById(accountId);
    }

    public boolean update(Account updatedAccount) {
        return accountRepository.update(updatedAccount);
    }

    public boolean delete(Account removedAccount) {
        return accountRepository.delete(removedAccount);
    }

    public boolean deposit(int accountId, BigDecimal amount) {
        return accountRepository.deposit(accountId, amount);
    }

    public boolean withdraw(int accountId, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be greater than zero");
        }

        BigDecimal currentBalance = accountRepository.getBalance(accountId);
        if (currentBalance == null) {
            throw new IllegalArgumentException("Account not found");
        }

        if (currentBalance.compareTo(amount) < 0) {
            throw new IllegalArgumentException("Insufficient funds");
        }
        return accountRepository.withdraw(accountId, amount);
    }

    public List<Account> getAccountsByUserId(int userId) {
        return accountRepository.getAccountsByUserId(userId);
    }

}


