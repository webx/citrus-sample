package com.alibaba.sample.petstore.dal.dao;

import java.util.List;

import com.alibaba.sample.petstore.dal.dataobject.Category;

public interface CategoryDao {
    List<Category> getCategoryList();

    Category getCategoryById(String categoryId);
}
