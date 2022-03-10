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
            String sql = "INSERT INTO accounts (account_type, balance, client_id) VALUES (?, ?, ?)";

            PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            pstmt.setString(1, account.getAccountType());
            pstmt.setDouble(2, account.getBalance());
            pstmt.setInt(3, account.getClientId());

            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            rs.next();
            int generatedId = rs.getInt(1);

            return new Account(generatedId, account.getAccountType(), account.getBalance(), account.getClientId(), account.getAccountNumber());

        }

    }

    // R-READ
    public Account getAccountByAccountID(int clientId, int id) throws SQLException {

        try (Connection con = ConnectionUtility.getConnection()) {
            String sql = "SELECT * FROM accounts WHERE clientId = ? AND id = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);

            pstmt.setInt(1, clientId);
            pstmt.setInt(2, id);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String accountType = rs.getString("account_type");
                double balance = rs.getDouble("balance");
                int accountNumber = rs.getInt("account_number");

                return new Account(id, accountType, balance, clientId, accountNumber);
            }
        }
        return null;
    }

    public List<Account> getAllAccounts(int clientId) throws SQLException {
        List<Account> accounts = new ArrayList<>();

        try (Connection con = ConnectionUtility.getConnection())  {
            String sql = "SELECT * FROM accounts WHERE clientId = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);

            pstmt.setInt(1, clientId);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()){
                int id = rs.getInt("id");
                String accountType = rs.getString("account_type");
                double balance = rs.getDouble("balance");
                int accountNumber = rs.getInt("account_number");

                accounts.add(new Account(id, accountType, balance, clientId, accountNumber));
            }
            return accounts;
        }
    }
    public List<Account> getAccountsByType(int clientId, String accountType) throws SQLException {
        List<Account> accounts = new ArrayList<>();

        try (Connection con = ConnectionUtility.getConnection())  {
            String sql = "SELECT * FROM accounts WHERE client_id = ? AND account_type = ?";
            PreparedStatement pstmt = con.prepareStatement(sql);

            ResultSet rs = pstmt.executeQuery();

            pstmt.setInt(1, clientId);
            pstmt.setString(2, accountType);

            while (rs.next()){
                int id = rs.getInt("id");
                double balance = rs.getDouble("balance");
                int accountNumber = rs.getInt("account_number");

                accounts.add(new Account(id, accountType, balance, clientId, accountNumber));
            }
            return accounts;
        }
    }

    public List<Account> getAccountsLessThan(int clientId, double maxBalance) throws SQLException {
        List<Account> accounts = new ArrayList<>();

        try (Connection con = ConnectionUtility.getConnection())  {
            String sql = "SELECT * FROM accounts WHERE clientId = ? AND balance <= ?";
            PreparedStatement pstmt = con.prepareStatement(sql);

            pstmt.setInt(1, clientId);
            pstmt.setDouble(2, maxBalance);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()){
                int id = rs.getInt("id");
                String accountType = rs.getString("account_type");
                double balance = rs.getDouble("balance");
                int accountNumber = rs.getInt("account_number");

                accounts.add(new Account(id, accountType, balance, clientId, accountNumber));
            }
            return accounts;
        }
    }

    public List<Account> getAccountsGreaterThan(int clientId, double minBalance) throws SQLException {
        List<Account> accounts = new ArrayList<>();

        try (Connection con = ConnectionUtility.getConnection())  {
            String sql = "SELECT * FROM accounts WHERE clientId = ? AND balance >= ?";
            PreparedStatement pstmt = con.prepareStatement(sql);

            pstmt.setInt(1, clientId);
            pstmt.setDouble(2, minBalance);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()){
                int id = rs.getInt("id");
                String accountType = rs.getString("account_type");
                double balance = rs.getDouble("balance");
                int accountNumber = rs.getInt("account_number");

                accounts.add(new Account(id, accountType, balance, clientId, accountNumber));
            }
            return accounts;
        }
    }

    public List<Account> getAccountsBetween(int clientId, double minBalance, double maxBalance) throws SQLException {
        List<Account> accounts = new ArrayList<>();

        try (Connection con = ConnectionUtility.getConnection())  {
            String sql = "SELECT * FROM accounts WHERE clientId = ? AND balance <= ? AND balance >= ?";
            PreparedStatement pstmt = con.prepareStatement(sql);

            pstmt.setInt(1, clientId);
            pstmt.setDouble(2, minBalance);
            pstmt.setDouble(3, maxBalance);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()){
                int id = rs.getInt("id");
                String accountType = rs.getString("account_type");
                double balance = rs.getDouble("balance");
                int accountNumber = rs.getInt("account_number");

                accounts.add(new Account(id, accountType, balance, clientId, accountNumber));
            }
            return accounts;
        }
    }

    // U-UPDATE
    public Account updateAccount(Account account) throws SQLException {
        try(Connection con = ConnectionUtility.getConnection()) {
            String sql = "UPDATE accounts SET account_type = ?, balance = ? WHERE client_id = ? AND id = ?";

            PreparedStatement pstmt = con.prepareStatement(sql);

            pstmt.setString(1, account.getAccountType());
            pstmt.setDouble(2, account.getBalance());
            pstmt.setInt(3, account.getClientId());
            pstmt.setInt(4, account.getId());

            pstmt.executeUpdate();
        }

        return account;

    }


    // D-DELETE

    public boolean deleteAccountById(int clientId, int accountId) throws SQLException {
        try (Connection con = ConnectionUtility.getConnection()) {
            String sql = "DELETE FROM accounts WHERE id = ? AND client_id = ?";

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

