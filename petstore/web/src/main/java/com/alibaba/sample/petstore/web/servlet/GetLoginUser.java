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
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/plain; charset=UTF-8");

        PrintWriter out = response.getWriter();
        PetstoreUser user = (PetstoreUser) request.getSession().getAttribute(PETSTORE_USER_SESSION_KEY);

        if (user.hasLoggedIn()) {
            out.print(user.getId());
        } else {
            out.print("guest");
        }

        out.flush();
    }
}
