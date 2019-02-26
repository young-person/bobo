package com.app.pay.service.impl;

import com.app.common.PayPayService;
import com.app.pay.service.BaseServiceImpl;
import com.bobo.annotation.BaseService;
import com.mybatis.mapper.PayPayMapper;
import com.mybatis.pojo.PayPay;
import com.mybatis.pojo.PayPayExample;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* PayPayService实现
*/
@Service
@Transactional
@BaseService
public class PayPayServiceImpl extends BaseServiceImpl<PayPayMapper, PayPay, PayPayExample> implements PayPayService {

    private static final Logger logger = LoggerFactory.getLogger(PayPayServiceImpl.class);

    @Autowired
    PayPayMapper payPayMapper;

}