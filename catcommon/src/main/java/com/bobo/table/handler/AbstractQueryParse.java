package com.bobo.table.handler;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractQueryParse<E> implements QueryParse<E>,AutoCloseable{
    public abstract DataSource getDynamicDataSource();

    protected List<Map<String,Object>> queryExec(String sql){
        List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
        DataSource dataSource = getDynamicDataSource();
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            ResultSetMetaData metaData = resultSet.getMetaData();
            int count = metaData.getColumnCount();
            String[] names = new String[count];
            for (int i = 0; i < count; i++) {
                names[i] = metaData.getColumnLabel(i+1);
            }
            while(resultSet.next()){
                Map<String,Object> m = new HashMap<String,Object>();
                for (int i = 0; i < count; i++) {
                    String k = resultSet.getString(names[i]);
                    Object v = resultSet.getObject(k);
                    m.put(k,v);
                    result.add(m);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void close() throws Exception{

    }
}
