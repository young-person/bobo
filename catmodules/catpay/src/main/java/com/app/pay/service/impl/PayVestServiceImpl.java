package com.app.pay.service.impl;

import com.app.common.PayVestService;
import com.app.pay.service.BaseServiceImpl;
import com.bobo.annotation.BaseService;
import com.mybatis.mapper.PayVestMapper;
import com.mybatis.pojo.PayVest;
import com.mybatis.pojo.PayVestExample;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* PayVestService实现
*/
@Service
@Transactional
@BaseService
public class PayVestServiceImpl extends BaseServiceImpl<PayVestMapper, PayVest, PayVestExample> implements PayVestService {

    private static final Logger log = LoggerFactory.getLogger(PayVestServiceImpl.class);

    @Autowired
    PayVestMapper payVestMapper;

}