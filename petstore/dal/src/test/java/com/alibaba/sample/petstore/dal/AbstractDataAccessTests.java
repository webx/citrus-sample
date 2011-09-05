package com.alibaba.sample.petstore.dal;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.citrus.test.context.SpringextContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/resources.xml", "/classpath/petstore/dal/data-source.xml",
        "/classpath/petstore/dal/dao.xml" }, loader = SpringextContextLoader.class)
public abstract class AbstractDataAccessTests extends AbstractTransactionalJUnit4SpringContextTests {
}
