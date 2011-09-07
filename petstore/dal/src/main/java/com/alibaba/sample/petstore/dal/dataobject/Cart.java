package com.alibaba.sample.petstore.dal.dataobject;

import static com.alibaba.citrus.util.CollectionUtil.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
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
        return cartItems.get(itemId);
    }

    public List<CartItem> getCartItemList() {
        return createArrayList(cartItems.values());
    }

    public void addCartItem(String itemId) {
        CartItem cartItem = cartItems.get(itemId);

        if (cartItem == null) {
            cartItem = new CartItem(itemId);

            cartItems.put(itemId, cartItem);
        }

        cartItem.incrementQuantity();
    }

    public void removeProductItem(String itemId) {
        cartItems.remove(itemId);
    }

    public void setQuantity(String itemId, int quantity) {
        CartItem cartItem = cartItems.get(itemId);

        if (cartItem == null) {
            addCartItem(itemId);
            cartItem = cartItems.get(itemId);
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
