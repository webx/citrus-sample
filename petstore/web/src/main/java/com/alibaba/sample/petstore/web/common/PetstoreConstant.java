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

/**
 * Petstore WEB层的常量。
 *
 * @author Michael Zhou
 */
public interface PetstoreConstant {
    /** 在session中保存petstore用户对象的key。 */
    String PETSTORE_USER_SESSION_KEY = "petstoreUser";

    /** 在session中保存shopping cart对象的key。 */
    String PETSTORE_CART_KEY = "petstoreCart";

    /** Login页面返回URL的key。 */
    String LOGIN_RETURN_KEY = "return";

    /** 如果未指定return，登录以后就跳到该URL。 */
    String LOGIN_RETURN_DEFAULT_LINK = "petstoreHomeLink";

    /** 登录URL的名字。 */
    String PETSTORE_LOGIN_LINK = "petstoreLoginLink";

    /** 登记用户URL的名字。 */
    String PETSTORE_REGISTER_LINK = "petstoreRegisterLink";

    /** 登记用户信息URL的名字。 */
    String PETSTORE_REGISTER_ACCOUNT_LINK = "petstoreRegisterAccountLink";

    /** 查看用户信息URL的名字。 */
    String PETSTORE_ACCOUNT_LINK = "petstoreAccountLink";
}
