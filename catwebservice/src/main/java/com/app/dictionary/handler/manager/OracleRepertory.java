package com.app.dictionary.handler.manager;

import com.bobo.dbconnection.ConnectionHolderDefault;
import com.mybatis.pojo.Dbs;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class OracleRepertory extends AbstractRepertory<Dbs> {
    @Override
    public List<String> queryTablesByConnection(Dbs dbs) {
        String sql = "select table_name TABLENAME from user_tables";
        ConnectionHolderDefault holderDefault = new ConnectionHolderDefault();
        List<Map<String, Object>> list = holderDefault.query(dbs, sql);
        return filter.filterTables(list);
    }

    @Override
    public List<Map<String, Object>> queryColumnsByDB(Dbs dbs) {

        String sql = "SELECT NULLABLE,table_name TABLENAME,column_name COLUMNNAME,data_type TYPE,data_length LENGTH,(SELECT COMMENTS FROM user_col_comments WHERE user_tab_columns.TABLE_NAME = user_col_comments.TABLE_NAME AND user_tab_columns.COLUMN_NAME= user_col_comments.COLUMN_NAME) TABLECOMMENTS FROM user_tab_columns where 1 = 1";

        ConnectionHolderDefault holderDefault = new ConnectionHolderDefault();
        List<Map<String, Object>> list = holderDefault.query(dbs, sql);
        List<Map<String, Object>> allPks = queryAllPkByDB(dbs);
        if (Objects.nonNull(allPks)){
            for(Map<String, Object> map : allPks){
                String tableNname = (String)map.get("TABLENAME");
                String columnName = (String)map.get("COLUMNNAME");
                for(Map<String,Object> m: list){
                    if (tableNname.equals(m.get("TABLENAME")) && columnName.equals(m.get("COLUMNNAME"))){
                        m.put(_PK,"Y");
                    }
                }
            }
        }

        return filter.simpleExchange(list);
    }

    @Override
    protected List<Map<String, Object>> queryAllPkByDB(Dbs dbs) {
        String sql = "select  B.owner,B.constraint_name,B.table_name TABLENAME,B.column_name COLUMNNAME,B.position from user_constraints A,user_cons_columns B where A.constraint_name=B.constraint_name and A.constraint_type='P'";
        ConnectionHolderDefault holderDefault = new ConnectionHolderDefault();
        List<Map<String, Object>> list = holderDefault.query(dbs,sql);
        return list;
    }


    private boolean isAllLowerCase(String table){
        boolean sure = false;
        for(int i = 0;i < table.length();i++){
            if (Character.isLowerCase(table.charAt(i))){
                sure = true;
                break;
            }
        }
        return sure;
    }
}
