package com.alibaba.sample.petstore.dal;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.sample.petstore.dal.dao.CategoryDao;
import com.alibaba.sample.petstore.dal.dataobject.Category;

public class CategoryDaoTests extends AbstractDataAccessTests {
    @Autowired
    private CategoryDao categoryDao;

    @Test
    public void getCategoryList() {
        assertCategoryList(categoryDao.getCategoryList(), "BIRDS", "CATS", "DOGS", "FISH", "REPTILES");
    }

    @Test
    public void getCategoryById() {
        Category cat = categoryDao.getCategoryById("BIRDS");

        assertEquals("BIRDS", cat.getCategoryId());
        assertEquals("Birds", cat.getName());
        assertEquals("Birds", cat.getDescription());
        assertEquals("cat_bird.jpg", cat.getLogo());
    }

    private void assertCategoryList(List<Category> cats, String... ids) {
        String[] result = new String[cats.size()];

        int i = 0;
        for (Category cat : cats) {
            result[i++] = cat.getCategoryId();
        }

        Arrays.sort(result);
        Arrays.sort(ids);

        assertArrayEquals(ids, result);
    }
}
