package com.alibaba.sample.petstore.dal.dao;

import java.util.List;

import com.alibaba.sample.petstore.dal.dataobject.Order;
import com.alibaba.sample.petstore.dal.dataobject.ProductItem;

public interface ProductItemDao {
    List<ProductItem> getItemListByProductId(String productId);

    ProductItem getItemById(String itemId);

    boolean isItemInStock(String itemId);

    void updateQuantity(Order order);
}
