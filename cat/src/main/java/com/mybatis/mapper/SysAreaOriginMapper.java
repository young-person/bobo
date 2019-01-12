package com.mybatis.mapper;

import com.mybatis.pojo.SysAreaOrigin;

public interface SysAreaOriginMapper {
    int deleteByPrimaryKey(String id);

    int insert(SysAreaOrigin record);

    int insertSelective(SysAreaOrigin record);

    SysAreaOrigin selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysAreaOrigin record);

    int updateByPrimaryKey(SysAreaOrigin record);
}