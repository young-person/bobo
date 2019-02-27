package com.bobo.table;

import com.bobo.base.CatException;
import com.bobo.constant.Measure;
import com.bobo.table.db.CDbIndex;
import com.bobo.table.db.DBTable;

import java.util.ArrayList;
import java.util.List;

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
