package com.revature.Account;

import com.revature.util.exceptions.InvalidInputException;
import com.revature.util.interfaces.Controller;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;

import java.util.List;

public class AccountController implements Controller {

    private final AccountService accountService;

    // Constructors - Dependency Injection - any dependent objects are provided at initialization
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    // No idea what this is for
    // private Predicate<String> isNotEmpty = str -> str != null && !str.isBlank();

    @Override
    public void registerPaths(Javalin app) {
        app.get("/flights/{accountNumber}", this::getAccountById);
        app.get("/accounts", this::getAllAccounts);
        app.post("/accounts", this::postNewAccount);
        app.put("/accounts", this::putUpdateAccount);
    }

    private void putUpdateAccount(Context ctx) {
        Account updatedAccount = ctx.bodyAsClass(Account.class);

        try {
            if (accountService.update(updatedAccount)) {
                ctx.status(HttpStatus.ACCEPTED);

            } else {
                ctx.status(HttpStatus.BAD_REQUEST);
            }
        } catch (InvalidInputException e) {
            e.printStackTrace();
        }
    }

    public void getAccountById(Context ctx) {
        int accountNumber = Integer.parseInt(ctx.pathParam("accountNumber"));
        Account foundAccount = accountService.findById(accountNumber);
        ctx.json(foundAccount);
    }

    public void getAllAccounts(Context ctx) {
        List<Account> accounts = accountService.findAll();
        ctx.json(accounts);
    }

    public void postNewAccount(Context ctx) {
        Account account = ctx.bodyAsClass(Account.class);
        ctx.json(accountService.create(account));
        ctx.status(HttpStatus.CREATED);
    }
}
