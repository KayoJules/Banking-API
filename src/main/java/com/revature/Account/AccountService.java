package com.revature.Account;
import com.revature.util.exceptions.DataNotFoundException;
import com.revature.util.interfaces.Serviceable;

import java.util.ArrayList;
import java.util.List;

public class AccountService implements Serviceable<Account> {
    private List<Account> accountList = new ArrayList<>();
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
        accountList.add(newAccount);
        return newAccount;
    }

    @Override
    public Account findById(int accountId) {


        for (Account account : accountList) {
            if (account.getAccountId() == accountId) {
                return account;
            }
        }
        return null;
    }

    public void update(Account updatedAccount) {

        for (int i = 0; i < accountList.size(); i++) {
            if (accountList.get(i).getAccountId() == updatedAccount.getAccountId()) {
                accountList.set(i, updatedAccount);
                return;
            }
        }
        throw new DataNotFoundException("Account with ID provided not within database");
    }





}


