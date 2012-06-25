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

package com.alibaba.sample.petstore.web.user.module.action;

import static com.alibaba.citrus.util.CollectionUtil.*;
import static com.alibaba.sample.petstore.web.common.PetstoreConstant.*;

import java.util.Map;
import javax.servlet.http.HttpSession;

import com.alibaba.citrus.service.form.CustomErrors;
import com.alibaba.citrus.service.form.Group;
import com.alibaba.citrus.turbine.Navigator;
import com.alibaba.citrus.turbine.dataresolver.FormField;
import com.alibaba.citrus.turbine.dataresolver.FormGroup;
import com.alibaba.sample.petstore.biz.DuplicatedUserException;
import com.alibaba.sample.petstore.biz.UserManager;
import com.alibaba.sample.petstore.dal.dataobject.User;
import com.alibaba.sample.petstore.web.common.PetstoreUser;
import org.springframework.beans.factory.annotation.Autowired;

public class RegisterAction {
    @Autowired
    private UserManager userManager;

    public void doRegister(@FormGroup("register") User user,
                           @FormField(name = "registerError", group = "register") CustomErrors err,
                           HttpSession session, Navigator nav) throws Exception {
        try {
            userManager.register(user);

            // 在session中创建petstoreUser对象
            PetstoreUser petstoreUser = (PetstoreUser) session.getAttribute(PETSTORE_USER_SESSION_KEY);

            if (petstoreUser == null || petstoreUser.hasLoggedIn()) {
                petstoreUser = new PetstoreUser();
            }

            petstoreUser.upgrade(user.getUserId(), user.getRoles());

            session.setAttribute(PETSTORE_USER_SESSION_KEY, petstoreUser);

            // 跳转到registerAccount页面
            nav.redirectTo(PETSTORE_REGISTER_ACCOUNT_LINK);
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

        nav.redirectTo(PETSTORE_ACCOUNT_LINK);
    }
}
