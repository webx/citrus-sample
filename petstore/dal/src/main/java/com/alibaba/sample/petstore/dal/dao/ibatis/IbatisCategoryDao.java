package com.alibaba.sample.petstore.dal.dao.ibatis;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.alibaba.sample.petstore.dal.dao.CategoryDao;
import com.alibaba.sample.petstore.dal.dataobject.Category;

public class IbatisCategoryDao extends SqlMapClientDaoSupport implements CategoryDao {
    @SuppressWarnings("unchecked")
    public List<Category> getCategoryList() {
        return getSqlMapClientTemplate().queryForList("getCategoryList", null);
    }

    public Category getCategoryById(String categoryId) {
        return (Category) getSqlMapClientTemplate().queryForObject("getCategory", categoryId);
    }
}
