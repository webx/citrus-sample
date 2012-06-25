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

package com.alibaba.sample.petstore.dal.dao;

import static com.alibaba.citrus.util.ArrayUtil.*;
import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

import com.alibaba.sample.petstore.dal.dataobject.Account;
import com.alibaba.sample.petstore.dal.dataobject.Profile;
import com.alibaba.sample.petstore.dal.dataobject.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserDaoTests extends AbstractDataAccessTests {
    @Autowired
    private UserDao userDao;

    @Test
    public void getUserById() {
        User user = userDao.getUserById("j2ee");
        assertUser(user);

        user = userDao.getUserById("admin");
        assertUser(user, "admin");
    }

    @Test
    public void getAuthenticatedUser() {
        User user = userDao.getAuthenticatedUser("j2ee", "j2ee");
        assertUser(user);

        user = userDao.getAuthenticatedUser("admin", "admin");
        assertUser(user, "admin");

        // wrong password
        user = userDao.getAuthenticatedUser("j2ee", "wrongpass");
        assertNull(user);

        // no password
        user = userDao.getAuthenticatedUser("j2ee", null);
        assertNull(user);

        // user not exists
        user = userDao.getAuthenticatedUser("nonexist", null);
        assertNull(user);
    }

    @Test
    public void insert_update() {
        // init user ids
        assertUserIdList(userDao.getUserIdList(), "admin", "j2ee");

        // insert new user
        User user = userDao.getUserById("j2ee");

        user.setUserId("myuser");
        user.setPassword("mypass");
        user.setRole("r1, r2");

        userDao.insertUser(user);

        // check user ids again
        assertUserIdList(userDao.getUserIdList(), "admin", "j2ee", "myuser");

        // check new user
        user = userDao.getAuthenticatedUser("myuser", "mypass");
        assertEquals("myuser", user.getUserId());
        assertUser(user, false, "r1", "r2");

        // change password
        user.setPassword("newpass");
        userDao.updateUser(user);

        assertNull(userDao.getAuthenticatedUser("myuser", "mypass"));

        user = userDao.getAuthenticatedUser("myuser", "newpass");
        assertEquals("myuser", user.getUserId());
        assertUser(user, false, "r1", "r2");

        // update other data
        user.getAccount().setCity("newcity");
        user.getAccount().setCreditCardExpiryMonth(1);
        user.getAccount().setCreditCardExpiryYear(2011);
        user.getProfile().setLanguagePreference("chinese");
        userDao.updateUser(user);

        user = userDao.getAuthenticatedUser("myuser", "newpass");
        assertEquals("myuser", user.getUserId());
        assertEquals("newcity", user.getAccount().getCity());
        assertEquals("2011-01-15", new SimpleDateFormat("yyyy-MM-dd").format(user.getAccount().getCreditCardExpiry()));
        assertEquals(1, user.getAccount().getCreditCardExpiryMonth());
        assertEquals(2011, user.getAccount().getCreditCardExpiryYear());
        assertEquals("chinese", user.getProfile().getLanguagePreference());
    }

    private void assertUserIdList(List<String> userIdList, String... ids) {
        String[] result = userIdList.toArray(new String[userIdList.size()]);

        Arrays.sort(result);
        Arrays.sort(ids);

        assertArrayEquals(ids, result);
    }

    private void assertUser(User user, String... roles) {
        assertUser(user, true, roles);
    }

    private void assertUser(User user, boolean checkUserId, String... roles) {
        Account account = user.getAccount();
        Profile profile = user.getProfile();

        // user
        if (checkUserId) {
            assertTrue(arrayContains(new String[] { "j2ee", "admin" }, user.getUserId()));
        }

        assertEquals(null, user.getPassword()); // 密码不可查询

        if (isEmptyArray(roles)) {
            assertTrue(isEmptyArray(user.getRoles()));
        } else {
            assertArrayEquals(roles, user.getRoles());
        }

        // account
        assertEquals("yourname@yourdomain.com", account.getEmail());
        assertEquals("ABC", account.getFirstName());
        assertEquals("XYX", account.getLastName());
        assertEquals("OK", account.getStatus());
        assertEquals("901 San Antonio Road", account.getAddress1());
        assertEquals("MS UCUP02-206", account.getAddress2());
        assertEquals("Palo Alto", account.getCity());
        assertEquals("CA", account.getState());
        assertEquals("94303", account.getZip());
        assertEquals("US", account.getCountry());
        assertEquals("555-555-5555", account.getPhone());
        assertEquals("1234567", account.getCreditCardNumber());
        assertEquals("Visa", account.getCreditCardType());
        assertEquals("2005-12-15", new SimpleDateFormat("yyyy-MM-dd").format(account.getCreditCardExpiry()));
        assertEquals(12, account.getCreditCardExpiryMonth());
        assertEquals(2005, account.getCreditCardExpiryYear());

        // profile
        assertEquals("english", profile.getLanguagePreference());
        assertEquals("DOGS", profile.getFavoriteCategoryId());
    }
}
