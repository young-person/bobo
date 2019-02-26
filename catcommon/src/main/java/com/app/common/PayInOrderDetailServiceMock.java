package com.app.common;

import com.mybatis.mapper.PayInOrderDetailMapper;
import com.mybatis.pojo.PayInOrderDetail;
import com.mybatis.pojo.PayInOrderDetailExample;

/**
* 降级实现PayInOrderDetailService接口
*/
public class PayInOrderDetailServiceMock extends BaseServiceMock<PayInOrderDetailMapper, PayInOrderDetail, PayInOrderDetailExample> implements PayInOrderDetailService {

}
