package com.app.pay.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.pay.service.BaseServiceImpl;
import com.bobo.annotation.BaseService;
import com.mybatis.mapper.PayPayMapper;
import com.mybatis.mapper.PayPayService;
import com.mybatis.pojo.PayPay;
import com.mybatis.pojo.PayPayExample;

/**
* PayPayService实现
*/
@Service
@Transactional
@BaseService
public class PayPayServiceImpl extends BaseServiceImpl<PayPayMapper, PayPay, PayPayExample> implements PayPayService {

    @Autowired
    PayPayMapper payPayMapper;

}