package com.mybatis.mapper;

import com.mybatis.pojo.SysSource;
import com.mybatis.pojo.SysSourceKey;

public interface SysSourceMapper {
    int deleteByPrimaryKey(SysSourceKey key);

    int insert(SysSource record);

    int insertSelective(SysSource record);

    SysSource selectByPrimaryKey(SysSourceKey key);

    int updateByPrimaryKeySelective(SysSource record);

    int updateByPrimaryKey(SysSource record);
}