package com.alibaba.sample.petstore.biz;

import org.apache.commons.fileupload.FileItem;

import com.alibaba.sample.petstore.dal.dataobject.Category;
import com.alibaba.sample.petstore.dal.dataobject.Product;

public interface AdminManager {
    Category getCategory(String categoryId);

    void addProduct(Product product, String categoryId, FileItem picture) throws AdminManagerException;
}
