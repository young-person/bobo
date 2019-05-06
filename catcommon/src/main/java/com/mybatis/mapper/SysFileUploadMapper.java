package com.mybatis.mapper;

import com.mybatis.pojo.SysFileUpload;

public interface SysFileUploadMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysFileUpload record);

    int insertSelective(SysFileUpload record);

    SysFileUpload selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysFileUpload record);

    int updateByPrimaryKey(SysFileUpload record);
}