/*
 * Copyright (c) 2002-2012 Alibaba Group Holding Limited.
 * All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.alibaba.sample.petstore.biz;

import static org.junit.Assert.*;

import com.alibaba.sample.petstore.dal.dataobject.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

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
