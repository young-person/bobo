package com.bobo.table.handler.impl;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.util.JdbcConstants;
import com.bobo.base.BaseClass;
import com.bobo.base.CatException;
import com.bobo.table.bean.Baseid;
import com.bobo.table.bean.Condition;
import com.bobo.table.bean.Dimension;
import com.bobo.table.handler.QueryParse;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 条件解析
 */
public class ParseCondition extends BaseClass {
    final String charsetName = "UTF-8";

    /**
     * 匹配占位符
     */
    final static Pattern PATTERN = Pattern.compile("\\$\\{[^}]+\\}");

    public static enum Special {
        KSSJ("${KSSJ}"),
        JSSJ("${JSSJ}"),
        COND("${COND}");
        private String serialize;

        Special(final String serialize) {
            this.serialize = serialize;
        }

        public static Special acquire(final String serialize) {
            Optional<Special> serializeEnum =
                    Arrays.stream(Special.values())
                            .filter(v -> Objects.equals(v.getSerialize(), serialize))
                            .findFirst();
            return serializeEnum.orElse(Special.KSSJ);
        }

        public String getSerialize() {
            return serialize;
        }

        public void setSerialize(final String serialize) {
            this.serialize = serialize;
        }
    }

    private Condition condition;

    public ParseCondition(Condition condition) {
        this.condition = condition;
    }


    /**
     * 实时sql查询 根据不同sql需要进行sql语句调整
     */
     public StringBuilder getWhereConditionRealSql(final Baseid baseid) {
        Map<String, String> condMap = this.condition.getCondMap();
        StringBuilder builder = this.getCommonCondition(baseid.getSql(), condMap);
        this.condMapAppendToSql(builder, baseid.getCondition());
        return builder;
    }
    /**
     * 获取正常SUM sql
     *
     * @return
     */
    public StringBuilder getWhereConditionSql(final Baseid baseid) {
        return this.getWhereConditionRealSql(baseid);
    }

    /**
     * 进行 特殊处理key：【sql】
     * @param condMap
     * @param builder
     * @return
     */
    private StringBuilder dealWithSql(Map<String,String> condMap,StringBuilder builder){
        Set<Map.Entry<String, String>> set = condMap.entrySet();
        for (Map.Entry<String, String> map : set) {
            String key = map.getKey();
            String v = map.getValue();

            if (StringUtils.isBlank(v))
                continue;
            if ("sql".equals(key)) {
                String sql = condMap.get(key);
                if (StringUtils.isNotBlank(sql)) {
                    String value = null;
                    try {
                        value = Base64.getEncoder().encodeToString(sql.getBytes(charsetName));
                        builder.append(value);
                    } catch (UnsupportedEncodingException e) {
                        LOGGER.error("getWhereCondition encodeToString is fail.... key:[{}],value:[{}]", key, value, e);
                    }
                }
                break;
            }
        }
        return builder;
    }

    /**
     * 对sql 进行通用处理
     * @param sql
     * @param condMap
     * @return
     */
    private StringBuilder getCommonCondition(String sql,Map<String, String> condMap){
        Matcher matcher = PATTERN.matcher(sql);
        Set<String> groups = new HashSet<>();
        while (matcher.find()) {
            groups.add(matcher.group());
        }
        Set<Map.Entry<String, String>> set = condMap.entrySet();
        for (String s : groups) {
            for (Map.Entry<String, String> entry : set) {
                String k = entry.getKey();
                String v = entry.getValue();
                if (s.substring(1, s.length() - 2).equals(k)) {
                    sql.replace(s, v);
                    condMap.remove(k);
                }
            }
        }
        return this.dealWithSql(condMap, new StringBuilder(sql));
    }

    /**
     * 将还未处理的条件件进行sql处理
     * @param builder
     * @param condMap
     */
    protected void condMapAppendToSql(StringBuilder builder,Map<String,Object> condMap){
        if(MapUtils.isNotEmpty(condMap)){
            condMap.forEach((key,value)->{
                if(value instanceof Integer){

                }else if(value instanceof String){

                }else if(value instanceof Float){

                }else if(value instanceof Double){

                }else{
                    throw new CatException("超出定义类型范围");
                }
            });
        }
    }

    /**
     * 获取最下层维度
     *
     * @return
     */
    public List<String> getDimensionValues() {
        List<String> hd = new ArrayList<>();
        Dimension hdimension = this.condition.getHdimensions().get(this.condition.getHdimensions().size() - 1);
        Dimension vdimension = this.condition.getVdimensions().get(this.condition.getVdimensions().size() - 1);
        getNames(hdimension, hdimension.getNext(), hd);
        getNames(vdimension, vdimension.getNext(), hd);
        return hd;
    }





    private Dimension exist(Dimension dimension, String key) {
        if (null != dimension) {
            String id = dimension.getId();
            if (id.equals(key)) {
                checkDimensionValue(dimension, this.condition.getCondMap());
                return dimension;
            } else {
                while (null != dimension.getNext()) {
                    if (dimension.getNext().getId().equals(key)) {
                        checkDimensionValue(dimension, this.condition.getCondMap());
                        return dimension;
                    }
                }
            }
        }
        return null;
    }

    private void checkDimensionValue(Dimension dimension, Map<String, String> condMap) {
        Set<Map.Entry<String, String>> set = condMap.entrySet();
        for (Map.Entry<String, String> entry : set) {
            if (dimension.getId().equals(entry.getKey()))
                dimension.setValue(condMap.get(entry.getKey()));
        }
    }

    private void getNames(Dimension dimension, Dimension next, List<String> list) {
        if (null == next) {
            list.add(dimension.getId());
            return;
        }
        getNames(dimension, dimension.getNext(), list);
    }

    private void spellStr(String key, StringBuilder builder, Map<String, String> condMap) {
        builder.append(key);
        builder.append(" = ");
        builder.append(condMap.get(key));
    }


    /**
     * 将同表名称，条件相同，维度相同进行查询合并。
     * @param map
     * @param queryParse
     */
    public void getCommonSql(Map<String, List<Baseid>> map, QueryParse queryParse){
        DruidDataSource dataSource = queryParse.getDynamicDataSource();
        String dbType = dataSource.getDbType();

        switch (dbType) {
            case JdbcConstants.MYSQL:
                break;
            case JdbcConstants.ORACLE:
                break;
            case JdbcConstants.SYBASE:
                break;
            case JdbcConstants.SQL_SERVER:
                break;
        }

        Set<Map.Entry<String, List<Baseid>>> set  = map.entrySet();
        String sql = null;
        List<String> hd = this.getDimensionValues();

        for(Map.Entry<String, List<Baseid>> entry : set){
            String key = entry.getKey();
            List<Baseid> list = entry.getValue();
            for(Baseid baseid : list){
                String table_col = baseid.getTable_col();
                char point = '.';
                String tableName = null;
                if (StringUtils.isNotBlank(table_col) &&  table_col.contains(String.valueOf(point))) {
                    tableName = table_col.split(String.valueOf(point))[0];
                    sql = this.getWhereConditionSql(baseid).toString();
                }else{
                    sql = this.getWhereConditionSql(baseid).toString();
                }
                if(Objects.nonNull(tableName)){
                    for(Map.Entry<String, List<Baseid>> e : set){
                        String k = e.getKey();
                        if (!key.equals(k)){
                            List<Baseid> compares = e.getValue();
                            for(Baseid b : compares){
                                if (StringUtils.isNotBlank(table_col) && table_col.indexOf(point) > -1) {
                                    String name = table_col.split(String.valueOf(point))[0];
                                    if(tableName.equals(name) && StringUtils.trimToEmpty(baseid.getSql()).equals(b.getSql()) ){//属于同类

                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}
