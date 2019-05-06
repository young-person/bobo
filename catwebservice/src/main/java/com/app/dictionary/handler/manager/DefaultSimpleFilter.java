package com.app.dictionary.handler.manager;

import com.app.dictionary.handler.QueryRepertory;
import com.app.dictionary.handler.SimpleFilter;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DefaultSimpleFilter implements SimpleFilter {
    @Override
    public List<Map<String,Object>> simpleExchange(List<Map<String, Object>> list) {

        return list;
    }

    @Override
    public List<String> filterTables(List<Map<String, Object>> list) {
        List<String> tables = new ArrayList<>();
        for (Map<String, Object> m : list) {
            String table = (String) m.get(QueryRepertory.TABLE_Name);
            if (StringUtils.isEmpty(table)) {
                continue;
            }
            tables.add(table);
        }
        return tables;
    }
}
