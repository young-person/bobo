package com.app.service.impl;

import com.app.common.FileSysService;
import com.mybatis.mapper.FiletableMapper;
import com.mybatis.pojo.Filetable;
import com.mybatis.pojo.FiletableExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class FileSysServiceImpl implements FileSysService {
    @Autowired
    private FiletableMapper filetableMapper;
    @Override
    public List<Filetable> queryFileToTables(String pid) {
        FiletableExample example = new FiletableExample();
        example.createCriteria().andPidEqualTo(pid);
        List<Filetable> list = filetableMapper.selectByExample(example);
        return list;
    }

    @Override
    public boolean saveOneFileToSysDisk(Filetable filetable) {
        int cnt = filetableMapper.insertSelective(filetable);
        return cnt > 0;
    }

    @Override
    public int deleteFilesOnRemoteDisk(List<Filetable> list) {
        int cnt = filetableMapper.deleteArrayByPrimaryKey(list);
        return 0;
    }

    public int updateForModel(Filetable filetable){
        return filetableMapper.updateByUuidKeySelective(filetable);
    }

}
