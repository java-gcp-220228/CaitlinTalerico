package com.revature.service;

import com.revature.dao.AccountDao;
import com.revature.dao.ClientDao;
import com.revature.exceptions.AccountNotFoundException;
import com.revature.exceptions.ClientNotFoundException;
import com.revature.model.Account;

import java.sql.SQLException;
import java.util.List;

public class AccountService {

    private final AccountDao accountDao;
    private final ClientDao clientDao;

    public AccountService() {
        this.accountDao = new AccountDao();
        this.clientDao = new ClientDao();
    }

    // Mock dao set-up for unit testing
    public AccountService(AccountDao mockDao, ClientDao mockClientDao) {
        this.accountDao = mockDao;
        this.clientDao = mockClientDao;
    }
    public Account getAccountByID(String clientId, String id) throws SQLException, AccountNotFoundException, ClientNotFoundException {
        int cId = validateIdAndReturnInt(clientId);
        verifyClientExists(cId);
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

    public List<Account> getAllAccounts(String clientId, String minAmount, String maxAmount) throws SQLException, ClientNotFoundException {
        int cId = validateIdAndReturnInt(clientId);
        verifyClientExists(cId);
        double minBalance = validateLong(minAmount);
        double maxBalance = validateLong(maxAmount);

        return accountDao.getAccountsBetween(cId, minBalance, maxBalance);
    }

    public List<Account> getAllAccountsLessThan(String clientId, String maxAmount) throws SQLException, ClientNotFoundException {
        int cId = validateIdAndReturnInt(clientId);
        verifyClientExists(cId);
        double maxBalance = validateLong(maxAmount);
        return accountDao.getAccountsLessThan(cId, maxBalance);
    }

    public List<Account> getAllAccountsGreaterThan(String clientId, String minAmount) throws SQLException, ClientNotFoundException {
        int cId = validateIdAndReturnInt(clientId);
        verifyClientExists(cId);
        double minBalance = validateLong(minAmount);
        return accountDao.getAccountsGreaterThan(cId, minBalance);
    }

    public List<Account> getAllAccountsByType(String clientId, String type) throws SQLException, ClientNotFoundException {
        int cId = validateIdAndReturnInt(clientId);
        verifyClientExists(cId);
        return accountDao.getAccountsByType(cId, type);
    }

    public Account updateAccount(String clientId, String accountId, Account account) throws SQLException, ClientNotFoundException, AccountNotFoundException {

        // Validate client information: Does the client actually exist
        int cId = validateIdAndReturnInt(clientId);
        verifyClientExists(cId);
        int accId = validateIdAndReturnInt(accountId);
        verifyAccountExist(cId, accId);

        // Validate account information follows database standards
        validateAccountInformation(account);

        // If changing the client id of the account
        if (account.getClientId() != cId) {
            verifyClientExists(account.getClientId());
            // Will update the numbering of the other client's accounts
            int currentAccount = accId + 1; // Account updating + 1
            // This will update the account switching clients to follow client's numbering
            account.setAccountNumber(accountDao.getAllAccounts(account.getClientId()).size() + 1);

            // This is the returned, updated account (Overloaded method)
            Account updatedAccount = accountDao.updateAccount(cId, accId, account);

            // The account from the other client whose numbering must be change
            Account changeAccountOtherClient = accountDao.getAccountByAccountID(cId, currentAccount);
            while(changeAccountOtherClient != null) {
                // Set the account number to previous accounts number
                changeAccountOtherClient.setAccountNumber(currentAccount-1);
                // Update account with new number
                accountDao.updateAccount(changeAccountOtherClient, currentAccount);
                // Move onto the next account
                currentAccount++;
                changeAccountOtherClient = accountDao.getAccountByAccountID(cId, currentAccount);
            }
            return updatedAccount;
        }
        // If not changing the client, simply update the account
        account.setAccountNumber(accId);
        account.setClientId(cId);
        return accountDao.updateAccount(account, accId);

    }

    public Account createAccount(String clientId, Account account) throws SQLException, ClientNotFoundException {

        int cId = validateIdAndReturnInt(clientId);
        verifyClientExists(cId);
        account.setClientId(cId);
        validateAccountInformation(account);

        return accountDao.createAccount(account);
    }

    public boolean deleteAccount(String clientId, String id) throws SQLException, ClientNotFoundException, AccountNotFoundException {
        int cId = validateIdAndReturnInt(clientId);
        verifyClientExists(cId);
        int accountNumber = validateIdAndReturnInt(id);
        verifyAccountExist(cId, accountNumber);
        boolean success = accountDao.deleteAccountById(cId, accountNumber);
        if (success) {
            int currentAccount = accountNumber + 1;
            Account updateAccount = accountDao.getAccountByAccountID(cId, currentAccount);
            while (updateAccount != null) {
                updateAccount.setAccountNumber(currentAccount - 1);
                accountDao.updateAccount(updateAccount, currentAccount);
                currentAccount++;
                updateAccount = accountDao.getAccountByAccountID(cId, currentAccount);
            }
        }
        return success;
    }

    public void verifyAccountExist(int clientId, int accountNumber) throws AccountNotFoundException, SQLException {
        if (accountDao.getAccountByAccountID(clientId, accountNumber) == null) {
            throw new AccountNotFoundException("An account with the id of " + accountNumber +
                " does not exist for this client.");
        };

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
            return Integer.parseInt(id);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("A valid number was not provided for the ID.\n" +
                    "Input: " + id);
        }
    }

    public double validateLong(String value) {
        try {
            return Double.parseDouble(value);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("A valid balance was not provided for the ID.\n" +
                    "Input: " + value);
        }
    }
    public void verifyClientExists(int clientId) throws SQLException, ClientNotFoundException {
        if (clientDao.getClientById(clientId) == null)
        {
            throw new ClientNotFoundException("A client with the client ID " + clientId + " does not exist.");
        }
    }
}
