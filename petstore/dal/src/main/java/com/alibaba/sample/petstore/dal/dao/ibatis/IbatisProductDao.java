package com.alibaba.sample.petstore.dal.dao.ibatis;

import static com.alibaba.citrus.util.CollectionUtil.*;
import static com.alibaba.citrus.util.StringUtil.*;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.alibaba.sample.petstore.dal.dao.ProductDao;
import com.alibaba.sample.petstore.dal.dataobject.Product;

public class IbatisProductDao extends SqlMapClientDaoSupport implements ProductDao {
    @SuppressWarnings("unchecked")
    public List<Product> getProductListByCategoryId(String categoryId) {
        return getSqlMapClientTemplate().queryForList("getProductListByCategory", categoryId);
    }

    public Product getProductById(String productId) {
        return (Product) getSqlMapClientTemplate().queryForObject("getProduct", productId);
    }

    @SuppressWarnings("unchecked")
    public List<Product> searchProductsByKeywords(String keywords) {
        return getSqlMapClientTemplate().queryForList("searchProductList", new ProductSearch(keywords));
    }

    public void insertProduct(Product product) {
        getSqlMapClientTemplate().update("insertProduct", product);
    }

    public static class ProductSearch {
        private List<String> keywordList = createLinkedList();

        public ProductSearch(String keywords) {
            if (keywords != null) {
                for (String keyword : split(keywords, ", ")) {
                    keywordList.add("%" + keyword + "%");
                }
            }
        }

        public List<String> getKeywordList() {
            return keywordList;
        }
    }
}
