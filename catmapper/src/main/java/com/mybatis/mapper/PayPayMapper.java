package com.mybatis.mapper;

import com.mybatis.pojo.PayPay;
import com.mybatis.pojo.PayPayExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PayPayMapper {
    long countByExample(PayPayExample example);

    int deleteByExample(PayPayExample example);

    int deleteByPrimaryKey(Integer payPayId);

    int insert(PayPay record);

    int insertSelective(PayPay record);

    List<PayPay> selectByExample(PayPayExample example);

    PayPay selectByPrimaryKey(Integer payPayId);

    int updateByExampleSelective(@Param("record") PayPay record, @Param("example") PayPayExample example);

    int updateByExample(@Param("record") PayPay record, @Param("example") PayPayExample example);

    int updateByPrimaryKeySelective(PayPay record);

    int updateByPrimaryKey(PayPay record);
}