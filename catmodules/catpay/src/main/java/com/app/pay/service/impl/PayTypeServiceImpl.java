package com.app.pay.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.common.PayTypeService;
import com.app.pay.service.BaseServiceImpl;
import com.bobo.annotation.BaseService;
import com.mybatis.mapper.PayTypeMapper;
import com.mybatis.pojo.PayType;
import com.mybatis.pojo.PayTypeExample;

/**
* PayTypeService实现
*/
@Service
@Transactional
@BaseService
public class PayTypeServiceImpl extends BaseServiceImpl<PayTypeMapper, PayType, PayTypeExample> implements PayTypeService {

    @Autowired
    PayTypeMapper payTypeMapper;

}