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

    public List<Account> getAllAccounts(String clientId, String minAmount, String maxAmount) throws SQLException {
        int cId = validateIdAndReturnInt(clientId);
        double minBalance = validateLong(minAmount);
        double maxBalance = validateLong(maxAmount);

        return accountDao.getAccountsBetween(cId, minBalance, maxBalance);
    }

    public List<Account> getAllAccountsLessThan(String clientId, String maxAmount) throws SQLException {
        int cId = validateIdAndReturnInt(clientId);
        double maxBalance = validateLong(maxAmount);
        return accountDao.getAccountsLessThan(cId, maxBalance);
    }

    public List<Account> getAllAccountsGreaterThan(String clientId, String minAmount) throws SQLException {
        int cId = validateIdAndReturnInt(clientId);
        double minBalance = validateLong(minAmount);
        return accountDao.getAccountsGreaterThan(cId, minBalance);
    }

    public List<Account> getAllAccountsByType(String clientId, String type) throws SQLException {
        int cId = validateIdAndReturnInt(clientId);
        return accountDao.getAccountsByType(cId, type);
    }

    public Account updateAccount(String clientId, String accountId, Account account) throws SQLException {

        int cId = validateIdAndReturnInt(clientId);
        int accId = validateIdAndReturnInt(accountId);

        account.setClientId(cId);
        account.setAccountNumber(accId);
        validateAccountInformation(account);

        Account updatedAccount = accountDao.updateAccount(account);
        return updatedAccount;
    }

    public Account createAccount(String clientId, Account account) throws SQLException {

        int cId = validateIdAndReturnInt(clientId);
        account.setClientId(cId);
        validateAccountInformation(account);
        account.setAccountNumber(accountDao.getAllAccounts(account.getClientId()).size() + 1);
        Account addedAccount = accountDao.createAccount(account);

        return addedAccount;
    }

    public boolean deleteAccount(String clientId, String id) throws SQLException {
        int cId = validateIdAndReturnInt(clientId);
        int accountNumber = validateIdAndReturnInt(id);

        boolean success = accountDao.deleteAccountById(cId, accountNumber);
        int currentAccount = accountNumber + 1;
        Account updateAccount = accountDao.getAccountByAccountID(cId, currentAccount);
        while(updateAccount != null) {
            updateAccount.setAccountNumber(currentAccount - 1);
            accountDao.updateAccount(updateAccount);
            currentAccount++;
            updateAccount = accountDao.getAccountByAccountID(cId, currentAccount);
        }

        return success;
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

    public double validateLong(String value) {
        try {
            double doubleValue = Double.parseDouble(value);
            return doubleValue;
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("A valid balance was not provided for the ID.\n" +
                    "Input: " + value);
        }
    }
}
