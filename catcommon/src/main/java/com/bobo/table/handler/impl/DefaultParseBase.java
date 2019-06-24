package com.bobo.table.handler.impl;

import com.bobo.base.CatException;
import com.bobo.constant.Measure;
import com.bobo.enums.CEnum;
import com.bobo.exception.CException;
import com.bobo.table.bean.Baseid;
import com.bobo.table.bean.Condition;
import com.bobo.table.bean.Dimension;
import com.bobo.table.bean.SimpleResult;
import com.bobo.table.db.CDbIndex;
import com.bobo.table.db.DBTable;
import com.bobo.table.db.DBean;
import com.bobo.table.handler.*;

import java.util.*;

public class DefaultParseBase implements ParseBase<Condition> {

    private CDbIndex dbIndex;

    private Actuator actuator;

    public DefaultParseBase(CDbIndex dbIndex){
        this.dbIndex = dbIndex;
    }


    @Override
    public void parse(Condition condition) {

        parseCorn(condition);

        List<DBTable> dbTables = dbIndex.loadTablesFromDbs();

        Dimension hdimension = condition.getHdimension();

        Dimension vdimension = condition.getVdimension();

        getDimension(hdimension);
        getDimension(vdimension);

        hdimension.getResult();
        List<SimpleResult> results = query(condition);

        String dimensions = getDimensions(condition);

        DynamicData dynamicData = new DynamicDataDefault();
        dynamicData.dynamicCreate(hdimension,vdimension,dimensions,results);
    }

    /**
     * 获取当前维度值
     * @param dimension
     */
    private  void getDimension(Dimension dimension){
        if (Objects.nonNull(dimension)){
            StringBuilder builder = new StringBuilder();
            builder.append("select ");
            builder.append(dimension.getId());

            List<DBean> result = BasicFactory.getResult(actuator,builder.toString());

            dimension.setResult(result);//获取结果
            getDimension(dimension);
        }else{
            return;
        }
    }

    private Condition getTimeCondition(Condition condition){
        return getTimeCondition( condition, true);
    }

    private Condition getTimeCondition(Condition condition,boolean is){
        Condition c = new Condition();
        c.setHdimension(condition.getHdimension());
        c.setVdimension(condition.getVdimension());
        c.setSql(condition.getSql());
        c.setCorn(condition.getCorn());
        List<Baseid> result = new ArrayList<>();
        Map<String,String> condMap = condition.getCondMap();
        for(Baseid baseid : condition.getBaseids()){
            if (is){
                if (baseid.getShowtq()){
                    result.add(baseid);
                }
            }else{
                if (baseid.getShowsq()){
                    result.add(baseid);
                }
            }
        }
        c.setBaseids(result);
        c.setCondMap(condMap);
        return c;
    }

    public interface CondFilter{
        public void filter(Map<String,String> condMap);
    }

    /**
     * 解析表达式 为baseid
     * @param condition
     */
    private void parseCorn(Condition condition){
        if (null == condition.getCorn())
            throw new CatException("表达式长度不能为空");
        List<Baseid> allBaseid = new ArrayList<>();
        for (String s : condition.getCorn()){
            int start = 0,end = 0;
            List<Baseid> baseids = new ArrayList<>();
            for(int i = 0; i < s.length(); i++){
                char str = s.charAt(i);
                boolean flag = true;
                for (int index = 0; index < Measure.Accord.length; index ++){
                    if (Measure.Accord[index].equals(str)){
                        flag = false;
                        end = i;
                        break;
                    }
                }
                if (!flag){
                    if (start != end){
                        Baseid baseid = new Baseid(s.substring(start,end));
                        baseids.add(baseid);
                        if (!allBaseid.contains(baseid)){
                            allBaseid.add(baseid);
                        }
                    }
                    start = end;
                }
            }
            condition.getRelativeMap().put(s,baseids);
        }
        condition.setBaseids(allBaseid);
    }


    protected String getDimensions(Condition condition){
        ParseCondition parseCondition = new ParseCondition(condition);
        return parseCondition.getDimensionValues();
    }

    /**
     * 同时间段 数据查询
     * @param condition
     */
    public List<SimpleResult> query(Condition condition) {
        for (CEnum cEnum : CEnum.values()) {
            for(int len = 0; len < condition.getBaseids().size(); len++){
                Baseid baseid = condition.getBaseids().get(len);
                String table_col = baseid.getTable_col();
                if (cEnum.getSerialize().equals(baseid.getType())){
                    if (table_col.indexOf(".") > -1){
                        String tname = table_col.split(".")[0];//表名字
                        String cname = table_col.split(".")[1];//段名
                        ResultCache.getInstance().put(cEnum.ordinal(),tname,baseid);
                    }else{
                        //属于实时查
                        ResultCache.getInstance().put(cEnum.ordinal(),baseid.getId(),baseid);
                        throw new CatException("数据不符合规范");
                    }
                }
            }
        }
        ParseCondition parseCondition = new ParseCondition(condition);
        String dimensions = parseCondition.getDimensionValues();

        String where = null;
        List<SimpleResult> all = new ArrayList<SimpleResult>();

        for (CEnum cEnum : CEnum.values()) {
            Map<String, List<Baseid>> group = ResultCache.getInstance().getGroupBaseids().get(cEnum.ordinal());
            if (null != group && group.size() > 0){
                QueryParse<CEnum> queryParse = null;

                if (CEnum.REAL.getSerialize().equals(cEnum.getSerialize())){
                    queryParse = new ParseRealResult();
                    for(String key : group.keySet()){
                        List<Baseid> realBaseids = group.get(key);
                        for(Baseid b: realBaseids){
                            where = parseCondition.getWhereConditionReal(b.getSql());
                            SimpleResult result = queryParse.parse(cEnum,where,dimensions,Arrays.asList(b),null);
                            all.add(result);
                        }
                    }
                }else{
                    if (CEnum.SUM.getSerialize().equals(cEnum.getSerialize())){
                        queryParse = new ParseSumResult();
                        where = parseCondition.getWhereCondition();
                    }else if(CEnum.FIRST.getSerialize().equals(cEnum.getSerialize())){
                        queryParse = new ParseSumResult();
                        where = parseCondition.getWhereConditionFirst();
                    }else if(CEnum.LAST.getSerialize().equals(cEnum.getSerialize())){
                        queryParse = new ParseSumResult();
                        where = parseCondition.getWhereConditionLast();
                    }else {
                    	throw new CException("不在定义操作里面");
                    }
                    for(String key : group.keySet()){
                        List<Baseid> baseids = group.get(key);
                        SimpleResult result = queryParse.parse(cEnum,where,dimensions,baseids,key);
                        all.add(result);
                    }
                }
            }
        }
        return all;
    }

}
