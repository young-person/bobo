package com.mybatis.mapper;

import com.mybatis.pojo.PayOutOrderDetail;
import com.mybatis.pojo.PayOutOrderDetailExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PayOutOrderDetailMapper {
    long countByExample(PayOutOrderDetailExample example);

    int deleteByExample(PayOutOrderDetailExample example);

    int deleteByPrimaryKey(Integer payOutOrderDetailId);

    int insert(PayOutOrderDetail record);

    int insertSelective(PayOutOrderDetail record);

    List<PayOutOrderDetail> selectByExample(PayOutOrderDetailExample example);

    PayOutOrderDetail selectByPrimaryKey(Integer payOutOrderDetailId);

    int updateByExampleSelective(@Param("record") PayOutOrderDetail record, @Param("example") PayOutOrderDetailExample example);

    int updateByExample(@Param("record") PayOutOrderDetail record, @Param("example") PayOutOrderDetailExample example);

    int updateByPrimaryKeySelective(PayOutOrderDetail record);

    int updateByPrimaryKey(PayOutOrderDetail record);
}