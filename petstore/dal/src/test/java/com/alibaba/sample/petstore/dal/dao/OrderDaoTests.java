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
import java.text.SimpleDateFormat;
import java.util.List;

import com.alibaba.sample.petstore.dal.dataobject.Order;
import com.alibaba.sample.petstore.dal.dataobject.OrderItem;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class OrderDaoTests extends AbstractDataAccessTests {
    @Autowired
    private OrderDao orderDao;

    @Autowired
    private SequenceDao sequenceDao;

    @Test
    public void order() throws Exception {
        insertOrder();
        getOrderById();
        getOrdersByUserId();
    }

    private void insertOrder() throws Exception {
        Order order = new Order();

        order.setOrderId(sequenceDao.getNextId("ordernum"));
        order.setUserId("userId");
        order.setOrderDate(new SimpleDateFormat("yyyy-MM-dd").parse("1975-12-15"));
        order.setShipAddress1("shipAddress1");
        order.setShipAddress2("shipAddress2");
        order.setShipCity("shipCity");
        order.setShipState("shipState");
        order.setShipZip("shipZip");
        order.setShipCountry("shipCountry");
        order.setBillAddress1("billAddress1");
        order.setBillAddress2("billAddress2");
        order.setBillCity("billCity");
        order.setBillState("billState");
        order.setBillZip("billZip");
        order.setBillCountry("billCountry");
        order.setCourier("courier");
        order.setBillToFirstName("billToFirstName");
        order.setBillToLastName("billToLastName");
        order.setShipToFirstName("shipToFirstName");
        order.setShipToLastName("shipToLastName");
        order.setCreditCard("creditCard");
        order.setExpiryDate("expiryDate");
        order.setCardType("cardType");
        order.setLocale("locale");
        order.setStatus("status");
        order.setTotalPrice(new BigDecimal("50.0"));

        OrderItem orderItem;

        orderItem = new OrderItem();
        orderItem.setOrderId(order.getOrderId());
        orderItem.setOrderItemId(sequenceDao.getNextId("orderitemnum"));
        orderItem.setProductItemId("EST-1");
        orderItem.setQuantity(1);
        orderItem.setUnitPrice(new BigDecimal("10.0"));

        order.addOrderItem(orderItem);

        orderItem = new OrderItem();
        orderItem.setOrderId(order.getOrderId());
        orderItem.setOrderItemId(sequenceDao.getNextId("orderitemnum"));
        orderItem.setProductItemId("EST-3");
        orderItem.setQuantity(2);
        orderItem.setUnitPrice(new BigDecimal("20.0"));

        order.addOrderItem(orderItem);

        orderDao.insertOrder(order);
    }

    private void getOrderById() {
        Order order = orderDao.getOrderById(1000);

        assertEquals("billAddress1", order.getBillAddress1());
        assertEquals(2, order.getOrderItems().size());
    }

    private void getOrdersByUserId() {
        List<Order> orders = orderDao.getOrdersByUserId("userId");

        assertEquals(1, orders.size());

        Order order = orders.get(0);

        assertEquals("billAddress1", order.getBillAddress1());
    }
}
