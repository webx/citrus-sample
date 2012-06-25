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

package com.alibaba.sample.petstore.web.common;

import static com.alibaba.citrus.util.Assert.*;
import static com.alibaba.citrus.util.BasicConstant.*;
import static com.alibaba.citrus.util.ObjectUtil.*;
import static com.alibaba.citrus.util.StringUtil.*;

import java.io.Serializable;

public class PetstoreUser implements Serializable {
    private static final long                      serialVersionUID = -7507510429755782596L;
    private static final ThreadLocal<PetstoreUser> userHolder       = new ThreadLocal<PetstoreUser>();
    private String   userId;
    private String[] roles;

    public static final PetstoreUser getCurrentUser() {
        return userHolder.get();
    }

    public static final void setCurrentUser(PetstoreUser user) {
        userHolder.set(user);
    }

    /** 创建匿名用户。 */
    public PetstoreUser() {
    }

    /** 创建用户。 */
    public PetstoreUser(String userId) {
        this.userId = trimToNull(userId);
    }

    public String getId() {
        return userId;
    }

    public String[] getRoles() {
        return defaultIfNull(roles, EMPTY_STRING_ARRAY);
    }

    public void setRoles(String[] roles) {
        this.roles = roles;
    }

    public void upgrade(String userId, String[] roles) {
        assertTrue(!hasLoggedIn(), ExceptionType.ILLEGAL_STATE, "already logged in");

        userId = assertNotNull(trimToNull(userId), "no user id");

        this.userId = userId;
        this.roles = roles;
    }

    public boolean hasLoggedIn() {
        return userId != null;
    }

    @Override
    public String toString() {
        return "PetstoreUser[" + defaultIfNull(userId, "anonymous") + ", roles=" + join(getRoles(), ":") + "]";
    }
}
