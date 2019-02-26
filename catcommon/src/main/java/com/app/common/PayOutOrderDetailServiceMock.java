package com.app.common;

import com.mybatis.mapper.PayOutOrderDetailMapper;
import com.mybatis.pojo.PayOutOrderDetail;
import com.mybatis.pojo.PayOutOrderDetailExample;

/**
* 降级实现PayOutOrderDetailService接口
*/
public class PayOutOrderDetailServiceMock extends BaseServiceMock<PayOutOrderDetailMapper, PayOutOrderDetail, PayOutOrderDetailExample> implements PayOutOrderDetailService {

}
