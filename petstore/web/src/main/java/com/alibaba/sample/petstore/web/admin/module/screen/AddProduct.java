package com.alibaba.sample.petstore.web.admin.module.screen;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alibaba.sample.petstore.biz.AdminManager;
import com.alibaba.sample.petstore.dal.dataobject.Category;

public class AddProduct {
    @Autowired
    private AdminManager adminManager;

    public void execute(@Param("categoryId") String categoryId, Context context) throws Exception {
        Category category = adminManager.getCategory(categoryId);

        context.put("category", category);
    }
}
