package com.bobo.table.handler.impl;

import com.bobo.table.bean.Condition;
import com.bobo.table.bean.Dimension;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * 条件解析
 */
public class ParseCondition {

    enum Special{
        KSSJ("KSSJ"),
        JSSJ("JSSJ"),
        COND("@COND@");
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

    interface Filter{
        void doFilter(String key,StringBuilder builder,Map<String,String> condMap);
    }

    private Condition condition;

    public ParseCondition(Condition condition){
        this.condition = condition;
    }

    /**
     *获取开始日期统计
     */
    public String getWhereConditionFirst(){

        return this.getWhereCondition(new Filter() {
            @Override
            public void doFilter(String key,StringBuilder builder,Map<String,String> condMap) {
                if(!Special.JSSJ.getSerialize().equals(key)){
                    if (Special.KSSJ.getSerialize().equals(key)){
                        spellStr( key, builder,condMap);
                    }else{
                        spellStr( key, builder,condMap);
                    }
                }
            }
        });
    }
    /**
     *获取结束日期统计
     */
    public String getWhereConditionLast(){

        return this.getWhereCondition(new Filter() {
            @Override
            public void doFilter(String key,StringBuilder builder,Map<String,String> condMap) {
                if(!Special.KSSJ.getSerialize().equals(key)){
                    if (Special.JSSJ.getSerialize().equals(key)){
                        spellStr( key, builder,condMap);
                    }else{
                        spellStr( key, builder,condMap);
                    }
                }
            }
        });
    }
    /**
     *实时sql查询 根据不同sql需要进行sql语句调整
     */
    public String getWhereConditionReal(final String sql){
        final List<String> tmp = new ArrayList<String>(1);
        String conditionSql = this.getWhereCondition(new Filter() {
            @Override
            public void doFilter(String key,StringBuilder builder,Map<String,String> condMap) {
                StringBuilder b = new StringBuilder("@");
                b.append(key);
                b.append("@");
                StringBuilder cond = new StringBuilder();
                if (sql.indexOf(b.toString()) > -1){
                    String s = sql.replace(b.toString(),condMap.get(key));
                    tmp.set(0,s);
                }
            }
        });
        return tmp.get(0).replace(Special.COND.getSerialize(),conditionSql);
    }

    /**
     * 获取所有维度
     * @return
     */
    public String getDimensionValues(){
        StringBuilder builder = new StringBuilder();

        List<String> hd = new ArrayList<>();
        getNames(this.condition.getHdimension(),hd);
        getNames(this.condition.getVdimension(),hd);

        return String.join(",",hd);
    }
    /**
     * 获取正常SUM sql
     * @return
     */
    public String getWhereCondition(){
        return getWhereCondition(new Filter() {
            @Override
            public void doFilter(String key,StringBuilder builder,Map<String,String> condMap) {
                builder.append(key);
                builder.append(" = ");
                builder.append(condMap.get(key));
            }
        });
    }
    public String getWhereCondition(Filter filter){
        if (null == this.condition.getCondMap())
            return "";
        StringBuilder builder = new StringBuilder();
        for(String key : this.condition.getCondMap().keySet()){
            if (StringUtils.isBlank(this.condition.getCondMap().get(key)))
                continue;
            Dimension d1 = exist(this.condition.getHdimension(),key);
            Dimension d2 = exist(this.condition.getVdimension(),key);
            boolean flag = false;
            if (null != d1)
                d1.setValue(this.condition.getCondMap().get(key));
            if (null != d2)
                d2.setValue(this.condition.getCondMap().get(key));
            if (null != d1 || null != d2){
                //TODO 针对 特殊还需要处理
                builder.append(key);
                builder.append(" like '");
                builder.append(this.condition.getCondMap().get(key));
                builder.append("%'");
            }else{
                if (null != filter){
                    filter.doFilter(key,builder,this.condition.getCondMap());
                }
            }
        }
        if (StringUtils.isNotBlank(this.condition.getSql())){
            builder.append(" and ");
            builder.append(this.condition.getSql());
        }
        return builder.toString();
    }


    private Dimension exist(Dimension dimension,String key){
        if (null != dimension){
            String id = dimension.getId();
            if (id.equals(key)){
                checkDimensionValue(dimension,this.condition.getCondMap());
                return dimension;
            }else{
                while (null != dimension.getNext()){
                    if (dimension.getNext().getId().equals(key)){
                        checkDimensionValue(dimension,this.condition.getCondMap());
                        return dimension;
                    }
                }
            }
        }
        return null;
    }

    private void checkDimensionValue(Dimension dimension,Map<String,String> condMap){
        for(String key : condMap.keySet()){
            if (dimension.getId().equals(key))
                dimension.setValue(condMap.get(key));
        }
    }
    private void getNames(Dimension dimension,List<String> list){
        if (null == dimension){return;}
        list.add(dimension.getId());
        getNames(dimension.getNext(),list);
    }

    private void spellStr(String key,StringBuilder builder,Map<String,String> condMap){
        builder.append(key);
        builder.append(" = ");
        builder.append(condMap.get(key));
    }
}
