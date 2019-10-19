package com.mybatis.mapper;

import com.mybatis.mapper.PayInOrderMapper;
import com.mybatis.pojo.PayInOrder;
import com.mybatis.pojo.PayInOrderExample;

/**
* 降级实现PayInOrderService接口
*/
public class PayInOrderServiceMock extends BaseServiceMock<PayInOrderMapper, PayInOrder, PayInOrderExample> implements PayInOrderService {

}
