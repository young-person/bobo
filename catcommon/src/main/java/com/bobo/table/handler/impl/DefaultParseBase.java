package com.bobo.table.handler.impl;

import com.bobo.table.bean.Baseid;
import com.bobo.table.bean.Condition;
import com.bobo.table.handler.DataExecute;
import com.bobo.table.handler.ParseBase;
import com.bobo.table.handler.data.SingleDataExecute;
import com.bobo.table.handler.data.SqlDataExecute;
import com.bobo.table.handler.data.VariousDataExecute;

import java.util.Objects;

public class DefaultParseBase extends ParseBaseFactory implements ParseBase<Condition> {

    public static void main(String[] args) {
        DefaultParseBase defaultParseBase = new DefaultParseBase();
        defaultParseBase.parse(null);
    }

    @Override
    public void parse(Condition condition) {

        this.parseCorn(condition);

        this.parseDimension(condition.getHdimensions());
        this.parseDimension(condition.getVdimensions());
        DataExecute dataExecute = null;
        if (Objects.isNull(condition.getHdimensions()) && Objects.isNull(condition.getVdimensions()) || this.sure) {
            dataExecute = new SqlDataExecute();
        } else if (condition.getHdimensions().size() > 1 || condition.getVdimensions().size() > 1) {
            dataExecute = new VariousDataExecute();
        } else {
            dataExecute = new SingleDataExecute();
        }
        dataExecute.handler(condition);
    }


    @Override
    public BaseidService getBaseidService() {
        return baseid -> {
            Baseid b = DataCacheUtil.getInstance().getBaseidMap().get(baseid.getId());
            baseid.setTable_col(b.getTable_col());
            baseid.setExplain(b.getExplain());
        };
    }


//    private Condition getTimeCondition(Condition condition){
//        return getTimeCondition( condition, true);
//    }
//
//    private Condition getTimeCondition(Condition condition,boolean is){
//        Condition c = new Condition();
//        c.setHdimensions(condition.getHdimensions());
//        c.setVdimensions(condition.getVdimensions());
//        c.setSql(condition.getSql());
//        c.setCorn(condition.getCorn());
//        List<Baseid> result = new ArrayList<>();
//        Map<String,String> condMap = condition.getCondMap();
//        for(Baseid baseid : condition.getBaseids()){
//            if (is){
//                if (baseid.getShowtq()){
//                    result.add(baseid);
//                }
//            }else{
//                if (baseid.getShowsq()){
//                    result.add(baseid);
//                }
//            }
//        }
//        c.setBaseids(result);
//        c.setCondMap(condMap);
//        return c;
//    }
}
