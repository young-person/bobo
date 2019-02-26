package com.app.duddo.service.impl;


import com.app.duddo.service.LogService;
import com.bobo.domain.ConditionQuery;
import com.bobo.domain.PageBean;
import com.bobo.serializer.CObjectSerializer;
import com.mybatis.pojo.SysLog;
import lombok.RequiredArgsConstructor;
import org.apache.zookeeper.ZooKeeper;

import java.util.List;

/**
 * zookeeper日志 接口实现
 */
@RequiredArgsConstructor
public class ZookeeperLogServiceImpl implements LogService<SysLog> {

    private final ZooKeeper zooKeeper;

    private final CObjectSerializer objectSerializer;

    @Override
    public PageBean<SysLog> listByPage(ConditionQuery query) {
        return null;
    }

    @Override
    public Boolean batchRemove(List<String> ids, String appName) {
        return null;
    }

    @Override
    public Boolean updateRetry(String id, Integer retry, String appName) {
        return null;
    }
}
