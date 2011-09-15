package com.alibaba.sample.petstore.web.admin.module.action;

import org.apache.commons.fileupload.FileItem;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.service.form.Group;
import com.alibaba.citrus.turbine.Navigator;
import com.alibaba.citrus.turbine.dataresolver.FormGroup;
import com.alibaba.citrus.turbine.dataresolver.Param;
import com.alibaba.sample.petstore.biz.AdminManager;
import com.alibaba.sample.petstore.biz.DuplicatedProductException;
import com.alibaba.sample.petstore.dal.dataobject.Product;

public class AddProductAction {
    @Autowired
    private AdminManager adminManager;

    public void doAdd(@FormGroup("addProduct") Group group, @Param("categoryId") String catId, Navigator nav)
            throws Exception {
        Product product = new Product();
        FileItem picture = group.getField("picture").getFileItem();

        group.setProperties(product);

        try {
            adminManager.addProduct(product, catId, picture);
            nav.redirectTo("adminModule").withTarget("categoryList.vm");
        } catch (DuplicatedProductException e) {
            group.getField("productId").setMessage("duplicatedProductId");
        }
    }
}
