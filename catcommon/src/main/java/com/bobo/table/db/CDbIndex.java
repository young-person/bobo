package com.bobo.table.db;

import java.util.List;

public interface CDbIndex<T> {
    /**
     * 查询DB 里面所有schema信息
     * @return
     */
    public List<T> loadTablesFromDbs();

    /**
     * 进行最优索引方案分析选择 最左匹配原则
     * @param list
     */
    public void aiAnalysis(List<T> list);


}
