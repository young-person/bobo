package com.mybatis.mapper;

import com.mybatis.pojo.PayMch;
import com.mybatis.pojo.PayMchExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PayMchMapper {
    long countByExample(PayMchExample example);

    int deleteByExample(PayMchExample example);

    int deleteByPrimaryKey(Integer payMchId);

    int insert(PayMch record);

    int insertSelective(PayMch record);

    List<PayMch> selectByExample(PayMchExample example);

    PayMch selectByPrimaryKey(Integer payMchId);

    int updateByExampleSelective(@Param("record") PayMch record, @Param("example") PayMchExample example);

    int updateByExample(@Param("record") PayMch record, @Param("example") PayMchExample example);

    int updateByPrimaryKeySelective(PayMch record);

    int updateByPrimaryKey(PayMch record);
}