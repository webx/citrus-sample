package com.alibaba.sample.petstore.dal.dao.ibatis;

import static com.alibaba.citrus.util.StringUtil.*;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.alibaba.sample.petstore.dal.dao.UserDao;
import com.alibaba.sample.petstore.dal.dataobject.User;

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
