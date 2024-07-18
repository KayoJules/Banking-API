package com.revature.Account;
import com.revature.util.exceptions.DataNotFoundException;
import com.revature.util.interfaces.Serviceable;

import java.util.ArrayList;
import java.util.List;

public class AccountService implements Serviceable<Account> {
    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public List<Account> findAll() {
        return null;
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


