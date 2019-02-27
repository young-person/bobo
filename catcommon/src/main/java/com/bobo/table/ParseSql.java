package com.bobo.table;

import com.bobo.base.CatException;
import com.bobo.enums.CEnum;
import com.bobo.table.db.DBTable;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParseSql {
    private Condition condition;

    private List<String> sqls;

    public ParseSql(Condition condition){
        this.condition = condition;
    }
    private static Map<String,String> exp = new HashMap<String,String>(){{
        exp.put("kssj","=<>");
        exp.put("jssj","<>");
    }};


    public String getWhereCondition(){
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
                builder.append(key);
                builder.append(" = ");
                builder.append(this.condition.getCondMap().get(key));
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

    private void parse(){

    }
    private void parse(List<DBTable> dbTables){
        ParseSql.Pip pip = new ParseSql.Pip();
        for(Baseid baseid:this.condition.getBaseids()){
            String table_col = baseid.getTable_col();
            switch (baseid.getType()) {
                case "real" :
                    pip.put(baseid.getId(),baseid);
                    break;
                case "sum":
                    if (table_col.indexOf(".") > -1){
                        String tname = table_col.split(".")[0];
                        String cname = table_col.split(".")[1];
                        pip.put(tname,baseid);
                    }else{
                        throw new CatException("数据不符合规范");
                    }
                    break;
                default:
                    break;
            }
        }
        for(String key : pip.getGroupMap().keySet()){
            int flag = 0;
            for(Baseid baseid : pip.getBaseids()){

                if (baseid.getId().equals(key) && CEnum.REAL.getSerialize().equals(baseid.getType()))
                    flag = 1;
                if (baseid.getId().equals(key) && CEnum.SUM.getSerialize().equals(baseid.getType()))
                    flag = 2;
                break;
            }
            List<Baseid> baseids = pip.getGroupMap().get(key);

            if (flag == 0 || flag == 2){
                int index = 0;
                String[] colunms = new String[baseids.size()];
                for(Baseid baseid:baseids){
                    String table_col = baseid.getTable_col();
                    colunms[index] = table_col.split(",")[1];
                }
            }else if(flag == 1){

            }

        }
    }

    class Pip{
        private List<Baseid> baseids = new ArrayList<>();
        private int size = 0;
        private Map<String,List<Baseid>> groupMap = new HashMap<>();
        public void put(String key,Baseid baseid){
            for(Baseid i : baseids){
                if (i.equals(baseid))
                    return;
            }
            baseids.add(baseid);
            size ++;
            groupMap.put(key,baseids);
        }
        public int getSize() {
            return size;
        }
        public Map<String, List<Baseid>> getGroupMap() {
            return groupMap;
        }
        public List<Baseid> getBaseids() {
            return baseids;
        }
    }

}
