package com.app.pay.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.common.PayInOrderDetailService;
import com.app.pay.service.BaseServiceImpl;
import com.bobo.annotation.BaseService;
import com.mybatis.mapper.PayInOrderDetailMapper;
import com.mybatis.pojo.PayInOrderDetail;
import com.mybatis.pojo.PayInOrderDetailExample;

/**
* PayInOrderDetailService实现
*/
@Service
@Transactional
@BaseService
public class PayInOrderDetailServiceImpl extends BaseServiceImpl<PayInOrderDetailMapper, PayInOrderDetail, PayInOrderDetailExample> implements PayInOrderDetailService {

    @Autowired
    PayInOrderDetailMapper payInOrderDetailMapper;

}