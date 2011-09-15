package com.alibaba.sample.petstore.web.user.module.action;

import static com.alibaba.citrus.util.CollectionUtil.*;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.form.CustomErrors;
import com.alibaba.citrus.service.form.Group;
import com.alibaba.citrus.turbine.Navigator;
import com.alibaba.citrus.turbine.dataresolver.FormField;
import com.alibaba.citrus.turbine.dataresolver.FormGroup;
import com.alibaba.sample.petstore.biz.DuplicatedUserException;
import com.alibaba.sample.petstore.biz.UserManager;
import com.alibaba.sample.petstore.dal.dataobject.User;
import com.alibaba.sample.petstore.web.common.PetstoreUser;
import com.alibaba.sample.petstore.web.common.WebConstant;

public class RegisterAction {
    @Autowired
    private UserManager userManager;

    public void doRegister(@FormGroup("register") User user,
                           @FormField(name = "registerError", group = "register") CustomErrors err,
                           HttpSession session, Navigator nav) throws Exception {
        try {
            userManager.register(user);

            // 在session中创建petstoreUser对象
            PetstoreUser petstoreUser = (PetstoreUser) session.getAttribute(WebConstant.PETSTORE_USER_SESSION_KEY);

            if (petstoreUser == null || petstoreUser.hasLoggedIn()) {
                petstoreUser = new PetstoreUser();
            }

            petstoreUser.upgrade(user.getUserId());

            session.setAttribute(WebConstant.PETSTORE_USER_SESSION_KEY, petstoreUser);

            // 跳转到registerAccount页面
            nav.redirectTo(WebConstant.PETSTORE_REGISTER_ACCOUNT_LINK);
        } catch (DuplicatedUserException e) {
            Map<String, Object> params = createHashMap();
            params.put("userId", user.getUserId());

            err.setMessage("duplicatedUserId", params);
        }
    }

    public void doRegisterAccount(@FormGroup("registerContact") Group registerContact,
                                  @FormGroup("registerCredit") Group registerCredit, Navigator nav) throws Exception {
        User user = new User();

        user.setUserId(PetstoreUser.getCurrentUser().getId());

        registerContact.setProperties(user);
        registerCredit.setProperties(user);

        userManager.update(user);

        nav.redirectTo(WebConstant.PETSTORE_ACCOUNT_LINK);
    }
}
