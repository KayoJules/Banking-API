package com.revature.Account;

import com.revature.util.interfaces.*;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;

import java.util.List;

public class AccountController extends Controller {

    @Override
    public void registerPaths(Javalin app) {
        app.get("/accounts", this::getAllAccounts);
        app.post("/accounts", this::postNewAccount);
        app.get("/flights/{accountNumber}", this::getAccountById);
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
        Account account  =  ctx.bodyAsClass(Account.class);
        ctx.json(accountService.create(account));
        ctx.status(HttpStatus.CREATED);
    }
}
