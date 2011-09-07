package com.alibaba.sample.petstore.biz;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.sample.petstore.dal.dataobject.Cart;
import com.alibaba.sample.petstore.dal.dataobject.CartItem;
import com.alibaba.sample.petstore.dal.dataobject.Category;
import com.alibaba.sample.petstore.dal.dataobject.Product;
import com.alibaba.sample.petstore.dal.dataobject.ProductItem;

public class StoreManagerTests extends AbstractBizManagerTests {
    @Autowired
    private StoreManager storeManager;

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
}
