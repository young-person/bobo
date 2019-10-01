package com.bobo.table.handler;

import java.util.Map;

public interface ConditionFilter {
    void doFilter(String key, StringBuilder builder, Map<String,String> condMap);
}
