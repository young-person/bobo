package com.app.common;

import com.mybatis.mapper.PayVendorMapper;
import com.mybatis.pojo.PayVendor;
import com.mybatis.pojo.PayVendorExample;

/**
* 降级实现PayVendorService接口
*/
public class PayVendorServiceMock extends BaseServiceMock<PayVendorMapper, PayVendor, PayVendorExample> implements PayVendorService {

}
