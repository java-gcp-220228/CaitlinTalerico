package com.revature.dao;

import com.revature.model.Account;
import com.revature.utlity.ConnectionUtility;
import org.eclipse.jetty.server.handler.ContextHandler;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDao {

    // Create CRUD operations

    // C- CREATE
//    public Account createAccount(Account account) throws SQLException {
//        try (Connection con = ConnectionUtility.getConnection()) {
//            String sql = "INSERT INTO accounts (account_type, client_id) VALUES (?, ?)";
//
//            PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
//
//            pstmt.setString(1, account.getAccountType());
//            pstmt.setInt(2, account.getClientId());
//
//            pstmt.executeUpdate();
//
//            ResultSet rs = pstmt.getGeneratedKeys();
//            rs.next();
//            int generateId = rs.getInt(1);
//
//            return new Account(generateId, account.getAccountType(), account.getBalance(), account.getClientId());
//
//
//
//        }
//
//    }

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

    // U-UPDATE
    public void updateAccount() {

    }


    // D-DELETE

    public void deleteAccount() {

    }




}
