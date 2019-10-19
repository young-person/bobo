package com.app.dictionary.handler;

import com.app.dictionary.templet.model.StoreRoom;

import java.util.List;
import java.util.Map;

public interface QueryRepertory<E> {
    /**
     * 获取数据库表名
     * @return
     */
    public List<String> queryTablesByConnection(E dbs);

    /**
     * 获取数据库表的字段
     * @return
     */
    public List<Map<String, Object>> queryColumnsByDB(E dbs);


    public StoreRoom createDBInfOBean(E dbs, String name);

    String TABLE_Name = "TABLENAME";
    String TABLE_Comments = "tableComments";
    String COLUMN_Name = "COLUMNNAME";
    String COLUMN_Comments = "comments";
    String NULLABLE = "NULLABLE";
    String _TYPE = "TYPE";
    String _LENGTH = "LENGTH";

    String _PK = "PK";

}
