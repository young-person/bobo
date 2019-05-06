package com.app.dictionary.handler;

import java.util.List;
import java.util.Map;

public interface SimpleFilter {
    public List<Map<String,Object>> simpleExchange(List<Map<String,Object>> list);

    public List<String> filterTables(List<Map<String, Object>> list);
}
