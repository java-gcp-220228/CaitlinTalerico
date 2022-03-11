package com.revature.model;

import java.util.Objects;

public class Account {
    private int id;
    private String accountType;
    private Double balance;
    private int clientId;
    private int accountNumber;

    public Account() {

    }

    public Account(int id, String accountType, double balance, int clientId, int accountNumber) {
        this.id = id;
        this.accountType = accountType;
        this.balance = balance;
        this.clientId = clientId;
        this.accountNumber = accountNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return id == account.id && clientId == account.clientId && Objects.equals(accountType, account.accountType) && Objects.equals(balance, account.balance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, accountType, balance, clientId);
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", accountType='" + accountType + '\'' +
                ", balance=" + balance +
                ", clientId=" + clientId +
                '}';
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }
}
