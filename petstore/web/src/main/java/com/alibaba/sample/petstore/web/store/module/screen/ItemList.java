package com.alibaba.sample.petstore.web.store.module.screen;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alibaba.sample.petstore.biz.StoreManager;
import com.alibaba.sample.petstore.dal.dao.CategoryDao;
import com.alibaba.sample.petstore.dal.dao.ProductDao;
import com.alibaba.sample.petstore.dal.dataobject.Category;
import com.alibaba.sample.petstore.dal.dataobject.Product;
import com.alibaba.sample.petstore.dal.dataobject.ProductItem;

public class ItemList {
    @Autowired
    private StoreManager storeManager;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private CategoryDao categoryDao;

    public void execute(@Param("productId") String productId, Context context) throws Exception {
        List<ProductItem> items = storeManager.getAllProductItems(productId);
        Product product = productDao.getProductById(productId);
        Category category = categoryDao.getCategoryById(product.getCategoryId());

        context.put("category", category);
        context.put("product", product);
        context.put("items", items);
    }
}
