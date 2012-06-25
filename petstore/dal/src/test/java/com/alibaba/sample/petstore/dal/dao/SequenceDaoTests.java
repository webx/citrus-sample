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

package com.alibaba.sample.petstore.dal.dao;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

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
