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

package com.alibaba.sample.petstore.web.store.module.action;

import com.alibaba.citrus.service.form.Group;
import com.alibaba.citrus.turbine.Navigator;
import com.alibaba.citrus.turbine.dataresolver.FormGroup;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alibaba.sample.petstore.biz.DuplicatedProductException;
import com.alibaba.sample.petstore.biz.StoreManager;
import com.alibaba.sample.petstore.dal.dataobject.Product;
import org.apache.commons.fileupload.FileItem;
import org.springframework.beans.factory.annotation.Autowired;

public class AddProductAction {
    @Autowired
    private StoreManager storeManager;

    public void doAdd(@FormGroup("addProduct") Group group, @Param("categoryId") String catId, Navigator nav)
            throws Exception {
        Product product = new Product();
        FileItem picture = group.getField("picture").getFileItem();

        group.setProperties(product);

        try {
            storeManager.addProduct(product, catId, picture);
            nav.redirectTo("storeModule").withTarget("edit/categoryList");
        } catch (DuplicatedProductException e) {
            group.getField("productId").setMessage("duplicatedProductId");
        }
    }
}
