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

package com.alibaba.sample.petstore.dal.dao.ibatis;

import static com.alibaba.citrus.util.StringUtil.*;

import java.util.List;

import com.alibaba.sample.petstore.dal.dao.UserDao;
import com.alibaba.sample.petstore.dal.dataobject.User;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class IbatisUserDao extends SqlMapClientDaoSupport implements UserDao {
    public User getUserById(String userId) {
        return (User) getSqlMapClientTemplate().queryForObject("getUserByUserId", userId);
    }

    public User getAuthenticatedUser(String userId, String password) {
        User user = new User();

        user.setUserId(userId);
        user.setPassword(password);

        return (User) getSqlMapClientTemplate().queryForObject("getUserByUserIdAndPassword", user);
    }

    @SuppressWarnings("unchecked")
    public List<String> getUserIdList() {
        return getSqlMapClientTemplate().queryForList("getUserIdList", null);
    }

    public void insertUser(User user) {
        getSqlMapClientTemplate().update("insertUser", user);
        getSqlMapClientTemplate().update("insertAccount", user);
        getSqlMapClientTemplate().update("insertProfile", user);
    }

    public void updateUser(User user) {
        getSqlMapClientTemplate().update("updateAccount", user);
        getSqlMapClientTemplate().update("updateProfile", user);

        if (!isEmpty(user.getPassword())) {
            getSqlMapClientTemplate().update("updateUser", user);
        }
    }
}
