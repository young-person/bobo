package com.app.pay.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.pay.service.BaseServiceImpl;
import com.bobo.annotation.BaseService;
import com.mybatis.mapper.PayMchMapper;
import com.mybatis.mapper.PayMchService;
import com.mybatis.pojo.PayMch;
import com.mybatis.pojo.PayMchExample;

/**
* PayMchService实现
*/
@Service
@Transactional
@BaseService
public class PayMchServiceImpl extends BaseServiceImpl<PayMchMapper, PayMch, PayMchExample> implements PayMchService {

    @Autowired
    PayMchMapper payMchMapper;

}