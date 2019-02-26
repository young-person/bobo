package com.app.duddo.service;

import com.bobo.domain.ConditionQuery;
import com.bobo.domain.PageBean;

import java.util.List;

/**
 * 应用 日志接口
 */
public interface LogService<T> {
    /***
     *
     * @param query
     * @return
     */
    PageBean<T> listByPage(ConditionQuery query);

    /**
     * batch remove transaction log by ids.
     *
     * @param ids      ids
     * @param appName  appName
     * @return true
     */
    Boolean batchRemove(List<String> ids, String appName);

    /**
     * modify retry count.
     *
     * @param id      transId
     * @param retry   retry
     * @param appName appName
     * @return true
     */
    Boolean updateRetry(String id, Integer retry, String appName);
}
