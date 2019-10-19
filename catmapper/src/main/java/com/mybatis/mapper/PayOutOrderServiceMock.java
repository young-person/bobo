package com.mybatis.mapper;


import com.mybatis.mapper.PayOutOrderMapper;
import com.mybatis.pojo.PayOutOrder;
import com.mybatis.pojo.PayOutOrderExample;

/**
* 降级实现PayOutOrderService接口
*/
public class PayOutOrderServiceMock extends BaseServiceMock<PayOutOrderMapper, PayOutOrder, PayOutOrderExample> implements PayOutOrderService {

}
