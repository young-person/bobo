package com.mybatis.mapper;

import com.mybatis.pojo.SysApp;
import com.mybatis.pojo.SysAppKey;

public interface SysAppMapper {
    int deleteByPrimaryKey(SysAppKey key);

    int insert(SysApp record);

    int insertSelective(SysApp record);

    SysApp selectByPrimaryKey(SysAppKey key);

    int updateByPrimaryKeySelective(SysApp record);

    int updateByPrimaryKey(SysApp record);
}