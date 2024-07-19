package com.revature.Account;

import com.revature.util.exceptions.InvalidInputException;
import com.revature.util.interfaces.Controller;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

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
        app.post("/accounts/{accountId}/deposit", this::deposit);
        app.post("/accounts/{accountId}/withdraw", this::withdraw);
        app.get("/users/{userId}/accounts", this::getAccountsByUserId);
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

    // TODO: Review
    public void postNewAccount(Context ctx) {
        Account account = ctx.bodyAsClass(Account.class);
        ctx.json(accountService.create(account));
        ctx.status(HttpStatus.CREATED);
    }

    private void deposit(Context ctx) {
        int accountId = Integer.parseInt(ctx.pathParam("accountId"));
        BigDecimal amount = new BigDecimal(ctx.bodyAsClass(Map.class).get("amount").toString());


        boolean success = accountService.deposit(accountId, amount);
        ctx.status(200).result("Deposit successful");
    }

    private void withdraw(Context ctx) {
        int accountId = Integer.parseInt(ctx.pathParam("accountId"));
        BigDecimal amount = new BigDecimal(ctx.bodyAsClass(Map.class).get("amount").toString());

        try {
            boolean success = accountService.withdraw(accountId, amount);
            if (success) {
                ctx.status(200).result("Withdrawal successful");
            } else {
                ctx.status(400).result("Withdrawal failed");
            }
        } catch (IllegalArgumentException e) {
            ctx.status(400).result(e.getMessage());
        } catch (Exception e) {
            ctx.status(500).result("Internal Server Error");
            e.printStackTrace();
        }
    }

    private void getAccountsByUserId(Context ctx) {
        int userId = Integer.parseInt(ctx.pathParam("userId"));
        List<Account> accounts = accountService.getAccountsByUserId(userId);

        if (accounts.isEmpty()) {
            ctx.status(404).result("No accounts found for user with ID: " + userId);
        } else {
            ctx.json(accounts);
        }
    }

}
