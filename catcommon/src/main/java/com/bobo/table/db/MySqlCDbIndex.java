package com.bobo.table.db;

import com.bobo.enums.IndexEnum;
import com.bobo.utils.JdbcUtil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MySqlCDbIndex extends CAbstractDbIndex<DBTable> {

    private static String driver = "com.mysql.cj.jdbc.Driver";
    private static String url = "jdbc:mysql://localhost:3307/datas?useSSL=false&serverTimezone=GMT%2B8";
    private static String username = "root";
    private static String password = "199345";

    private static volatile List<DBTable> cache = null;

    @Override
    public List<DBTable> loadTablesFromDbs() {
        try{
            if (null == cache){
                synchronized (MySqlCDbIndex.class){
                    if (null == cache){
                        JdbcUtil jdbcUtil = new JdbcUtil(driver,  url,  username,  password);
                        List<Map<String,Object>> result1 = jdbcUtil.selectByParams("select table_name，table_rows from information_schema.TABLES " +
                                        "where " +
                                        "table_schema = " +
                                        "'datas' ",
                                null);
                        /**
                         * 查询所有主键
                         */
                        List<Map<String,Object>> result2 = jdbcUtil.selectByParams("select table_name, constraint_name," +
                                "column_name from information_schema.KEY_COLUMN_USAGE where " +
                                "table_schema " +
                                "= 'datas'",null);
                        /**
                         * 增对 双主键特殊处理
                         */
                        List<Map<String,Object>> result3 = jdbcUtil.selectByParams("select table_name, column_name, is_nullable, " +
                                        "data_type," +
                                        "column_type,column_key,extra from information_schema.columns where " +
                                        "table_schema = 'datas'",
                                null);

                        for(Map<String,Object> map1 : result1){
                            String name1 = (String) map1.get("table_name");
                            int table_rows = (Integer) map1.get("table_rows");
                            List<DBColumn> columns = new ArrayList<>();
                            for(Map<String,Object> map3 : result3){
                                String name3 = (String) map3.get("table_name");
                                if (name1.equals(name3)){
                                    String column_name = (String) map3.get("column_name");
                                    String is_nullable = (String) map3.get("is_nullable");
                                    String data_type = (String) map3.get("data_type");
                                    String column_type = (String) map3.get("column_type");
                                    String column_key = (String) map3.get("column_key");

                                    DBColumn column = new DBColumn(column_name);
                                    column.setType(data_type);
                                    switch (column_key) {
                                        case "PRI":
                                            column.setCindex(IndexEnum.PRIMAY);
                                            break;
                                        case "UNI":
                                            column.setCindex(IndexEnum.UNIQUE);
                                            break;
                                        case "MUL":
                                            column.setCindex(IndexEnum.NORMAL);
                                            break;
                                        default:
                                            break;
                                    }
                                    if ("YES".equals(is_nullable)){
                                        column.setIsnull(true);
                                    }
                                    columns.add(column);
                                }
                            }
                            DBTable dbTable = new DBTable(name1,table_rows,columns);
                            cache.add(dbTable);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cache;
    }

    /**
     * 进行最优索引方案分析选择 最左匹配原则
     *
     * @param list
     */
    @Override
    public void aiAnalysis(List list) {

    }


}
