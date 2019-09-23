package com.bobo.table.handler.impl;

import com.bobo.table.bean.Condition;
import com.bobo.table.bean.Dimension;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * 条件解析
 */
public class ParseCondition {
    final String charsetName = "UTF-8";
    private static final Logger LOGGER = LoggerFactory.getLogger(ParseCondition.class);

    public static enum Special {
        KSSJ("#{KSSJ}"),
        JSSJ("#{JSSJ}"),
        COND("#{COND}");
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

    interface Filter {
        void doFilter(String key, StringBuilder builder, Map<String, String> condMap);
    }

    private Condition condition;

    public ParseCondition(Condition condition) {
        this.condition = condition;
    }

    /**
     * 获取开始日期统计
     */
    public String getWhereConditionFirst() {
        return this.getWhereCondition((key, builder, condMap) -> {
            if (!Special.JSSJ.getSerialize().equals(key)) {
                spellStr(key, builder, condMap);
            }
        });
    }

    /**
     * 获取结束日期统计
     */
    public String getWhereConditionLast() {

        return this.getWhereCondition((key, builder, condMap) -> {
            if (!Special.KSSJ.getSerialize().equals(key)) {
                spellStr(key, builder, condMap);
            }
        });
    }

    /**
     * 实时sql查询 根据不同sql需要进行sql语句调整
     */
    public String getWhereConditionReal(final String sql) {
        final List<String> tmp = new ArrayList<>(1);
        String conditionSql = this.getWhereCondition((key, builder, condMap) -> {
            StringBuilder b = new StringBuilder("@");
            b.append(key);
            b.append("@");
            if (sql.indexOf(b.toString()) > -1) {
                String s = sql.replace(b.toString(), condMap.get(key));
                tmp.set(0, s);
            }
        });
        return tmp.get(0).replace(Special.COND.getSerialize(), conditionSql);
    }

    /**
     * 获取所有维度
     *
     * @return
     */
    public String getDimensionValues() {
        List<String> hd = new ArrayList<>();
        for(Dimension dimension :this.condition.getHdimensions()){
            getNames(dimension, hd);
        }
        for(Dimension dimension :this.condition.getVdimensions()){
            getNames(dimension, hd);
        }
        return String.join(",", hd);
    }

    /**
     * 获取正常SUM sql
     *
     * @return
     */
    public String getWhereCondition() {
        return getWhereCondition((key, builder, condMap) -> {
            builder.append(key);
            builder.append(" = ");
            builder.append(condMap.get(key));
        });
    }

    public String getWhereCondition(Filter filter) {
        if (null == this.condition.getCondMap())
            return "";
        StringBuilder builder = new StringBuilder();
        for (String key : this.condition.getCondMap().keySet()) {
            if (StringUtils.isBlank(this.condition.getCondMap().get(key)))
                continue;
            if("sql".equals(key)){
                String sql = this.condition.getCondMap().get(key);
                if(StringUtils.isNotBlank(sql)){
                    String value = null;
                    try {
                        value = Base64.getEncoder().encodeToString(sql.getBytes(charsetName));
                        builder.append(value);
                    } catch (UnsupportedEncodingException e) {
                        LOGGER.error("getWhereCondition encodeToString is fail.... key:[{}],value:[{}]",key,value,e);
                    }
                }
                continue;
            }
            Dimension d1 = exist(this.condition.getHdimensions().get(0), key);
            Dimension d2 = exist(this.condition.getVdimensions().get(0), key);
            if (null != d1)
                d1.setValue(this.condition.getCondMap().get(key));
            if (null != d2)
                d2.setValue(this.condition.getCondMap().get(key));
            if (null != d1 || null != d2) {
                builder.append(key);
                builder.append(" like '");
                builder.append(this.condition.getCondMap().get(key));
                builder.append("%'");
            } else {
                if (null != filter) {
                    filter.doFilter(key, builder, this.condition.getCondMap());
                }
            }
        }
        return builder.toString();
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

    private void getNames(Dimension dimension, List<String> list) {
        if (null == dimension) {
            return;
        }
        list.add(dimension.getId());
        getNames(dimension.getNext(), list);
    }

    private void spellStr(String key, StringBuilder builder, Map<String, String> condMap) {
        builder.append(key);
        builder.append(" = ");
        builder.append(condMap.get(key));
    }
}
