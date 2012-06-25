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

import java.util.List;
import java.util.Map;

import com.alibaba.sample.petstore.dal.dao.ProductItemDao;
import com.alibaba.sample.petstore.dal.dataobject.Order;
import com.alibaba.sample.petstore.dal.dataobject.OrderItem;
import com.alibaba.sample.petstore.dal.dataobject.ProductItem;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class IbatisProductItemDao extends SqlMapClientDaoSupport implements ProductItemDao {
    @SuppressWarnings("unchecked")
    public List<ProductItem> getItemListByProductId(String productId) {
        return getSqlMapClientTemplate().queryForList("getItemListByProduct", productId);
    }

    public ProductItem getItemById(String itemId) {
        Integer i = (Integer) getSqlMapClientTemplate().queryForObject("getInventoryQuantity", itemId);
        ProductItem item = (ProductItem) getSqlMapClientTemplate().queryForObject("getItem", itemId);

        if (item != null && i != null) {
            item.setQuantity(i.intValue());
        }

        return item;
    }

    public boolean isItemInStock(String itemId) {
        Integer i = (Integer) getSqlMapClientTemplate().queryForObject("getInventoryQuantity", itemId);

        return i != null && i.intValue() > 0;
    }

    public void updateQuantity(Order order) {
        for (OrderItem orderItem : order.getOrderItems()) {
            String itemId = orderItem.getProductItemId();
            int quantity = orderItem.getQuantity();

            Map<String, Object> params = createHashMap();

            params.put("itemId", itemId);
            params.put("quantity", quantity);

            getSqlMapClientTemplate().update("updateInventoryQuantity", params);
        }
    }
}
