package com.app.pay.service.impl;

import com.app.common.PayVendorService;
import com.app.pay.service.BaseServiceImpl;
import com.bobo.annotation.BaseService;
import com.mybatis.mapper.PayVendorMapper;
import com.mybatis.pojo.PayVendor;
import com.mybatis.pojo.PayVendorExample;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
* PayVendorService实现
*/
@Service
@Transactional
@BaseService
public class PayVendorServiceImpl extends BaseServiceImpl<PayVendorMapper, PayVendor, PayVendorExample> implements PayVendorService {

    private static final Logger logger = LoggerFactory.getLogger(PayVendorServiceImpl.class);

    @Autowired
    PayVendorMapper payVendorMapper;

}