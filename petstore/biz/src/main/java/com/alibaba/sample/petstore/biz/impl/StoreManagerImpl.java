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

package com.alibaba.sample.petstore.biz.impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.alibaba.citrus.util.io.StreamUtil;
import com.alibaba.sample.petstore.biz.DuplicatedProductException;
import com.alibaba.sample.petstore.biz.StoreManager;
import com.alibaba.sample.petstore.biz.StoreManagerException;
import com.alibaba.sample.petstore.dal.dao.CategoryDao;
import com.alibaba.sample.petstore.dal.dao.ProductDao;
import com.alibaba.sample.petstore.dal.dao.ProductItemDao;
import com.alibaba.sample.petstore.dal.dataobject.Cart;
import com.alibaba.sample.petstore.dal.dataobject.CartItem;
import com.alibaba.sample.petstore.dal.dataobject.Category;
import com.alibaba.sample.petstore.dal.dataobject.Product;
import com.alibaba.sample.petstore.dal.dataobject.ProductItem;
import org.apache.commons.fileupload.FileItem;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.dao.DataIntegrityViolationException;

public class StoreManagerImpl implements StoreManager, InitializingBean {
    private final static String UPLOAD_DIR = "/petstore/upload";

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private ProductItemDao productItemDao;

    @Autowired
    private ResourceLoader resourceLoader;

    private File uploadDir;

    public void afterPropertiesSet() {
        try {
            uploadDir = resourceLoader.getResource(UPLOAD_DIR).getFile();

            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            if (!uploadDir.isDirectory()) {
                throw new IOException("Could not create directory " + uploadDir.getAbsolutePath());
            }
        } catch (Exception e) {
            throw new StoreManagerException("Could not get upload directory from ResourceLoader: " + UPLOAD_DIR);
        }
    }

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

        // 依次按catId, productId, productItemId排序
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

    public Category getCategory(String categoryId) {
        return categoryDao.getCategoryById(categoryId);
    }

    public void addProduct(Product product, String categoryId, FileItem picture) throws StoreManagerException {
        String imageFileName;

        try {
            imageFileName = getPictureName(picture);
        } catch (IOException e) {
            throw new StoreManagerException(e);
        }

        product.setLogo(imageFileName);
        product.setCategoryId(categoryId);

        try {
            productDao.insertProduct(product);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicatedProductException(product.getProductId());
        }
    }

    private String getPictureName(FileItem picture) throws IOException {
        String imageFileName = null;

        if (picture != null) {
            String fileName = picture.getName().replace('\\', '/');

            fileName = fileName.substring(fileName.lastIndexOf("/") + 1);

            String ext = "";
            int index = fileName.lastIndexOf(".");

            if (index > 0) {
                ext = fileName.substring(index);
            }

            File imageFile = File.createTempFile("image_", ext, uploadDir);

            imageFileName = imageFile.getName();

            InputStream is = picture.getInputStream();
            OutputStream os = new BufferedOutputStream(new FileOutputStream(imageFile));

            StreamUtil.io(is, os, true, true);
        }

        return imageFileName;
    }
}
