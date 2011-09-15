package com.alibaba.sample.petstore.web.user.module.screen;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.sample.petstore.biz.UserManager;
import com.alibaba.sample.petstore.dal.dataobject.User;
import com.alibaba.sample.petstore.web.common.PetstoreUser;

public class Account {
    @Autowired
    private UserManager userManager;

    public void execute(Context context) throws Exception {
        User user = userManager.getUser(PetstoreUser.getCurrentUser().getId());
        context.put("user", user);
    }
}
