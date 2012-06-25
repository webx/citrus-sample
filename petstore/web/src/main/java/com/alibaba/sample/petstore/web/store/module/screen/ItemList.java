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

package com.alibaba.sample.petstore.web.store.module.screen;

import java.util.List;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alibaba.sample.petstore.biz.StoreManager;
import com.alibaba.sample.petstore.dal.dao.CategoryDao;
import com.alibaba.sample.petstore.dal.dao.ProductDao;
import com.alibaba.sample.petstore.dal.dataobject.Category;
import com.alibaba.sample.petstore.dal.dataobject.Product;
import com.alibaba.sample.petstore.dal.dataobject.ProductItem;
import org.springframework.beans.factory.annotation.Autowired;

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
