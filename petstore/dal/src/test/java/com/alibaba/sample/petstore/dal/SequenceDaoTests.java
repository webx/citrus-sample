package com.alibaba.sample.petstore.dal;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.sample.petstore.dal.dao.SequenceDao;

public class SequenceDaoTests extends AbstractDataAccessTests {
    @Autowired
    private SequenceDao sequenceDao;

    @Test(expected = IllegalArgumentException.class)
    public void getNextId_illegalName() {
        sequenceDao.getNextId("nonexist");
    }

    @Test
    public void getNextId() {
        assertEquals(1000, sequenceDao.getNextId("ordernum"));
        assertEquals(1001, sequenceDao.getNextId("ordernum"));
        assertEquals(1002, sequenceDao.getNextId("ordernum"));

        assertEquals(1000, sequenceDao.getNextId("orderitemnum"));
        assertEquals(1001, sequenceDao.getNextId("orderitemnum"));
        assertEquals(1002, sequenceDao.getNextId("orderitemnum"));
    }
}
