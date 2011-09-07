package com.alibaba.sample.petstore.biz;

import java.util.List;

import com.alibaba.sample.petstore.dal.dataobject.Cart;
import com.alibaba.sample.petstore.dal.dataobject.Category;
import com.alibaba.sample.petstore.dal.dataobject.ProductItem;

public interface StoreManager {
    List<Category> getAllCategories();

    List<ProductItem> getAllProductItems(String productId);

    ProductItem getProductItem(String itemId);

    Cart getCartItems(Cart cart);
}
