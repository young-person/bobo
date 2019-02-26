package com.app.common;


import com.mybatis.pojo.Filetable;

import java.util.List;

/**
 * 文件接口
 */
public interface FileSysService {
    public List<Filetable> queryFileToTables(String pid);

    public boolean saveOneFileToSysDisk(Filetable filetable);

    public int deleteFilesOnRemoteDisk(List<Filetable> list);
}
