package com.alibaba.sample.petstore.web.common;

/**
 * Petstore WEB层的常量。
 * 
 * @author Michael Zhou
 */
public interface WebConstant {
    /** Petstore前台的ACL realm。 */
    String ACCESS_STORE_REALM = "store";

    /** Petstore后台的ACL realm。 */
    String ACCESS_ADMIN_REALM = "admin";

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
