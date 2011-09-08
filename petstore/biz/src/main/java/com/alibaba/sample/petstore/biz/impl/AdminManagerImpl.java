package com.alibaba.sample.petstore.biz.impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.fileupload.FileItem;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.dao.DataIntegrityViolationException;

import com.alibaba.citrus.util.io.StreamUtil;
import com.alibaba.sample.petstore.biz.AdminManager;
import com.alibaba.sample.petstore.biz.AdminManagerException;
import com.alibaba.sample.petstore.biz.DuplicatedProductException;
import com.alibaba.sample.petstore.dal.dao.CategoryDao;
import com.alibaba.sample.petstore.dal.dao.ProductDao;
import com.alibaba.sample.petstore.dal.dataobject.Category;
import com.alibaba.sample.petstore.dal.dataobject.Product;

public class AdminManagerImpl implements AdminManager, InitializingBean {
    private final static String UPLOAD_DIR = "/petstore/upload";

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private ProductDao productDao;

    private File uploadDir;

    public void afterPropertiesSet() {
        try {
            uploadDir = resourceLoader.getResource(UPLOAD_DIR).getFile();

            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            if (!uploadDir.isDirectory()) {
                throw new IOException("Could not create directory " + uploadDir.getAbsolutePath());
            }
        } catch (Exception e) {
            throw new AdminManagerException("Could not get upload directory from ResourceLoader: " + UPLOAD_DIR);
        }
    }

    public Category getCategory(String categoryId) {
        return categoryDao.getCategoryById(categoryId);
    }

    public void addProduct(Product product, String categoryId, FileItem picture) throws AdminManagerException {
        String imageFileName;

        try {
            imageFileName = getPictureName(picture);
        } catch (IOException e) {
            throw new AdminManagerException(e);
        }

        product.setLogo(imageFileName);
        product.setCategoryId(categoryId);

        try {
            productDao.insertProduct(product);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicatedProductException(product.getProductId());
        }
    }

    private String getPictureName(FileItem picture) throws IOException {
        String imageFileName = null;

        if (picture != null) {
            String fileName = picture.getName().replace('\\', '/');

            fileName = fileName.substring(fileName.lastIndexOf("/") + 1);

            String ext = "";
            int index = fileName.lastIndexOf(".");

            if (index > 0) {
                ext = fileName.substring(index);
            }

            File imageFile = File.createTempFile("image_", ext, uploadDir);

            imageFileName = imageFile.getName();

            InputStream is = picture.getInputStream();
            OutputStream os = new BufferedOutputStream(new FileOutputStream(imageFile));

            StreamUtil.io(is, os, true, true);
        }

        return imageFileName;
    }
}
