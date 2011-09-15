package com.alibaba.sample.petstore.web.store.module.screen;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alibaba.sample.petstore.biz.StoreManager;
import com.alibaba.sample.petstore.dal.dataobject.Category;

public class AddProduct {
    @Autowired
    private StoreManager storeManager;

    public void execute(@Param("categoryId") String categoryId, Context context) throws Exception {
        Category category = storeManager.getCategory(categoryId);

        context.put("category", category);
    }
}
