package com.alibaba.sample.petstore.dal;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.citrus.test.context.SpringextContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/resources.xml", "/classpath/dal/dal-data-source.xml", "/classpath/dal/dal-dao.xml" }, loader = SpringextContextLoader.class)
public class AbstractDataAccessTests extends AbstractTransactionalJUnit4SpringContextTests {
}
