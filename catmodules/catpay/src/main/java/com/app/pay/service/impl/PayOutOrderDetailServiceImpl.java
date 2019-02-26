package com.app.pay.service.impl;

import com.app.common.PayOutOrderDetailService;
import com.app.pay.service.BaseServiceImpl;
import com.bobo.annotation.BaseService;
import com.mybatis.mapper.PayOutOrderDetailMapper;
import com.mybatis.pojo.PayOutOrderDetail;
import com.mybatis.pojo.PayOutOrderDetailExample;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* PayOutOrderDetailService实现
*/
@Service
@Transactional
@BaseService
public class PayOutOrderDetailServiceImpl extends BaseServiceImpl<PayOutOrderDetailMapper, PayOutOrderDetail, PayOutOrderDetailExample> implements PayOutOrderDetailService {

    private static final Logger logger = LoggerFactory.getLogger(PayOutOrderDetailServiceImpl.class);

    @Autowired
    PayOutOrderDetailMapper payOutOrderDetailMapper;

}