package com.alibaba.sample.petstore.web.store.module.action;

import static com.alibaba.citrus.util.CollectionUtil.*;
import static com.alibaba.sample.petstore.web.common.PetstoreConstant.*;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.form.Group;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.FormGroups;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alibaba.citrus.webx.WebxException;
import com.alibaba.sample.petstore.biz.StoreManager;
import com.alibaba.sample.petstore.dal.dataobject.Cart;

public class CartAction {
    @Autowired
    private StoreManager storeManager;

    public void doAddItem(HttpSession session, @Param("itemId") String itemId, Context context) throws Exception {
        Cart cart = (Cart) session.getAttribute(PETSTORE_CART_KEY);

        if (cart == null) {
            cart = new Cart();
        }

        cart.addCartItem(itemId);

        session.setAttribute(PETSTORE_CART_KEY, cart);

        context.put("itemAdded", Boolean.TRUE);
    }

    public void doRemoveItem(HttpSession session, @Param("itemId") String itemId, Context context) throws WebxException {
        Cart cart = (Cart) session.getAttribute(PETSTORE_CART_KEY);

        if (cart == null) {
            cart = new Cart();
        }

        cart.removeCartItem(itemId);

        session.setAttribute(PETSTORE_CART_KEY, cart);
    }

    public void doUpdate(HttpSession session, @FormGroups("cartItem") Group[] groups, Context context)
            throws WebxException {
        Cart cart = (Cart) session.getAttribute(PETSTORE_CART_KEY);

        if (cart == null) {
            return;
        }

        cart = storeManager.getCartItems(cart);

        for (Group group : groups) {
            String itemId = group.getInstanceKey();
            int quantity = group.getField("quantity").getIntegerValue();
            int stockQuantity = cart.getCartItem(itemId).getProductItem().getQuantity();

            if (quantity > stockQuantity) {
                Map<String, Object> params = createHashMap();
                params.put("stockQuantity", new Integer(stockQuantity));

                group.getField("quantity").setMessage("outOfStock", params);
            } else {
                cart.setQuantity(itemId, quantity);
            }
        }

        session.setAttribute(PETSTORE_CART_KEY, cart);
    }
}
