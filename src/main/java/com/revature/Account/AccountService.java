package com.revature.Account;
import com.revature.util.exceptions.DataNotFoundException;
import com.revature.util.interfaces.Serviceable;
import java.util.function.Predicate;
import java.util.ArrayList;
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




}


