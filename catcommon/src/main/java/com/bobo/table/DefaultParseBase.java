package com.bobo.table;

import com.bobo.base.CatException;
import com.bobo.constant.Measure;
import com.bobo.table.db.CDbIndex;
import com.bobo.table.db.DBTable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DefaultParseBase implements ParseBase<Condition> {

    private CDbIndex dbIndex;

    public DefaultParseBase(CDbIndex dbIndex){
        this.dbIndex = dbIndex;
    }


    @Override
    public void parse(Condition condition) {

        parseCorn(condition);

        List<DBTable> dbTables = dbIndex.loadTablesFromDbs();

        ParseSql parseSql = new ParseSql(condition);

        String where = parseSql.getWhereCondition();

        List<String> hSql = null,vSql = null;
        Dimension hdimension = condition.getHdimension();

        Dimension vdimension = condition.getVdimension();

        getDimension(hdimension,hSql);
        getDimension(vdimension,vSql);

    }

    private  void getDimension(Dimension dimension,List<String> sqls){
        if (Objects.nonNull(dimension.getNext()) && null != sqls){
            sqls = new ArrayList<String>();
            StringBuilder builder = new StringBuilder();
            builder.append("select ");
            builder.append(dimension.getId());
        }

        if (Objects.nonNull(dimension.getNext()))
            getDimension(dimension.getNext(),sqls);
    }



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


}
