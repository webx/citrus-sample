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

package com.alibaba.sample.petstore.web.servlet;

import static com.alibaba.sample.petstore.web.common.PetstoreConstant.*;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.sample.petstore.web.common.PetstoreUser;

/**
 * 这个简单的servlet用来演示如何在普通的servlet中访问webx所管理的request/response/session等对象。你需要在webx
 * framework filter的配置中，为这个servlet添加一个passthru过滤。
 *
 * @author Michael Zhou
 */
public class GetLoginUser extends HttpServlet {
    private static final long serialVersionUID = 8852358774890544749L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/plain; charset=UTF-8");

        PrintWriter out = response.getWriter();
        PetstoreUser user = (PetstoreUser) request.getSession().getAttribute(PETSTORE_USER_SESSION_KEY);

        if (user != null && user.hasLoggedIn()) {
            out.print(user.getId());
        } else {
            out.print("guest");
        }

        out.flush();
    }
}
