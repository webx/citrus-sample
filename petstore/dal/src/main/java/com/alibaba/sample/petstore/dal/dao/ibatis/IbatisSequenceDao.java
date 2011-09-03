package com.alibaba.sample.petstore.dal.dao.ibatis;

import static com.alibaba.citrus.util.Assert.*;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.alibaba.sample.petstore.dal.dao.SequenceDao;
import com.alibaba.sample.petstore.dal.dataobject.Sequence;

public class IbatisSequenceDao extends SqlMapClientDaoSupport implements SequenceDao {
    public synchronized int getNextId(String name) {
        Sequence sequence = new Sequence(name, -1);

        sequence = (Sequence) getSqlMapClientTemplate().queryForObject("getSequence", sequence);

        assertNotNull(sequence, "unknown sequence name: {}", name);

        Object parameterObject = new Sequence(name, sequence.getNextId() + 1);

        getSqlMapClientTemplate().update("updateSequence", parameterObject);

        return sequence.getNextId();
    }
}
