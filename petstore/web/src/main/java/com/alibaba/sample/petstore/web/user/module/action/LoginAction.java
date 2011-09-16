package com.alibaba.sample.petstore.web.user.module.action;

import static com.alibaba.sample.petstore.web.common.PetstoreConstant.*;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.form.CustomErrors;
import com.alibaba.citrus.service.requestcontext.parser.ParameterParser;
import com.alibaba.citrus.turbine.Navigator;
import com.alibaba.citrus.turbine.dataresolver.FormField;
import com.alibaba.citrus.turbine.dataresolver.FormGroup;
import com.alibaba.citrus.util.StringUtil;
import com.alibaba.sample.petstore.biz.UserManager;
import com.alibaba.sample.petstore.dal.dataobject.User;
import com.alibaba.sample.petstore.web.common.PetstoreUser;

public class LoginAction {
    @Autowired
    private UserManager userManager;

    public void doLogin(@FormGroup("login") User user,
                        @FormField(name = "loginError", group = "login") CustomErrors err, Navigator nav,
                        HttpSession session, ParameterParser params) throws Exception {
        user = userManager.login(user.getUserId(), user.getPassword());

        if (user != null) {
            // 在session中创建petstoreUser对象
            PetstoreUser petstoreUser = (PetstoreUser) session.getAttribute(PETSTORE_USER_SESSION_KEY);

            if (petstoreUser == null || petstoreUser.hasLoggedIn()) {
                petstoreUser = new PetstoreUser();
            }

            petstoreUser.upgrade(user.getUserId());

            session.setAttribute(PETSTORE_USER_SESSION_KEY, petstoreUser);

            // 跳转到return页面
            redirectToReturnPage(nav, params);
        } else {
            err.setMessage("invalidUserOrPassword");
        }
    }

    public void doLogout(HttpSession session, Navigator nav, ParameterParser params) throws Exception {
        // 清除session中的user
        session.removeAttribute(PETSTORE_USER_SESSION_KEY);

        // 跳转到return页面
        redirectToReturnPage(nav, params);
    }

    private void redirectToReturnPage(Navigator nav, ParameterParser params) {
        String returnURL = params.getString("return");

        if (StringUtil.isEmpty(returnURL)) {
            nav.redirectTo(LOGIN_RETURN_DEFAULT_LINK);
        } else {
            nav.redirectToLocation(returnURL);
        }
    }
}
