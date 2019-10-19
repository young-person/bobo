package com.mybatis.mapper;

import com.mybatis.pojo.SysOrganizeRelation;

public interface SysOrganizeRelationMapper {
    int insert(SysOrganizeRelation record);

    int insertSelective(SysOrganizeRelation record);
}