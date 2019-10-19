package com.mybatis.mapper;

import com.mybatis.pojo.PayVest;
import com.mybatis.pojo.PayVestExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PayVestMapper {
    long countByExample(PayVestExample example);

    int deleteByExample(PayVestExample example);

    int deleteByPrimaryKey(Integer payVestId);

    int insert(PayVest record);

    int insertSelective(PayVest record);

    List<PayVest> selectByExample(PayVestExample example);

    PayVest selectByPrimaryKey(Integer payVestId);

    int updateByExampleSelective(@Param("record") PayVest record, @Param("example") PayVestExample example);

    int updateByExample(@Param("record") PayVest record, @Param("example") PayVestExample example);

    int updateByPrimaryKeySelective(PayVest record);

    int updateByPrimaryKey(PayVest record);
}