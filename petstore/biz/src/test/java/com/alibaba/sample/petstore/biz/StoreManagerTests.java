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

package com.alibaba.sample.petstore.biz;

import static com.alibaba.citrus.test.TestEnvStatic.*;
import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import com.alibaba.citrus.util.io.StreamUtil;
import com.alibaba.sample.petstore.dal.dao.ProductDao;
import com.alibaba.sample.petstore.dal.dataobject.Cart;
import com.alibaba.sample.petstore.dal.dataobject.CartItem;
import com.alibaba.sample.petstore.dal.dataobject.Category;
import com.alibaba.sample.petstore.dal.dataobject.Product;
import com.alibaba.sample.petstore.dal.dataobject.ProductItem;
import org.apache.commons.fileupload.FileItem;
import org.easymock.IAnswer;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class StoreManagerTests extends AbstractBizManagerTests {
    @Autowired
    private StoreManager storeManager;

    @Autowired
    private ProductDao productDao;

    @Test
    public void getAllCategories() {
        List<Category> cats = storeManager.getAllCategories();

        assertEquals(5, cats.size());

        List<Product> prods = null;

        for (Category cat : cats) {
            if ("CATS".equals(cat.getCategoryId())) {
                assertEquals("Cats", cat.getName());
                prods = cat.getProductList();
                assertNotNull(prods);
                break;
            }
        }

        assertEquals(2, prods.size());
    }

    @Test
    public void getAllProductItems() {
        List<ProductItem> items = storeManager.getAllProductItems("FL-DSH-01");

        assertEquals(2, items.size());
    }

    @Test
    public void getProductItem() {
        assertNull(storeManager.getProductItem("nonexist"));

        ProductItem item = storeManager.getProductItem("EST-1");
        assertEquals("EST-1", item.getProductItemId());

        Product prod = item.getProduct();
        assertEquals("FI-SW-01", prod.getProductId());
        assertEquals("Angelfish", prod.getName());

        Category cat = prod.getCategory();
        assertEquals("FISH", cat.getCategoryId());
    }

    @Test
    public void getCartItems() {
        Cart cart = new Cart();

        cart.setQuantity("EST-1", 10);
        cart.setQuantity("EST-2", 100);

        storeManager.getCartItems(cart);

        assertProductItem(cart, 0, "EST-1", 10);
        assertProductItem(cart, 1, "EST-2", 100);

        assertEquals(165 + 1650, cart.getTotal().intValue());
    }

    private void assertProductItem(Cart cart, int index, String itemId, int quantity) {
        CartItem item = cart.getCartItemList().get(index);

        assertSame(item, cart.getCartItem(itemId));
        assertEquals(itemId, item.getProductItemId());
        assertEquals(itemId, item.getProductItem().getProductItemId());
        assertEquals(quantity, item.getQuantity());
        assertEquals(item.getProductItem().getListPrice().doubleValue() * quantity, item.getTotal().doubleValue(),
                     0.01d);
    }

    @Test
    public void getCategory() {
        Category cat = storeManager.getCategory("BIRDS");

        assertEquals("BIRDS", cat.getCategoryId());
        assertEquals("Birds", cat.getName());
        assertEquals("Birds", cat.getDescription());
        assertEquals("cat_bird.jpg", cat.getLogo());
    }

    @Test(expected = DuplicatedProductException.class)
    public void addProduct_duplicated() {
        Product prod = new Product();

        prod.setProductId("FI-SW-01");

        storeManager.addProduct(prod, "FISH", null);
    }

    @Test
    public void addProduct() throws Exception {
        Product prod = new Product();
        final File srcfile = new File(srcdir, "resources.xml");

        prod.setProductId("myfish");
        prod.setDescription("My fish");
        prod.setName("my fish");

        FileItem fi = createMock(FileItem.class);
        expect(fi.getName()).andReturn("c:\\test\\pic.gif");
        expect(fi.getInputStream()).andAnswer(new IAnswer<InputStream>() {
            public InputStream answer() throws Throwable {
                return new FileInputStream(srcfile);
            }
        });
        replay(fi);

        storeManager.addProduct(prod, "FISH", fi);

        prod = productDao.getProductById("myfish");
        assertEquals("myfish", prod.getProductId());
        assertEquals("my fish", prod.getName());
        assertEquals("My fish", prod.getDescription());
        assertEquals("FISH", prod.getCategoryId());
        assertTrue(prod.getLogo().startsWith("image_"));
        assertTrue(prod.getLogo().endsWith(".gif"));

        File f = new File(destdir, "upload/" + prod.getLogo());
        assertTrue(f.exists());

        assertEquals(StreamUtil.readText(new FileInputStream(srcfile), "8859_1", true),
                     StreamUtil.readText(new FileInputStream(f), "8859_1", true));
    }
}
