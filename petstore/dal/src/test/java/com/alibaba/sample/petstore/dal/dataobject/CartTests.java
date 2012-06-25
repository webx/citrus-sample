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

package com.alibaba.sample.petstore.dal.dataobject;

import static org.junit.Assert.*;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.alibaba.citrus.util.io.ByteArrayOutputStream;
import org.junit.Before;
import org.junit.Test;

public class CartTests {
    private Cart cart;

    @Before
    public void init() {
        cart = new Cart();
    }

    @Test
    public void getTotal() {
        assertEquals(0, cart.getTotal().intValue());

        cart.addCartItem("item1");
        assertEquals(0, cart.getTotal().intValue());
    }

    @Test
    public void addCartItem() {
        assertEquals(0, cart.getCartItemList().size());

        cart.addCartItem("item1");
        assertEquals(1, cart.getCartItemList().size());

        cart.addCartItem("item2");
        assertEquals(2, cart.getCartItemList().size());

        cart.addCartItem("item2");
        assertEquals(2, cart.getCartItemList().size());

        assertProductItem(cart, 0, "item1", 1);
        assertProductItem(cart, 1, "item2", 2);
    }

    private void assertProductItem(Cart cart, int index, String itemId, int quantity) {
        CartItem item = cart.getCartItemList().get(index);

        assertSame(item, cart.getCartItem(itemId));
        assertEquals(itemId, item.getProductItemId());
        assertEquals(null, item.getProductItem());
        assertEquals(quantity, item.getQuantity());
        assertEquals(0, item.getTotal().intValue());
    }

    @Test
    public void setQuantity() {
        assertEquals(0, cart.getCartItemList().size());

        cart.setQuantity("item1", 10);
        assertEquals(1, cart.getCartItemList().size());
        assertProductItem(cart, 0, "item1", 10);

        cart.setQuantity("item1", 100);
        assertEquals(1, cart.getCartItemList().size());
        assertProductItem(cart, 0, "item1", 100);
    }

    @Test
    public void removeProductItem() {
        cart.removeCartItem("item1");
        assertEquals(0, cart.getCartItemList().size());

        cart.addCartItem("item1");
        assertEquals(1, cart.getCartItemList().size());

        cart.removeCartItem("item1");
        assertEquals(0, cart.getCartItemList().size());
    }

    @Test
    public void serialize() throws Exception {
        cart.setQuantity("item1", 10);
        cart.setQuantity("item2", 100);

        Cart newcart = deepClone();

        assertProductItem(newcart, 0, "item1", 10);
        assertProductItem(newcart, 1, "item2", 100);
    }

    private Cart deepClone() throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);

        oos.writeObject(cart);
        oos.close();

        ObjectInputStream ois = new ObjectInputStream(baos.toInputStream());

        try {
            return (Cart) ois.readObject();
        } finally {
            ois.close();
        }
    }
}
