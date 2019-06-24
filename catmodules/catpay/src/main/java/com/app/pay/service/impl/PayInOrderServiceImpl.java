package com.app.pay.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.common.PayInOrderService;
import com.app.pay.service.BaseServiceImpl;
import com.bobo.annotation.BaseService;
import com.mybatis.mapper.PayInOrderMapper;
import com.mybatis.pojo.PayInOrder;
import com.mybatis.pojo.PayInOrderExample;

/**
* PayInOrderService实现
*/
@Service
@Transactional
@BaseService
public class PayInOrderServiceImpl extends BaseServiceImpl<PayInOrderMapper, PayInOrder, PayInOrderExample> implements PayInOrderService {

    @Autowired
    PayInOrderMapper payInOrderMapper;

}