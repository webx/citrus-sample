package com.alibaba.sample.petstore.dal.dao;

import java.util.List;

import com.alibaba.sample.petstore.dal.dataobject.Product;

public interface ProductDao {
    List<Product> getProductListByCategoryId(String categoryId);

    Product getProductById(String productId);

    List<Product> searchProductsByKeywords(String keywords);

    void insertProduct(Product product);
}
