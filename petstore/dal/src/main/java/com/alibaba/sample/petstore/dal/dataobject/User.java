package com.alibaba.sample.petstore.dal.dataobject;

import static com.alibaba.citrus.util.StringUtil.*;

public class User {
    private String userId;
    private String password;
    private String[] roles;
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

    public String[] getRoles() {
        return roles;
    }

    public void setRoles(String[] roles) {
        this.roles = roles;
    }

    public String getRole() {
        return join(roles, ", ");
    }

    public void setRole(String roles) {
        this.roles = split(roles, ", ");
    }

    public Account getAccount() {
        return account;
    }

    public Profile getProfile() {
        return profile;
    }
}
