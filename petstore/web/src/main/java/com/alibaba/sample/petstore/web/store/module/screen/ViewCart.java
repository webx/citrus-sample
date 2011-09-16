package com.alibaba.sample.petstore.web.store.module.screen;

import static com.alibaba.sample.petstore.web.common.PetstoreConstant.*;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.turbine.Context;
import com.alibaba.sample.petstore.biz.StoreManager;
import com.alibaba.sample.petstore.dal.dataobject.Cart;

public class ViewCart {
    @Autowired
    private StoreManager storeManager;

    public void execute(HttpSession session, Context context) throws Exception {
        Cart cart = (Cart) session.getAttribute(PETSTORE_CART_KEY);

        if (cart == null) {
            cart = new Cart();
        }

        cart = storeManager.getCartItems(cart);

        context.put("cart", cart);
    }
}
