package com.alibaba.sample.petstore.biz;

import java.util.List;

import org.apache.commons.fileupload.FileItem;

import com.alibaba.sample.petstore.dal.dataobject.Cart;
import com.alibaba.sample.petstore.dal.dataobject.Category;
import com.alibaba.sample.petstore.dal.dataobject.Product;
import com.alibaba.sample.petstore.dal.dataobject.ProductItem;

public interface StoreManager {
    Category getCategory(String categoryId);

    void addProduct(Product product, String categoryId, FileItem picture) throws StoreManagerException;

    List<Category> getAllCategories();

    List<ProductItem> getAllProductItems(String productId);

    ProductItem getProductItem(String itemId);

    Cart getCartItems(Cart cart);
}
