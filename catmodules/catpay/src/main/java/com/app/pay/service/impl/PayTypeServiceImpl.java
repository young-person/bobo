package com.app.pay.service.impl;


import com.app.common.PayTypeService;
import com.app.pay.service.BaseServiceImpl;
import com.bobo.annotation.BaseService;
import com.mybatis.mapper.PayTypeMapper;
import com.mybatis.pojo.PayType;
import com.mybatis.pojo.PayTypeExample;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* PayTypeService实现
*/
@Service
@Transactional
@BaseService
public class PayTypeServiceImpl extends BaseServiceImpl<PayTypeMapper, PayType, PayTypeExample> implements PayTypeService {

    private static final Logger logger = LoggerFactory.getLogger(PayTypeServiceImpl.class);

    @Autowired
    PayTypeMapper payTypeMapper;

}