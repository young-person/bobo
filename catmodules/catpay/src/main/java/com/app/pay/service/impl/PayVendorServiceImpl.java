package com.app.pay.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.pay.service.BaseServiceImpl;
import com.bobo.annotation.BaseService;
import com.mybatis.mapper.PayVendorMapper;
import com.mybatis.mapper.PayVendorService;
import com.mybatis.pojo.PayVendor;
import com.mybatis.pojo.PayVendorExample;

/**
* PayVendorService实现
*/
@Service
@Transactional
@BaseService
public class PayVendorServiceImpl extends BaseServiceImpl<PayVendorMapper, PayVendor, PayVendorExample> implements PayVendorService {

    @Autowired
    PayVendorMapper payVendorMapper;

}