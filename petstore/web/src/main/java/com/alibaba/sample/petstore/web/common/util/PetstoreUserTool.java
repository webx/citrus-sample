package com.alibaba.sample.petstore.web.common.util;

import com.alibaba.citrus.service.pull.ToolFactory;
import com.alibaba.sample.petstore.web.common.PetstoreUser;

public class PetstoreUserTool implements ToolFactory {
    public boolean isSingleton() {
        return false;
    }

    public Object createTool() throws Exception {
        return PetstoreUser.getCurrentUser();
    }
}
