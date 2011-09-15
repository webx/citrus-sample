package com.alibaba.sample.petstore.web.store.module.screen;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.sample.petstore.biz.StoreManager;
import com.alibaba.sample.petstore.dal.dataobject.Category;

public class CategoryList {
    @Autowired
    private StoreManager storeManager;

    public void execute(Context context) throws Exception {
        List<Category> cats = storeManager.getAllCategories();

        context.put("cats", cats);
    }
}
