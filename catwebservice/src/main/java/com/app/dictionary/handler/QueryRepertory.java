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

    public static final String TABLE_Name = "TABLENAME";
    public static final String TABLE_Comments = "tableComments";
    public static final String COLUMN_Name = "COLUMNNAME";
    public static final String COLUMN_Comments = "comments";

    public static final String _TYPE = "TYPE";
    public static final String _LENGTH = "LENGTH";

    public static final String _PK = "PK";

}
