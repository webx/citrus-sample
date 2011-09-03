package com.alibaba.sample.petstore.dal.dataobject;

public class User {
    private String userId;
    private String password;
    private final Account account = new Account(this);
    private final Profile profile = new Profile(this);

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Account getAccount() {
        return account;
    }

    public Profile getProfile() {
        return profile;
    }
}
