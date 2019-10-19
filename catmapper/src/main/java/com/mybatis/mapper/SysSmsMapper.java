package com.mybatis.mapper;

import com.mybatis.pojo.SysSms;

public interface SysSmsMapper {
    int insert(SysSms record);

    int insertSelective(SysSms record);
}