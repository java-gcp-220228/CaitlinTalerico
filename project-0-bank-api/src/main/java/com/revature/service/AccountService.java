package com.revature.service;

import com.revature.dao.AccountDao;
import com.revature.exceptions.AccountNotFoundException;
import com.revature.model.Account;

import java.sql.SQLException;
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
    public Account getAccountByID(String clientId, String id) throws SQLException, AccountNotFoundException {

        int cId = validateIdAndReturnInt(clientId);
        int accountId = validateIdAndReturnInt(id);

        Account account = accountDao.getAccountByAccountID(cId, accountId);

        if (account == null) {
            throw new AccountNotFoundException("An account with the id of " + accountId +
                    " does not exist for this client.");
        }
        return account;
    }

    public List<Account> getAllAccounts(String clientId) throws SQLException {

        int cId = validateIdAndReturnInt(clientId);
        return accountDao.getAllAccounts(cId);
    }

    public Account updateAccount(Account account) throws SQLException {

        validateAccountInformation(account);

        Account updatedAccount = accountDao.updateAccount(account);
        return updatedAccount;
    }

    public Account createAccount(Account account) throws SQLException {

        validateAccountInformation(account);
        Account addedAccount = accountDao.createAccount(account);

        return addedAccount;
    }

    public boolean deleteAccount(String clientId, String id) throws SQLException {
        int cId = validateIdAndReturnInt(clientId);
        int accountId = validateIdAndReturnInt(id);

        return accountDao.deleteAccountById(cId, accountId);
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

    public int validateIdAndReturnInt(String id) {
        try {
            int intId = Integer.parseInt(id);
            return intId;
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("A valid number was not provided for the ID.\n" +
                    "Input: " + id);
        }
    }
}
