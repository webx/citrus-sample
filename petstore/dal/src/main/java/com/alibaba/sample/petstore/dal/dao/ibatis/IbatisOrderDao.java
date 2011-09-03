package com.alibaba.sample.petstore.dal.dao.ibatis;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.alibaba.sample.petstore.dal.dao.OrderDao;
import com.alibaba.sample.petstore.dal.dataobject.Order;
import com.alibaba.sample.petstore.dal.dataobject.OrderItem;

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
