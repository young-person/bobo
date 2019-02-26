package com.mybatis.mapper;

import com.mybatis.pojo.Filetable;
import com.mybatis.pojo.FiletableExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FiletableMapper {
    long countByExample(FiletableExample example);

    int deleteByExample(FiletableExample example);

    int deleteByPrimaryKey(Integer uid);

    int deleteArrayByPrimaryKey(List<Filetable> list);

    int insert(Filetable record);

    int insertSelective(Filetable record);

    List<Filetable> selectByExample(FiletableExample example);

    Filetable selectByPrimaryKey(Integer uid);

    int updateByExampleSelective(@Param("record") Filetable record, @Param("example") FiletableExample example);

    int updateByExample(@Param("record") Filetable record, @Param("example") FiletableExample example);

    int updateByPrimaryKeySelective(Filetable record);

    int updateByPrimaryKey(Filetable record);

    int updateByUuidKeySelective(Filetable record);
}