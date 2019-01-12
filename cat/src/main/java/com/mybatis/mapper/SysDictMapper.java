package com.mybatis.mapper;

import com.mybatis.pojo.SysDict;

public interface SysDictMapper {
    int deleteByPrimaryKey(Integer dict_id);

    int insert(SysDict record);

    int insertSelective(SysDict record);

    SysDict selectByPrimaryKey(Integer dict_id);

    int updateByPrimaryKeySelective(SysDict record);

    int updateByPrimaryKey(SysDict record);
}