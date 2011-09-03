package com.alibaba.sample.petstore.dal.dataobject;

public class Product {
    private String productId;
    private String categoryId;
    private String name;
    private String logo;
    private String description;
    private Category category;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId.trim();
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    @Override
    public String toString() {
        return getName();
    }
}
