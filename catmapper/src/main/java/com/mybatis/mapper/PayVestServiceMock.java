package com.mybatis.mapper;

import com.mybatis.mapper.PayVestMapper;
import com.mybatis.pojo.PayVest;
import com.mybatis.pojo.PayVestExample;

/**
* 降级实现PayVestService接口
*/
public class PayVestServiceMock extends BaseServiceMock<PayVestMapper, PayVest, PayVestExample> implements PayVestService {

}
