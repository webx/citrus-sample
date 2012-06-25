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

package com.alibaba.sample.petstore.dal.dao.ibatis;

import java.util.List;

import com.alibaba.sample.petstore.dal.dao.OrderDao;
import com.alibaba.sample.petstore.dal.dataobject.Order;
import com.alibaba.sample.petstore.dal.dataobject.OrderItem;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class IbatisOrderDao extends SqlMapClientDaoSupport implements OrderDao {
    @SuppressWarnings("unchecked")
    public List<Order> getOrdersByUserId(String username) {
        return getSqlMapClientTemplate().queryForList("getOrdersByUserId", username);
    }

    @SuppressWarnings("unchecked")
    public Order getOrderById(int orderId) {
        Order order = null;
        Object parameterObject = new Integer(orderId);

        order = (Order) getSqlMapClientTemplate().queryForObject("getOrder", parameterObject);
        order.setOrderItems(getSqlMapClientTemplate().queryForList("getOrderItemsByOrderId",
                                                                   new Integer(order.getOrderId())));

        return order;
    }

    public void insertOrder(Order order) {
        getSqlMapClientTemplate().update("insertOrder", order);
        getSqlMapClientTemplate().update("insertOrderStatus", order);

        for (OrderItem orderItem : order.getOrderItems()) {
            orderItem.setOrderId(order.getOrderId());
            getSqlMapClientTemplate().update("insertOrderItem", orderItem);
        }
    }
}
