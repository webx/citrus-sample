package com.alibaba.sample.petstore.dal.dataobject;

import static org.junit.Assert.*;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.junit.Before;
import org.junit.Test;

import com.alibaba.citrus.util.io.ByteArrayOutputStream;

public class CartTests {
    private Cart cart;

    @Before
    public void init() {
        cart = new Cart();
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

        assertProductItem(0, "item1", 1);
        assertProductItem(1, "item2", 2);
    }

    private void assertProductItem(int index, String itemId, int quantity) {
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
        assertProductItem(0, "item1", 10);

        cart.setQuantity("item1", 100);
        assertEquals(1, cart.getCartItemList().size());
        assertProductItem(0, "item1", 100);
    }

    @Test
    public void removeProductItem() {
        cart.removeProductItem("item1");
        assertEquals(0, cart.getCartItemList().size());

        cart.addCartItem("item1");
        assertEquals(1, cart.getCartItemList().size());

        cart.removeProductItem("item1");
        assertEquals(0, cart.getCartItemList().size());
    }

    @Test
    public void serialize() throws Exception {
        cart.setQuantity("item1", 10);
        cart.setQuantity("item2", 100);

        cart = deepClone();

        assertProductItem(0, "item1", 10);
        assertProductItem(1, "item2", 100);
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
