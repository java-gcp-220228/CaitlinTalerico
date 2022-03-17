package com.revature.dao;

import com.revature.model.Account;
import com.revature.utlity.ConnectionUtility;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDao {

    //CRUD Operations

    // C-CREATE

    public Account createAccount(Account account) throws SQLException {
        try (Connection con = ConnectionUtility.getConnection()) {

            account.setAccountNumber(new AccountDao().getAllAccounts(account.getClientId()).size() + 1);

            String sql = "INSERT INTO accounts (account_type, balance, client_id, account_number) VALUES (?, ?, ?, ?)";

            PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            pstmt.setString(1, account.getAccountType());
            pstmt.setDouble(2, account.getBalance());
            pstmt.setInt(3, account.getClientId());
            pstmt.setInt(4, account.getAccountNumber());

            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            rs.next();


            return new Account(account.getAccountType(), account.getBalance(), account.getClientId(), account.getAccountNumber());

        }

    }

    // R-READ
    public Account getAccountByAccountID(int clientId, int id) throws SQLException {

        try (Connection con = ConnectionUtility.getConnection()) {
            String sql = "SELECT * FROM accounts WHERE client_id = ? AND account_number = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);

            pstmt.setInt(1, clientId);
            pstmt.setInt(2, id);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String accountType = rs.getString("account_type");
                double balance = rs.getDouble("balance");
                int accountNumber = rs.getInt("account_number");

                return new Account(accountType, balance, clientId, accountNumber);
            }
        }
        return null;
    }

    public List<Account> getAllAccounts(int clientId) throws SQLException {
        List<Account> accounts = new ArrayList<>();

        try (Connection con = ConnectionUtility.getConnection())  {
            String sql = "SELECT * FROM accounts WHERE client_id = ? ORDER BY account_number";
            PreparedStatement pstmt = con.prepareStatement(sql);

            pstmt.setInt(1, clientId);
            con.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()){
                String accountType = rs.getString("account_type");
                double balance = rs.getDouble("balance");
                int accountNumber = rs.getInt("account_number");

                accounts.add(new Account(accountType, balance, clientId, accountNumber));
            }
            return accounts;
        }
    }
    public List<Account> getAccountsByType(int clientId, String accountType) throws SQLException {
        List<Account> accounts = new ArrayList<>();

        try (Connection con = ConnectionUtility.getConnection())  {
            String sql = "SELECT * FROM accounts WHERE client_id = ? AND account_type = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);



            pstmt.setInt(1, clientId);
            pstmt.setString(2, (accountType.substring(0,1).toUpperCase() + accountType.substring(1).toLowerCase()));
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()){
                int id = rs.getInt("id");
                double balance = rs.getDouble("balance");
                int accountNumber = rs.getInt("account_number");

                accounts.add(new Account(accountType, balance, clientId, accountNumber));
            }
            return accounts;
        }
    }

    public List<Account> getAccountsLessThan(int clientId, double maxBalance) throws SQLException {
        List<Account> accounts = new ArrayList<>();

        try (Connection con = ConnectionUtility.getConnection())  {
            String sql = "SELECT * FROM accounts WHERE client_id = ? AND balance <= ? ORDER BY balance";
            PreparedStatement pstmt = con.prepareStatement(sql);

            pstmt.setInt(1, clientId);
            pstmt.setDouble(2, maxBalance);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()){
                int id = rs.getInt("id");
                String accountType = rs.getString("account_type");
                double balance = rs.getDouble("balance");
                int accountNumber = rs.getInt("account_number");

                accounts.add(new Account(accountType, balance, clientId, accountNumber));
            }
            return accounts;
        }
    }

    public List<Account> getAccountsGreaterThan(int clientId, double minBalance) throws SQLException {
        List<Account> accounts = new ArrayList<>();

        try (Connection con = ConnectionUtility.getConnection())  {
            String sql = "SELECT * FROM accounts WHERE client_id = ? AND balance >= ? ORDER BY balance DESC";
            PreparedStatement pstmt = con.prepareStatement(sql);

            pstmt.setInt(1, clientId);
            pstmt.setDouble(2, minBalance);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()){
                String accountType = rs.getString("account_type");
                double balance = rs.getDouble("balance");
                int accountNumber = rs.getInt("account_number");

                accounts.add(new Account(accountType, balance, clientId, accountNumber));
            }
            return accounts;
        }
    }

    public List<Account> getAccountsBetween(int clientId, double minBalance, double maxBalance) throws SQLException {
        List<Account> accounts = new ArrayList<>();

        try (Connection con = ConnectionUtility.getConnection())  {
            String sql = "SELECT * FROM accounts WHERE client_id = ? AND balance >=? AND balance <= ? ORDER BY balance";
            PreparedStatement pstmt = con.prepareStatement(sql);

            pstmt.setInt(1, clientId);
            pstmt.setDouble(2, minBalance);
            pstmt.setDouble(3, maxBalance);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()){
                String accountType = rs.getString("account_type");
                double balance = rs.getDouble("balance");
                int accountNumber = rs.getInt("account_number");

                accounts.add(new Account(accountType, balance, clientId, accountNumber));
            }
            return accounts;
        }
    }

    // U-UPDATE
    public Account updateAccount(Account account, int accNum) throws SQLException {
        try(Connection con = ConnectionUtility.getConnection()) {
            con.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);

            String sql = "UPDATE accounts SET account_type = ?, balance = ?, account_number = ? WHERE client_id = ? AND account_number = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);

            pstmt.setString(1, account.getAccountType());
            pstmt.setDouble(2, account.getBalance());
            pstmt.setInt(3, account.getAccountNumber());
            pstmt.setInt(4, account.getClientId());
            pstmt.setInt(5, accNum);

            pstmt.executeUpdate();
        }

        return account;
    }
    public Account updateAccount(int clientId, int accountId, Account account) throws SQLException {
        try(Connection con = ConnectionUtility.getConnection()) {
            con.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);

            String sql = "UPDATE accounts SET account_type = ?, balance = ?, account_number = ?, client_id = ? WHERE client_id = ? AND account_number = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);

            pstmt.setString(1, account.getAccountType());
            pstmt.setDouble(2, account.getBalance());
            pstmt.setInt(3, account.getAccountNumber());
            pstmt.setInt(4, account.getClientId());
            pstmt.setInt(5, clientId);
            pstmt.setInt(6, accountId);


            pstmt.executeUpdate();
        }

        return account;
    }

    // D-DELETE

    public boolean deleteAccountById(int clientId, int accountId) throws SQLException {
        try (Connection con = ConnectionUtility.getConnection()) {
            String sql = "DELETE FROM accounts WHERE account_number = ? AND client_id = ?";

            PreparedStatement pstmt = con.prepareStatement(sql);

            pstmt.setInt(1, accountId);
            pstmt.setInt(2, clientId);

            int numberOfRecordsDelete = pstmt.executeUpdate();

            if (numberOfRecordsDelete == 1) {
                return true;
            }
        }
        return false;

    }
}

