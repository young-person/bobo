package com.mybatis.mapper;

import com.mybatis.pojo.PayInOrder;
import com.mybatis.pojo.PayInOrderExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PayInOrderMapper {
    long countByExample(PayInOrderExample example);

    int deleteByExample(PayInOrderExample example);

    int deleteByPrimaryKey(Integer payInOrderId);

    int insert(PayInOrder record);

    int insertSelective(PayInOrder record);

    List<PayInOrder> selectByExample(PayInOrderExample example);

    PayInOrder selectByPrimaryKey(Integer payInOrderId);

    int updateByExampleSelective(@Param("record") PayInOrder record, @Param("example") PayInOrderExample example);

    int updateByExample(@Param("record") PayInOrder record, @Param("example") PayInOrderExample example);

    int updateByPrimaryKeySelective(PayInOrder record);

    int updateByPrimaryKey(PayInOrder record);
}