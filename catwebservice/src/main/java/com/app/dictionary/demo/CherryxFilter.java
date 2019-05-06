package com.app.dictionary.demo;

import com.app.dictionary.handler.QueryRepertory;
import com.app.dictionary.handler.manager.DefaultSimpleFilter;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CherryxFilter extends DefaultSimpleFilter {
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

            if (table.indexOf("CUC_ORG_") > -1
                    || table.indexOf("CUC_ORG_AUTH_") > -1
                    || table.indexOf("CUC_ORG_USER_") > -1
                    || table.indexOf("CUC_RESOURCE_") > -1
                    || table.indexOf("CUC_ROLE_") > -1
                    || table.indexOf("CUC_ROLE_GROUP_") > -1
                    || table.indexOf("CUC_ROLE_ORG_") > -1
                    || table.indexOf("CUC_ROLE_RESOURCE_") > -1
                    || table.indexOf("CUC_ROLE_USER_") > -1
                    || table.indexOf("CUC_USER_") > -1

            ){
                if (!(table.lastIndexOf("000") > -1)){
                    continue;
                }
            }
            tables.add(table);
        }
        return tables;
    }
}
