package com.alibaba.sample.petstore.dal.dao;

import java.util.List;

import com.alibaba.sample.petstore.dal.dataobject.Order;

public interface OrderDao {
    List<Order> getOrdersByUserId(String username);

    Order getOrderById(int orderId);

    void insertOrder(Order order);
}
