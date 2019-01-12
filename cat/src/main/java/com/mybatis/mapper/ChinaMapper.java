package com.mybatis.mapper;

import com.mybatis.pojo.China;
import com.mybatis.pojo.ChinaExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ChinaMapper {
    long countByExample(ChinaExample example);

    int deleteByExample(ChinaExample example);

    int insert(China record);

    int insertSelective(China record);

    List<China> selectByExample(ChinaExample example);

    int updateByExampleSelective(@Param("record") China record, @Param("example") ChinaExample example);

    int updateByExample(@Param("record") China record, @Param("example") ChinaExample example);
}