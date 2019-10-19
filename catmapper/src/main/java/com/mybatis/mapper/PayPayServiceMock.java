package com.mybatis.mapper;

import com.mybatis.mapper.PayPayMapper;
import com.mybatis.pojo.PayPay;
import com.mybatis.pojo.PayPayExample;

/**
* 降级实现PayPayService接口
*/
public class PayPayServiceMock extends BaseServiceMock<PayPayMapper, PayPay, PayPayExample> implements PayPayService {

}
