package com.app.pay.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.common.PayOutOrderDetailService;
import com.app.pay.service.BaseServiceImpl;
import com.bobo.annotation.BaseService;
import com.mybatis.mapper.PayOutOrderDetailMapper;
import com.mybatis.pojo.PayOutOrderDetail;
import com.mybatis.pojo.PayOutOrderDetailExample;

/**
* PayOutOrderDetailService实现
*/
@Service
@Transactional
@BaseService
public class PayOutOrderDetailServiceImpl extends BaseServiceImpl<PayOutOrderDetailMapper, PayOutOrderDetail, PayOutOrderDetailExample> implements PayOutOrderDetailService {

    @Autowired
    PayOutOrderDetailMapper payOutOrderDetailMapper;

}