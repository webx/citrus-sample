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

package com.alibaba.sample.petstore.web.store.module.action;

import static com.alibaba.citrus.util.CollectionUtil.*;
import static com.alibaba.sample.petstore.web.common.PetstoreConstant.*;

import java.util.Map;
import javax.servlet.http.HttpSession;

import com.alibaba.citrus.service.form.Group;
import com.alibaba.citrus.turbine.Context;
import com.alibaba.citrus.turbine.dataresolver.FormGroups;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alibaba.citrus.webx.WebxException;
import com.alibaba.sample.petstore.biz.StoreManager;
import com.alibaba.sample.petstore.dal.dataobject.Cart;
import org.springframework.beans.factory.annotation.Autowired;

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

    public void doRemoveItem(HttpSession session, @Param("itemId") String itemId, Context context)
            throws WebxException {
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
