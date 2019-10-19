package com.mybatis.mapper;

import com.mybatis.mapper.PayTypeMapper;
import com.mybatis.pojo.PayType;
import com.mybatis.pojo.PayTypeExample;

/**
* 降级实现PayTypeService接口
*/
public class PayTypeServiceMock extends BaseServiceMock<PayTypeMapper, PayType, PayTypeExample> implements PayTypeService {

}
