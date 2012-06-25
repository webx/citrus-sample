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

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import com.alibaba.sample.petstore.dal.dataobject.Order;
import com.alibaba.sample.petstore.dal.dataobject.OrderItem;
import com.alibaba.sample.petstore.dal.dataobject.ProductItem;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ProductItemDaoTests extends AbstractDataAccessTests {
    @Autowired
    private ProductItemDao productItemDao;

    @Test
    public void getItemListByProductId() {
        assertProductItemList(productItemDao.getItemListByProductId("FI-SW-01"), "EST-1", "EST-2");
    }

    @Test
    public void getItemById() {
        ProductItem item = productItemDao.getItemById("EST-1");

        assertEquals("EST-1", item.getProductItemId());
        assertEquals("FI-SW-01", item.getProductId());
        assertEquals(new BigDecimal("16.50"), item.getListPrice());
        assertEquals(new BigDecimal("10.00"), item.getUnitCost());
        assertEquals(1, item.getSupplierId());
        assertEquals("P", item.getStatus());
        assertEquals("Large", item.getAttribute1());
    }

    @Test
    public void isItemInStock() {
        assertEquals(false, productItemDao.isItemInStock("EST-0"));
        assertEquals(true, productItemDao.isItemInStock("EST-1"));
        assertEquals(true, productItemDao.isItemInStock("EST-2"));
    }

    @Test
    public void updateQuantity() {
        Order order = new Order();

        OrderItem orderItem = new OrderItem();
        orderItem.setProductItemId("EST-1");
        orderItem.setQuantity(1);

        order.addOrderItem(orderItem);

        orderItem = new OrderItem();
        orderItem.setProductItemId("EST-3");
        orderItem.setQuantity(2);

        order.addOrderItem(orderItem);

        productItemDao.updateQuantity(order);

        assertEquals(false, productItemDao.isItemInStock("EST-1"));
        assertEquals(true, productItemDao.isItemInStock("EST-3"));
    }

    private void assertProductItemList(List<ProductItem> items, String... ids) {
        String[] result = new String[items.size()];

        int i = 0;
        for (ProductItem item : items) {
            result[i++] = item.getProductItemId();
        }

        Arrays.sort(result);
        Arrays.sort(ids);

        assertArrayEquals(ids, result);
    }
}
