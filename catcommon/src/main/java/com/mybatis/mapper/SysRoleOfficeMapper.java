package com.mybatis.mapper;

import com.mybatis.pojo.SysRoleOfficeExample;
import com.mybatis.pojo.SysRoleOfficeKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysRoleOfficeMapper {
    long countByExample(SysRoleOfficeExample example);

    int deleteByExample(SysRoleOfficeExample example);

    int deleteByPrimaryKey(SysRoleOfficeKey key);

    int insert(SysRoleOfficeKey record);

    int insertSelective(SysRoleOfficeKey record);

    List<SysRoleOfficeKey> selectByExample(SysRoleOfficeExample example);

    int updateByExampleSelective(@Param("record") SysRoleOfficeKey record, @Param("example") SysRoleOfficeExample example);

    int updateByExample(@Param("record") SysRoleOfficeKey record, @Param("example") SysRoleOfficeExample example);
}