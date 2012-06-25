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

package com.alibaba.sample.petstore.dal.dao;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import com.alibaba.sample.petstore.dal.dataobject.Product;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ProductDaoTests extends AbstractDataAccessTests {
    @Autowired
    private ProductDao productDao;

    @Test
    public void getProductListByCategoryId() {
        assertProductList(productDao.getProductListByCategoryId("FISH"), "FI-SW-01", "FI-SW-02", "FI-FW-01", "FI-FW-02");
        assertProductList(productDao.getProductListByCategoryId("DOGS"), "K9-BD-01", "K9-PO-02", "K9-DL-01",
                          "K9-RT-01", "K9-RT-02", "K9-CW-01");
    }

    @Test
    public void getProductById() {
        Product prod = productDao.getProductById("FI-SW-01");

        assertEquals("FI-SW-01", prod.getProductId());
        assertEquals("FISH", prod.getCategoryId());
        assertEquals("Angelfish", prod.getName());
        assertEquals("fish1.jpg", prod.getLogo());
        assertEquals("Salt Water fish from Australia", prod.getDescription());

        assertNull(prod.getCategory());
    }

    @Test
    public void searchProductsByKeywords() {
        assertProductList(productDao.searchProductsByKeywords("angel, bull, green"), "FI-SW-01", "K9-BD-01", "RP-LI-02");

        assertEquals("Angelfish", productDao.getProductById("FI-SW-01").getName());
        assertEquals("Bulldog", productDao.getProductById("K9-BD-01").getName());
        assertEquals("Friendly green friend", productDao.getProductById("RP-LI-02").getDescription());
    }

    @Test
    public void insertProduct() {
        Product prod = new Product();

        prod.setProductId("id");
        prod.setCategoryId("DOGS");
        prod.setName("name");
        prod.setDescription("description");
        prod.setLogo("logo.jpg");

        productDao.insertProduct(prod);

        prod = productDao.getProductById("id");

        assertEquals("id", prod.getProductId());
        assertEquals("DOGS", prod.getCategoryId());
        assertEquals("name", prod.getName());
        assertEquals("logo.jpg", prod.getLogo());
        assertEquals("description", prod.getDescription());

        assertNull(prod.getCategory());
    }

    private void assertProductList(List<Product> products, String... ids) {
        String[] result = new String[products.size()];

        int i = 0;
        for (Product prod : products) {
            result[i++] = prod.getProductId();
        }

        Arrays.sort(result);
        Arrays.sort(ids);

        assertArrayEquals(ids, result);
    }
}
