package com.bobo.table.handler.data;

import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlInsertStatement;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlUpdateStatement;
import com.alibaba.druid.sql.dialect.mysql.parser.MySqlStatementParser;
import com.bobo.base.BaseClass;
import com.bobo.enums.CEnum;
import com.bobo.table.bean.Baseid;
import com.bobo.table.bean.Condition;
import com.bobo.table.bean.SimpleResult;
import com.bobo.table.handler.DataExecute;
import com.bobo.table.handler.QueryParse;
import com.bobo.table.handler.impl.ParseCondition;
import com.bobo.table.handler.impl.ParseRealResult;
import com.bobo.table.handler.impl.ParseSumResult;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SqlDataExecute extends BaseClass implements DataExecute<Condition> {
    /**
     * 执行处理多类别数据维度
     *
     * @param condition
     */
    @Override
    public void handler(Condition condition) {
        LOGGER.info("1");
    }

    private static final String REGEX = "/sum\\((\\w+)\\)/g";

    /**
     * 同时间段 数据查询
     *
     * @param condition
     */
    protected List<SimpleResult> query(Condition condition) {
        Map<String, Map<String, List<Baseid>>> groupBaseids = new HashMap<>();

        for (int len = 0; len < condition.getBaseids().size(); len++) {
            Baseid baseid = condition.getBaseids().get(len);
            String table_col = baseid.getTable_col();
            char point = '.';
            if (StringUtils.isBlank(table_col) || (table_col.indexOf(point) == -1 && StringUtils.isNotBlank(table_col))) {
                this.put(CEnum.REAL.name(), baseid.getId(), baseid, groupBaseids);
            } else {
                String sql = baseid.getSql();

                if (sql.contains(ParseCondition.Special.KSSJ.name()) && !sql.contains(ParseCondition.Special.JSSJ.name())) {
                    this.put(CEnum.FIRST.name(), baseid.getId(), baseid, groupBaseids);
                } else if (!sql.contains(ParseCondition.Special.KSSJ.name()) && sql.contains(ParseCondition.Special.JSSJ.name())) {
                    this.put(CEnum.LAST.name(), baseid.getId(), baseid, groupBaseids);
                } else {
                    Pattern c = Pattern.compile(REGEX);
                    Matcher mc = c.matcher(sql);
                    boolean flag = false;
                    while (mc.find()) {
                        flag = true;
                    }
                    if (flag) {
                        this.put(CEnum.SUM.name(), baseid.getId(), baseid, groupBaseids);
                    } else {
                        this.put(CEnum.REAL.name(), baseid.getId(), baseid, groupBaseids);
                    }
                }
            }
        }

        ParseCondition parseCondition = new ParseCondition(condition);
        List<String> hd = parseCondition.getDimensionValues();

        String sql = null;
        List<SimpleResult> all = new ArrayList<>();

        Set<Map.Entry<String, Map<String, List<Baseid>>>> entries = groupBaseids.entrySet();
        for (Map.Entry<String, Map<String, List<Baseid>>> entry : entries) {
            String key = entry.getKey();
            Map<String, List<Baseid>> set = entry.getValue();
            List<Baseid> list = set.get(key);
            QueryParse queryParse = null;
            //TODO 暂时不做处理
            if(list.size() > 1)
                continue;
//            parseCondition.getCommonSql(set, queryParse);
            if (CEnum.REAL.name().equals(key)){
                for (Baseid b : list) {
                    queryParse = new ParseRealResult();
                    sql = parseCondition.getWhereConditionRealSql(b);
                }
            }else{
                queryParse = new ParseSumResult();
                sql = parseCondition.getWhereConditionSql();
            }
            SimpleResult result = queryParse.parse(key, sql, hd, list);
            all.add(result);

        }
        return all;
    }
    /**
     * 针对有同一条sql 不同时间段进行统计的语句  同一个baseid 会对应多条Baseid
     * @param index
     * @param key
     * @param baseid
     * @param groupBaseids
     */
    private void put(String index, String key, Baseid baseid, Map<String, Map<String, List<Baseid>>> groupBaseids) {
        Map<String, List<Baseid>> group = groupBaseids.get(index);

        if (null == group.get(key)) {
            group.put(key, Collections.singletonList(baseid));
        } else {
            List<Baseid> arr = group.get(key);
            if (!arr.contains(baseid))
                arr.add(baseid);
        }
    }

    private void updateSql(String sql){
//        SQLSelectStatement
//        MySqlSelectIntoStatement
        //MySqlDeleteStatement;
//        MySqlSelectIntoParser

        MySqlStatementParser parser = new MySqlStatementParser(sql);
        SQLStatement stmt = parser.parseStatement();
        MySqlUpdateStatement update = (MySqlUpdateStatement)stmt;
    }

    private void insertSQL(String sql){
            MySqlStatementParser parser = new MySqlStatementParser(sql);
            SQLStatement statement = parser.parseStatement();
            MySqlInsertStatement insert = (MySqlInsertStatement)statement;

    }

}
