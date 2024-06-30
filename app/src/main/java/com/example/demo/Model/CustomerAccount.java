package com.example.demo.Model;

import com.example.demo.Common;

import java.util.Map;
import java.util.Objects;

public class CustomerAccount {
    private String userName;
    private String email;
    private String password;
    private String phoneNumber;
    private double balance;
    private int Id;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return userName + "::" + email + "::" + password + "::" + phoneNumber + "::" + balance;
    }

    public CustomerAccount getAccount(String username) {
        for (Map.Entry<String, CustomerAccount> entry : Common.customers.entrySet()) {
            if (Objects.equals(entry.getValue().getUserName(), username)) {
                return entry.getValue();
            }
        }
        return null;
    }
}
