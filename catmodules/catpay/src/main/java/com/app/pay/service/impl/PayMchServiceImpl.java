package com.app.pay.service.impl;

import com.app.common.PayMchService;
import com.app.pay.service.BaseServiceImpl;
import com.bobo.annotation.BaseService;
import com.mybatis.mapper.PayMchMapper;
import com.mybatis.pojo.PayMch;
import com.mybatis.pojo.PayMchExample;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* PayMchService实现
*/
@Service
@Transactional
@BaseService
public class PayMchServiceImpl extends BaseServiceImpl<PayMchMapper, PayMch, PayMchExample> implements PayMchService {

    private static final Logger logger = LoggerFactory.getLogger(PayMchServiceImpl.class);

    @Autowired
    PayMchMapper payMchMapper;

}