package com.mybatis.mapper;

import java.util.List;

import com.mybatis.pojo.SysOrganize;

public interface SysOrganizeMapper {
    int deleteByPrimaryKey(String id);

    int insert(SysOrganize record);

    int insertSelective(SysOrganize record);

    SysOrganize selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysOrganize record);

    int updateByPrimaryKeyWithBLOBs(SysOrganize record);

    int updateByPrimaryKey(SysOrganize record);
    
    List<SysOrganize> selectAll();
}