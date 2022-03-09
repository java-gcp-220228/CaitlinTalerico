package com.revature.service;

import com.revature.dao.AccountDao;
import com.revature.model.Account;
import com.revature.model.Client;

import java.util.List;

public class AccountService {

    private AccountDao accountDao;

    public AccountService() {
        this.accountDao = new AccountDao();
    }

    // Mock dao set-up for unit testing
    public AccountService(AccountDao mockDao) {
        this.accountDao = mockDao;
    }
    public Account getAccountByID(String clientId, String id) {
        return null;
    }

    public List<Account> getAllAccounts(String clientId) {
        return null;
    }

    public void updateAccount(String clientId, String id) {

    }

    public void createAccount(String clientId) {

    }

    public void deleteAccount(String clientId, String id) {

    }

    public void validateAccountInformation(Account account) {
        account.setAccountType(account.getAccountType().trim());
        String accountType = account.getAccountType().toLowerCase();
        if (accountType.compareTo("checking") == 0) {
            account.setAccountType("Checking");
        } else if (accountType.compareTo("savings") == 0) {
            account.setAccountType("Savings");
        } else if (accountType.compareTo("mma") == 0) {
            account.setAccountType("MMA");
        } else if (accountType.compareTo("cd") == 0) {
            account.setAccountType("CD");
        } else {
            throw new IllegalArgumentException("That type of account is not supported.\n" +
                    "Valid Accounts: Checking, Savings, MMA, CD\n" +
                    "Input: " + account.getAccountType());
        }

    }
}
