package com.tzplatform.service.test.impl;

import com.tzplatform.dao.test.TestDao;
import com.tzplatform.service.test.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service(value = "testService")
public class TestServiceImpl implements TestService {

    @Resource
    private TestDao testDao;

    @Override
    public Integer resultOne() {
        return testDao.resultOne();
    }
}
