package com.alibaba.sample.petstore.dal.dao;

import java.util.List;

import com.alibaba.sample.petstore.dal.dataobject.User;

public interface UserDao {
    User getUserById(String userId);

    User getAuthenticatedUser(String userId, String password);

    List<String> getUserIdList();

    void insertUser(User user);

    void updateUser(User user);
}
