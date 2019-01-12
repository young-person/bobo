package com.mybatis.mapper;

import com.mybatis.pojo.SysDictDetail;

public interface SysDictDetailMapper {
    int deleteByPrimaryKey(Integer detail_id);

    int insert(SysDictDetail record);

    int insertSelective(SysDictDetail record);

    SysDictDetail selectByPrimaryKey(Integer detail_id);

    int updateByPrimaryKeySelective(SysDictDetail record);

    int updateByPrimaryKey(SysDictDetail record);
}