package com.alibaba.sample.petstore.biz;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.sample.petstore.dal.dataobject.User;

public class UserManagerTests extends AbstractBizManagerTests {
    @Autowired
    private UserManager userManager;

    @Test
    public void login() {
        User user = userManager.login("j2ee", "j2ee");
        assertEquals("j2ee", user.getUserId());
        assertEquals(null, user.getPassword());
        assertEquals("Palo Alto", user.getAccount().getCity());

        // wrong password
        user = userManager.login("j2ee", "wrongpass");
        assertNull(user);

        // no password
        user = userManager.login("j2ee", null);
        assertNull(user);

        // user not exists
        user = userManager.login("nonexist", null);
        assertNull(user);
    }

    @Test
    public void register() {
        // register basic info
        User user = new User();

        user.setUserId("newuser");
        user.setPassword("newpass");

        userManager.register(user);

        // login
        user = userManager.login("newuser", "newpass");
        assertEquals("newuser", user.getUserId());
        assertEquals(null, user.getPassword());

        assertEquals(null, user.getAccount().getCity());

        // register account
        user.getAccount().setAddress1("address1");
        user.getProfile().setLanguagePreference("chinese");

        userManager.update(user);

        // get user
        user = userManager.getUser("newuser");
        assertEquals("newuser", user.getUserId());
        assertEquals(null, user.getPassword());

        assertEquals("address1", user.getAccount().getAddress1());
        assertEquals("chinese", user.getProfile().getLanguagePreference());
    }

    @Test(expected = DuplicatedUserException.class)
    public void register_duplicated() {
        User user = new User();

        user.setUserId("j2ee");
        user.setPassword("test");

        userManager.register(user);
    }

    @Test
    public void getUser() {
        User user = userManager.getUser("j2ee");
        assertEquals("j2ee", user.getUserId());
        assertEquals(null, user.getPassword());
        assertEquals("Palo Alto", user.getAccount().getCity());

        assertNull(userManager.getUser("nonexist"));
    }
}
