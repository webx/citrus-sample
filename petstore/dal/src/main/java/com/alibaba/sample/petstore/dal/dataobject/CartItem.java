package com.alibaba.sample.petstore.dal.dataobject;

import java.io.Serializable;
import java.math.BigDecimal;

public class CartItem implements Serializable {
    private static final long serialVersionUID = -8518016753555699796L;
    private String productItemId;
    private int quantity;
    private transient ProductItem item;

    public CartItem(String productItemId) {
        this.productItemId = productItemId;
    }

    public String getProductItemId() {
        return productItemId;
    }

    public ProductItem getProductItem() {
        return item;
    }

    public void setProductItem(ProductItem item) {
        this.item = item;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void incrementQuantity() {
        quantity++;
    }

    public BigDecimal getTotal() {
        if (item == null) {
            return new BigDecimal(0);
        }

        return item.getListPrice().multiply(new BigDecimal(quantity));
    }
}
