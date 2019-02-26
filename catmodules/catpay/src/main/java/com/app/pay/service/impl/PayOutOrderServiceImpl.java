package com.app.pay.service.impl;

import com.app.common.PayOutOrderService;
import com.app.pay.service.BaseServiceImpl;
import com.bobo.annotation.BaseService;
import com.mybatis.mapper.PayOutOrderMapper;
import com.mybatis.pojo.PayOutOrder;
import com.mybatis.pojo.PayOutOrderExample;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* PayOutOrderService实现
*/
@Service
@Transactional
@BaseService
public class PayOutOrderServiceImpl extends BaseServiceImpl<PayOutOrderMapper, PayOutOrder, PayOutOrderExample> implements PayOutOrderService {

    private static final Logger log = LoggerFactory.getLogger(PayOutOrderServiceImpl.class);

    @Autowired
    PayOutOrderMapper payOutOrderMapper;

}