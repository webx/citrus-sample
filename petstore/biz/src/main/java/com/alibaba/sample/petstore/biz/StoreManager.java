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

package com.alibaba.sample.petstore.biz;

import java.util.List;

import com.alibaba.sample.petstore.dal.dataobject.Cart;
import com.alibaba.sample.petstore.dal.dataobject.Category;
import com.alibaba.sample.petstore.dal.dataobject.Product;
import com.alibaba.sample.petstore.dal.dataobject.ProductItem;
import org.apache.commons.fileupload.FileItem;

public interface StoreManager {
    Category getCategory(String categoryId);

    void addProduct(Product product, String categoryId, FileItem picture) throws StoreManagerException;

    List<Category> getAllCategories();

    List<ProductItem> getAllProductItems(String productId);

    ProductItem getProductItem(String itemId);

    Cart getCartItems(Cart cart);
}
