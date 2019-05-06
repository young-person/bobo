package com.app.dictionary.handler.manager;
/**
 * mysql数据字典
 */

import com.app.dictionary.handler.QueryRepertory;
import com.bobo.dbconnection.ConnectionHolderDefault;
import com.mybatis.pojo.Dbs;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MysqlRepertory extends AbstractRepertory<Dbs> {

    @Override
    public List<String> queryTablesByConnection(Dbs dbs) {
        StringBuilder builder = new StringBuilder("select TABLE_NAME ");
        builder.append(TABLE_Name);
        builder.append(",TABLE_COMMENT ");
        builder.append(TABLE_Comments);
        builder.append(" from information_schema.tables where TABLE_SCHEMA = ' ");
        builder.append(dbs.getDbname());
        builder.append("'");

        ConnectionHolderDefault holderDefault = new ConnectionHolderDefault();
        List<Map<String, Object>> list = holderDefault.query(dbs, builder.toString());
        return filter.filterTables(list);
    }

    @Override
    public List<Map<String, Object>> queryColumnsByDB(Dbs dbs) {
        String sql = "select TABLE_SCHEMA,TABLE_NAME,COLUMN_NAME,COLLATION_NAME,ORDINAL_POSITION,COLUMN_DEFAULT,IS_NULLABLE,DATA_TYPE,CHARACTER_MAXIMUM_LENGTH,CHARACTER_OCTET_LENGTH,CHARACTER_SET_NAME,COLLATION_NAME,COLUMN_TYPE,COLUMN_KEY,`PRIVILEGES`,COLUMN_COMMENT from information_schema.COLUMNS where table_schema = '" + dbs.getDbname() + "'";
        StringBuilder builder = new StringBuilder("select TABLE_NAME ");
        builder.append(TABLE_Name);
        builder.append(",COLUMN_NAME ");
        builder.append(COLUMN_Name);
        builder.append(",COLUMN_TYPE ");
        builder.append(_TYPE);
        builder.append(",COLUMN_COMMENT ");
        builder.append(COLUMN_Comments);
        builder.append(",IS_NULLABLE ");
        builder.append(NULLABLE);
        builder.append(" from information_schema.COLUMNS where 1 = 1 and table_schema = '");
        builder.append(dbs.getDbname());
        builder.append("'");

        ConnectionHolderDefault holderDefault = new ConnectionHolderDefault();
        List<Map<String, Object>> list = holderDefault.query(dbs, builder.toString());

        List<Map<String, Object>> allPks = queryAllPkByDB(dbs);
        if (Objects.nonNull(allPks)){
            for(Map<String, Object> map : allPks){
                String tableNname = (String)map.get(QueryRepertory.TABLE_Name);
                String columnName = (String)map.get(QueryRepertory.COLUMN_Name);
                for(Map<String,Object> m: list){
                    if (tableNname.equals(m.get(TABLE_Name)) && columnName.equals(m.get(columnName))){
                        m.put(_PK,"Y");
                    }
                }
            }
        }


        return filter.simpleExchange(list);
    }
    @Override
    protected List<Map<String, Object>> queryAllPkByDB(Dbs dbs) {
        StringBuilder builder = new StringBuilder("SELECT TABLE_NAME ");
        builder.append(TABLE_Name);
        builder.append(",COLUMN_NAME ");
        builder.append(COLUMN_Name);
        builder.append("FROM INFORMATION_SCHEMA.`KEY_COLUMN_USAGE` WHERE 1=1 AND constraint_name='PRIMARY'");
        ConnectionHolderDefault holderDefault = new ConnectionHolderDefault();
        List<Map<String, Object>> list = holderDefault.query(dbs, builder.toString());
        return list;
    }
}
