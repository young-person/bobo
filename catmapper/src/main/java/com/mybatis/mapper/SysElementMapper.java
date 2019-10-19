package com.mybatis.mapper;

import com.mybatis.pojo.SysElement;
import com.mybatis.pojo.SysElementKey;

public interface SysElementMapper {
    int deleteByPrimaryKey(SysElementKey key);

    int insert(SysElement record);

    int insertSelective(SysElement record);

    SysElement selectByPrimaryKey(SysElementKey key);

    int updateByPrimaryKeySelective(SysElement record);

    int updateByPrimaryKey(SysElement record);
}