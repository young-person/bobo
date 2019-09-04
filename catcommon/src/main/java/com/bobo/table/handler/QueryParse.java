package com.bobo.table.handler;

import com.bobo.table.bean.Baseid;
import com.bobo.table.bean.SimpleResult;

import java.util.List;

/**
 * 数据查询
 * @param <E>
 */
public interface QueryParse<E> {

    /**
     * 简单数据查询
     * @param result
     * @param where
     * @param dimensions
     * @param baseids
     * @param from
     * @return
     */
    SimpleResult parse(E result, String where, String dimensions, List<Baseid> baseids, String from);
}
