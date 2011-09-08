package com.alibaba.sample.petstore.biz;

import static com.alibaba.citrus.test.TestEnvStatic.*;
import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.commons.fileupload.FileItem;
import org.easymock.IAnswer;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.citrus.util.io.StreamUtil;
import com.alibaba.sample.petstore.dal.dao.ProductDao;
import com.alibaba.sample.petstore.dal.dataobject.Category;
import com.alibaba.sample.petstore.dal.dataobject.Product;

public class AdminManagerTests extends AbstractBizManagerTests {
    @Autowired
    private AdminManager adminManager;

    @Autowired
    private ProductDao productDao;

    @Test
    public void getCategory() {
        Category cat = adminManager.getCategory("BIRDS");

        assertEquals("BIRDS", cat.getCategoryId());
        assertEquals("Birds", cat.getName());
        assertEquals("Birds", cat.getDescription());
        assertEquals("cat_bird.jpg", cat.getLogo());
    }

    @Test(expected = DuplicatedProductException.class)
    public void addProduct_duplicated() {
        Product prod = new Product();

        prod.setProductId("FI-SW-01");

        adminManager.addProduct(prod, "FISH", null);
    }

    @Test
    public void addProduct() throws Exception {
        Product prod = new Product();
        final File srcfile = new File(srcdir, "resources.xml");

        prod.setProductId("myfish");
        prod.setDescription("My fish");
        prod.setName("my fish");

        FileItem fi = createMock(FileItem.class);
        expect(fi.getName()).andReturn("c:\\test\\pic.gif");
        expect(fi.getInputStream()).andAnswer(new IAnswer<InputStream>() {
            public InputStream answer() throws Throwable {
                return new FileInputStream(srcfile);
            }
        });
        replay(fi);

        adminManager.addProduct(prod, "FISH", fi);

        prod = productDao.getProductById("myfish");
        assertEquals("myfish", prod.getProductId());
        assertEquals("my fish", prod.getName());
        assertEquals("My fish", prod.getDescription());
        assertEquals("FISH", prod.getCategoryId());
        assertTrue(prod.getLogo().startsWith("image_"));
        assertTrue(prod.getLogo().endsWith(".gif"));

        File f = new File(destdir, "upload/" + prod.getLogo());
        assertTrue(f.exists());

        assertEquals(StreamUtil.readText(new FileInputStream(srcfile), "8859_1", true),
                StreamUtil.readText(new FileInputStream(f), "8859_1", true));
    }
}
