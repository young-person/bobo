package com.mybatis.mapper;

import com.mybatis.pojo.SysOffice;
import com.mybatis.pojo.SysOfficeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SysOfficeMapper {
    long countByExample(SysOfficeExample example);

    int deleteByExample(SysOfficeExample example);

    int deleteByPrimaryKey(String id);

    int insert(SysOffice record);

    int insertSelective(SysOffice record);

    List<SysOffice> selectByExample(SysOfficeExample example);

    SysOffice selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") SysOffice record, @Param("example") SysOfficeExample example);

    int updateByExample(@Param("record") SysOffice record, @Param("example") SysOfficeExample example);

    int updateByPrimaryKeySelective(SysOffice record);

    int updateByPrimaryKey(SysOffice record);
}