package com.revature.controller;

import com.revature.model.Account;
import com.revature.model.Client;
import com.revature.response.ResponseBody;
import com.revature.service.AccountService;
import com.revature.service.ClientService;
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
        String paramType = ctx.queryParam("type");
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
            if (accounts.size() > 0){
                ctx.json(accounts);
            } else {
                ctx.json("No accounts exist for this client.");
            }

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


        Account accountToAdd = ctx.bodyAsClass(Account.class);
        String clientId = ctx.pathParam("clientId");
        Account newAccount = accountService.createAccount(clientId, accountToAdd);
        ctx.status(201);
        ctx.json(newAccount);
    };

    private final Handler updateAccount = (ctx) -> {
        ctx.bodyValidator(Account.class);
        Account accountToEdit = ctx.bodyAsClass(Account.class);

        String clientId = ctx.pathParam("clientId");
        String accountId = ctx.pathParam("accountId");

        Account editedAccount = accountService.updateAccount(clientId, accountId, accountToEdit);
        ctx.status(200);
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
