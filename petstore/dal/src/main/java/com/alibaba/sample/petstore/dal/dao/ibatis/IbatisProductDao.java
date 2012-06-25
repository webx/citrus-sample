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

package com.alibaba.sample.petstore.dal.dao.ibatis;

import static com.alibaba.citrus.util.CollectionUtil.*;
import static com.alibaba.citrus.util.StringUtil.*;

import java.util.List;

import com.alibaba.sample.petstore.dal.dao.ProductDao;
import com.alibaba.sample.petstore.dal.dataobject.Product;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

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
