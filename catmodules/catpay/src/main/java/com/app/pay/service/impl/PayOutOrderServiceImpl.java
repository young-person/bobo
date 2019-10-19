package com.app.pay.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.pay.service.BaseServiceImpl;
import com.bobo.annotation.BaseService;
import com.mybatis.mapper.PayOutOrderMapper;
import com.mybatis.mapper.PayOutOrderService;
import com.mybatis.pojo.PayOutOrder;
import com.mybatis.pojo.PayOutOrderExample;

/**
* PayOutOrderService实现
*/
@Service
@Transactional
@BaseService
public class PayOutOrderServiceImpl extends BaseServiceImpl<PayOutOrderMapper, PayOutOrder, PayOutOrderExample> implements PayOutOrderService {

    @Autowired
    PayOutOrderMapper payOutOrderMapper;

}