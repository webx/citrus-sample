package com.alibaba.sample.petstore.web.common;

import static com.alibaba.citrus.util.Assert.*;
import static com.alibaba.citrus.util.StringUtil.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.uribroker.URIBrokerService;
import com.alibaba.citrus.service.uribroker.uri.URIBroker;
import com.alibaba.citrus.springext.support.BeanSupport;
import com.alibaba.citrus.turbine.TurbineRunData;
import com.alibaba.citrus.turbine.pipeline.valve.PageAuthorizationValve.Callback;

public class PageAuthorizationCallback extends BeanSupport implements Callback<PageAuthorizationCallback.Status> {
    @Autowired
    private URIBrokerService uriBrokerService;

    private String sessionKey;
    private String brokerId;
    private String returnKey;

    public void setSessionKey(String sessionKey) {
        this.sessionKey = trimToNull(sessionKey);
    }

    public void setRedirectURI(String brokerId) {
        this.brokerId = trimToNull(brokerId);
    }

    public void setReturnKey(String returnKey) {
        this.returnKey = trimToNull(returnKey);
    }

    @Override
    public void init() {
        assertNotNull(uriBrokerService, "could not get URIBrokerService");

        if (sessionKey == null) {
            sessionKey = WebConstant.PETSTORE_USER_SESSION_KEY;
        }

        if (brokerId == null) {
            brokerId = WebConstant.PETSTORE_LOGIN_LINK;
        }

        if (returnKey == null) {
            returnKey = WebConstant.LOGIN_RETURN_KEY;
        }
    }

    public Status onStart(TurbineRunData rundata) {
        HttpSession session = rundata.getRequest().getSession();
        PetstoreUser user = (PetstoreUser) session.getAttribute(sessionKey);

        if (user == null) {
            // 创建匿名用户
            user = new PetstoreUser();
            session.setAttribute(sessionKey, user);
        }

        // 将user设置到rundata中，以便其它程序使用。
        PetstoreUser.setCurrentUser(user);

        return new Status(rundata, user);
    }

    public String getUserName(Status status) {
        return status.user.getId();
    }

    public String[] getRoleNames(Status status) {
        return status.user.getRoles();
    }

    public String[] getActions(Status status) {
        return null;
    }

    public void onAllow(Status status) throws Exception {
    }

    public void onDeny(Status status) throws Exception {
        HttpServletRequest request = status.rundata.getRequest();
        HttpServletResponse response = status.rundata.getResponse();
        URIBroker redirectURI = uriBrokerService.getURIBroker(brokerId);

        assertNotNull(redirectURI, "no URI broker found: %s", brokerId);

        StringBuffer buf = request.getRequestURL();
        String queryString = trimToNull(request.getQueryString());

        if (queryString != null) {
            buf.append('?').append(queryString);
        }

        String returnURL = buf.toString();

        response.sendRedirect(redirectURI.addQueryData(returnKey, returnURL).render());
    }

    static class Status {
        private final TurbineRunData rundata;
        private PetstoreUser user;

        public Status(TurbineRunData rundata, PetstoreUser user) {
            this.rundata = rundata;
            this.user = user;
        }
    }
}
