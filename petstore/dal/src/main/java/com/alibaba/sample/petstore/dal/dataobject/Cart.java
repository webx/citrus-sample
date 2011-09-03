package com.alibaba.sample.petstore.dal.dataobject;

import static com.alibaba.citrus.util.CollectionUtil.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Map;

/**
 * 存放在session中的shopping cart对象。
 * 
 * @author Michael Zhou
 */
public class Cart implements Serializable {
    private static final long serialVersionUID = 43557847549256390L;
    private Map<String, CartItem> cartItems = createHashMap();

    public CartItem getCartItem(String itemId) {
        return (CartItem) cartItems.get(itemId);
    }

    public Collection<CartItem> getCartItemList() {
        return cartItems.values();
    }

    public void addItem(String itemId) {
        CartItem cartItem = (CartItem) cartItems.get(itemId);

        if (cartItem == null) {
            cartItem = new CartItem(itemId);

            cartItems.put(itemId, cartItem);
        }

        cartItem.incrementQuantity();
    }

    public void removeItem(String itemId) {
        cartItems.remove(itemId);
    }

    public void setQuantity(String itemId, int quantity) {
        CartItem cartItem = (CartItem) cartItems.get(itemId);

        if (cartItem == null) {
            addItem(itemId);
            cartItem = (CartItem) cartItems.get(itemId);
        }

        cartItem.setQuantity(quantity);
    }

    public BigDecimal getTotal() {
        BigDecimal total = new BigDecimal("0");

        for (CartItem cartItem : getCartItemList()) {
            total = total.add(cartItem.getTotal());
        }

        return total;
    }
}
