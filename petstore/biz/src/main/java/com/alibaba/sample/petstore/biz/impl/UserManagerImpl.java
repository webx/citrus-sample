package com.alibaba.sample.petstore.biz.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.sample.petstore.biz.DuplicatedUserException;
import com.alibaba.sample.petstore.biz.UserManager;
import com.alibaba.sample.petstore.dal.dao.UserDao;
import com.alibaba.sample.petstore.dal.dataobject.User;

/**
 * 有关用户的操作。
 * 
 * @author Michael Zhou
 */
public class UserManagerImpl implements UserManager {
    @Autowired
    private UserDao userDao;

    /**
     * 登录用户，如果用户名和密码正确，则返回相应的用户信息。
     * 
     * @param userId 用户名
     * @param password 密码
     * @return 用户信息，如果用户名或密码不正确，则返回<code>null</code>
     */
    public User login(String userId, String password) {
        return userDao.getAuthenticatedUser(userId, password);
    }

    /**
     * 注册用户。
     * 
     * @param user 用户对象
     * @return 新的用户信息
     */
    public void register(User user) throws DuplicatedUserException {
        User dupuser = userDao.getUserById(user.getUserId());

        if (dupuser != null) {
            throw new DuplicatedUserException("duplicated user: " + user.getUserId());
        }

        userDao.insertUser(user);
    }

    /**
     * 更新用户的信息。
     * 
     * @param user 用户对象
     * @return 新的用户信息
     */
    public void update(User user) {
        userDao.updateUser(user);
    }

    /**
     * 取得指定id的用户。
     * 
     * @param userId 用户id
     * @return 指定id的用户
     */
    public User getUser(String userId) {
        return userDao.getUserById(userId);
    }
}
