package com.alibaba.sample.petstore.biz.impl;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.sample.petstore.biz.StoreManager;
import com.alibaba.sample.petstore.dal.dao.CategoryDao;
import com.alibaba.sample.petstore.dal.dao.ProductDao;
import com.alibaba.sample.petstore.dal.dao.ProductItemDao;
import com.alibaba.sample.petstore.dal.dataobject.Cart;
import com.alibaba.sample.petstore.dal.dataobject.CartItem;
import com.alibaba.sample.petstore.dal.dataobject.Category;
import com.alibaba.sample.petstore.dal.dataobject.Product;
import com.alibaba.sample.petstore.dal.dataobject.ProductItem;

public class StoreManagerImpl implements StoreManager {
    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private ProductItemDao productItemDao;

    public List<Category> getAllCategories() {
        List<Category> catList = categoryDao.getCategoryList();

        for (Category cat : catList) {
            List<Product> prodList = productDao.getProductListByCategoryId(cat.getCategoryId());

            cat.setProductList(prodList);
        }

        return catList;
    }

    public List<ProductItem> getAllProductItems(String productId) {
        return productItemDao.getItemListByProductId(productId);
    }

    public ProductItem getProductItem(String itemId) {
        ProductItem item = productItemDao.getItemById(itemId);

        if (item == null) {
            return null;
        }

        Product product = productDao.getProductById(item.getProductId());
        Category category = categoryDao.getCategoryById(product.getCategoryId());

        product.setCategory(category);
        item.setProduct(product);

        return item;
    }

    public Cart getCartItems(Cart cart) {
        List<CartItem> cartItems = cart.getCartItemList();

        for (CartItem cartItem : cartItems) {
            cartItem.setProductItem(getProductItem(cartItem.getProductItemId()));
        }

        // “¿¥Œ∞¥catId, productId, productItemId≈≈–Ú
        Collections.sort(cartItems, new Comparator<CartItem>() {
            public int compare(CartItem cartItem1, CartItem cartItem2) {
                ProductItem item1 = cartItem1.getProductItem();
                ProductItem item2 = cartItem2.getProductItem();

                if (item1 == null || item2 == null) {
                    return 0;
                }

                int compare = item1.getProduct().getCategory().getCategoryId()
                        .compareTo(item2.getProduct().getCategory().getCategoryId());

                if (compare != 0) {
                    return compare;
                }

                compare = item1.getProduct().getProductId().compareTo(item2.getProduct().getProductId());

                if (compare != 0) {
                    return compare;
                }

                return item1.getProductItemId().compareTo(item2.getProductItemId());
            }
        });

        return cart;
    }
}
