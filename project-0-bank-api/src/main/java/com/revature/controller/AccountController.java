package com.revature.controller;

import com.revature.model.Account;
import com.revature.response.ResponseBody;
import com.revature.service.AccountService;
import io.javalin.Javalin;
import io.javalin.http.Handler;

import java.util.List;

public class AccountController implements Controller{

    private AccountService accountService;

    public AccountController() {
        this.accountService = new AccountService();
    }

    private final Handler getAllAccounts = (ctx) -> {
        String paramMaxBalance = ctx.queryParam("amountLessThan");
        String paramMinBalance = ctx.queryParam("amountGreaterThan");
        String paramType = ctx.queryParam("Type");
        String clientId = ctx.pathParam("clientId");

        if (paramMaxBalance != null) {
            if (paramMinBalance != null) {
                List<Account> accounts = accountService.getAllAccounts(clientId, paramMinBalance, paramMaxBalance);
                ctx.json(200);
                ctx.json(accounts);
            } else {
                List<Account> accounts = accountService.getAllAccountsLessThan(clientId, paramMaxBalance);
                ctx.status(200);
                ctx.json(accounts);
            }
        } else if (paramMinBalance != null){
            List<Account> accounts = accountService.getAllAccountsGreaterThan(clientId, paramMinBalance);
            ctx.status(200);
            ctx.json(accounts);
        } else if (paramType != null) {
            List<Account> accounts = accountService.getAllAccountsByType(clientId, paramType);
            ctx.status(200);
            ctx.json(accounts);
        } else {
            List<Account> accounts = accountService.getAllAccounts(clientId);
            ctx.status(200);
            ctx.json(accounts);
        }
    };

    private final Handler getAccountById = (ctx) -> {
        String clientId = ctx.pathParam("clientId");
        String accountId = ctx.pathParam("accountId");
        Account account = accountService.getAccountByID(clientId, accountId);
        ctx.status(200);
        ctx.json(account);
    };

    private final Handler addAccount = (ctx) -> {
        String accountType = ctx.formParam("account_type");
        String newBalance = ctx.formParam("balance");
        System.out.println(ctx.body().contains("account_type"));
        System.out.println(accountType);
        System.out.println(newBalance);

        Account accountToAdd = new Account();
        if (accountType != null) {
            if (newBalance != null)
                try{
                    double balance = Double.parseDouble(ctx.formParam("balance"));
                    accountToAdd.setBalance(balance);
                } catch (IllegalArgumentException e){
                    throw new IllegalArgumentException("Invalid balance provided.\n" +
                            "Input: " + ctx.formParam("balance"));
                }
            String clientId = ctx.pathParam("clientId");
            accountToAdd.setAccountType(accountType);
            Account newAccount = accountService.createAccount(clientId, accountToAdd);
            ctx.status(201);
            ctx.json(newAccount);
        } else {
            ctx.status(400);
            ctx.json("Improper request body provided. Please use the template below to form your request.\n" +
                    "{\n" +
                    "   \"account_type\" : \"\",\n" +
                    "    \"balance\" : \"(optional)\",\n" +
                    "}");
        }
    };

    private final Handler updateAccount = (ctx) -> {
        ctx.bodyValidator(Account.class);
        Account accountToEdit = ctx.bodyAsClass(Account.class);

        String clientId = ctx.pathParam("clientId");
        String accountId = ctx.pathParam("accountId");

        Account editedAccount = accountService.updateAccount(clientId, accountId, accountToEdit);
        ctx.status(201);
        ctx.json(editedAccount);
    };

    private final Handler deleteAccount = (ctx) -> {
          String clientId = ctx.pathParam("clientId");
          String accountId = ctx.pathParam("accountId");

          ResponseBody responseBody = ResponseBody.deleteAccount(accountService.deleteAccount(clientId, accountId));
          ctx.status(200);
          ctx.json(responseBody);
    };



    @Override
    public void mapEndpoints(Javalin app) {
        app.get("/clients/{clientId}/accounts", getAllAccounts);
        app.get("/clients/{clientId}/accounts/{accountId}", getAccountById);
        app.post("/clients/{clientId}/accounts", addAccount);
        app.put("/clients/{clientId}/accounts/{accountId}", updateAccount);
        app.delete("/clients/{clientId}/accounts/{accountId}", deleteAccount);
    }
}
