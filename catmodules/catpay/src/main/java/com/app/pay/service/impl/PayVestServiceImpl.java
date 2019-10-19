package com.app.pay.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.pay.service.BaseServiceImpl;
import com.bobo.annotation.BaseService;
import com.mybatis.mapper.PayVestMapper;
import com.mybatis.mapper.PayVestService;
import com.mybatis.pojo.PayVest;
import com.mybatis.pojo.PayVestExample;

/**
* PayVestService实现
*/
@Service
@Transactional
@BaseService
public class PayVestServiceImpl extends BaseServiceImpl<PayVestMapper, PayVest, PayVestExample> implements PayVestService {

    @Autowired
    PayVestMapper payVestMapper;

}