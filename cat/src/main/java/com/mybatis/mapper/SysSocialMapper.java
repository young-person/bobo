package com.mybatis.mapper;

import com.mybatis.pojo.SysSocial;

public interface SysSocialMapper {
    int deleteByPrimaryKey(String id);

    int insert(SysSocial record);

    int insertSelective(SysSocial record);

    SysSocial selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysSocial record);

    int updateByPrimaryKey(SysSocial record);
}