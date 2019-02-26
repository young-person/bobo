package com.app.common;

import com.mybatis.mapper.PayMchMapper;
import com.mybatis.pojo.PayMch;
import com.mybatis.pojo.PayMchExample;

/**
* 降级实现PayMchService接口
*/
public class PayMchServiceMock extends BaseServiceMock<PayMchMapper, PayMch, PayMchExample> implements PayMchService {

}
