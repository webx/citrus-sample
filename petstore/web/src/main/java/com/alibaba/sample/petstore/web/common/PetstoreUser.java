package com.alibaba.sample.petstore.web.common;

import static com.alibaba.citrus.util.Assert.*;
import static com.alibaba.citrus.util.CollectionUtil.*;
import static com.alibaba.citrus.util.ObjectUtil.*;
import static com.alibaba.citrus.util.StringUtil.*;

import java.io.Serializable;
import java.util.List;

import com.alibaba.citrus.util.Assert.ExceptionType;

public class PetstoreUser implements Serializable {
    private static final long serialVersionUID = -7507510429755782596L;
    private static final ThreadLocal<PetstoreUser> userHolder = new ThreadLocal<PetstoreUser>();
    private String userId;
    private List<String> roles = createArrayList();

    public static final PetstoreUser getCurrentUser() {
        return userHolder.get();
    }

    public static final void setCurrentUser(PetstoreUser user) {
        userHolder.set(user);
    }

    /**
     * 创建匿名用户。
     */
    public PetstoreUser() {
    }

    /**
     * 创建用户。
     */
    public PetstoreUser(String userId) {
        this.userId = trimToNull(userId);
    }

    public String getId() {
        return userId;
    }

    public String[] getRoles() {
        return roles.toArray(new String[roles.size()]);
    }

    public void addRole(String role) {
        roles.add(role);
    }

    public void cleanRoles() {
        roles.clear();
    }

    public void upgrade(String userId) {
        assertTrue(hasLoggedIn(), ExceptionType.ILLEGAL_STATE, "already logged in");

        userId = assertNotNull(trimToNull(userId), "no user id");

        this.userId = userId;
    }

    public boolean hasLoggedIn() {
        return userId != null;
    }

    @Override
    public String toString() {
        return "PetstoreUser[" + defaultIfNull(userId, "anonymous") + "]";
    }
}
