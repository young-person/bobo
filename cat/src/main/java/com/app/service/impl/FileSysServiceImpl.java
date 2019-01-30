package com.app.service.impl;

import com.app.common.FileSysService;
import com.mybatis.mapper.FiletableMapper;
import com.mybatis.pojo.Filetable;
import com.mybatis.pojo.FiletableExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class FileSysServiceImpl implements FileSysService {
    @Autowired
    private FiletableMapper filetableMapper;
    @Override
    public List<Filetable> queryFileToTables(String pid) {
        FiletableExample example = new FiletableExample();
        example.createCriteria().andPidEqualTo(pid);
        List<com.mybatis.pojo.Filetable> list = filetableMapper.selectByExample(example);
        return null;
    }

    @Override
    public boolean saveOneFileToSysDisk(Filetable filetable) {
        return false;
    }

    @Override
    public int deleteFilesOnRemoteDisk(Filetable... obj) {
        return 0;
    }
}
