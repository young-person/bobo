package com.app.dictionary.handler.manager;

import com.app.dictionary.handler.*;
import com.app.dictionary.templet.model.Form;
import com.app.dictionary.templet.model.StoreRoom;
import com.app.dictionary.templet.model.Table;
import com.bobo.base.BaseClass;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.*;

public abstract class AbstractRepertory<E> extends BaseClass implements QueryRepertory<E> {



    protected SimpleFilter filter =  new DefaultSimpleFilter();

    protected AutomationDict automationDict = new DefaultAutomationDict();

    public void setFilter(SimpleFilter filter) {
        this.filter = filter;
    }

    protected boolean isFieldNotNull(E dbs,String table,String column){
        return false;
    }

    public AutomationDict getAutomationDict() {
        return automationDict;
    }

    public void setAutomationDict(AutomationDict automationDict) {
        this.automationDict = automationDict;
    }

    protected List<Map<String, Object>> queryAllPkByDB(E dbs){
        return new ArrayList<>();
    }

    /**
     * 创建数据表单
     * @param name
     * @return
     */
    public StoreRoom createDBInfOBean(E dbs, String name) {
        List<String> tables = queryTablesByConnection(dbs);
        List<Map<String,Object>> columns = queryColumnsByDB(dbs);


        StoreRoom storeRoom = new StoreRoom(name);
        List<Table> list = new ArrayList<>();
        int index = 0;
        for(String table :tables){
            Table t = new Table(table);
            List<Form> forms = createForms(dbs, table,columns);
            t.setForms(forms);
            list.add(t);
            index ++;
        }

        Collections.sort(list, new Comparator<Table>() {
            public int compare(Table o1, Table o2) {
                return o1.getTableName().compareTo(o2.getTableName());
            };
        });
        storeRoom.setTables(list);
        automationDict.startAutomation(storeRoom);
        return storeRoom;
    }

    protected List<Form> createForms(E dbs, String table, List<Map<String, Object>> list) {
        List<Form> forms = new ArrayList<>();
        for (Map<String, Object> m : list) {
            String tableName = (String) m.get(QueryRepertory.TABLE_Name);// 表名
            if (table.equals(tableName)){
                String colname = (String) m.get(QueryRepertory.COLUMN_Name);// 字段名
                String colType = (String) m.get(QueryRepertory._TYPE);
                BigDecimal len = (BigDecimal) m.get(QueryRepertory._LENGTH);
                String comments = (String) m.get(QueryRepertory.TABLE_Comments);
                String columnRemark = m.get(QueryRepertory._PK)==null ? "" : (String)m.get(QueryRepertory._PK);
                boolean sure = isFieldNotNull(dbs,table,colname);

                String columnDetail = automationDict.getKeyName(tableName,colname);
                if (StringUtils.isNotBlank(columnDetail)){
                    comments = columnDetail;
                }else if(StringUtils.isBlank(columnDetail) && StringUtils.isNotBlank(comments)){
                    logger.error("配置文件里面{}表的{}字段的对应不存在但是数据库有对应翻译",new Object[]{tableName,COLUMN_Name});
                }else{
                    logger.error("{}表的{}字段的翻译不存在需要人工补录",new Object[]{tableName,COLUMN_Name});
                }

                Form form = new Form(colname, colType, comments,columnRemark, "");
                form.setLength(String.valueOf(len.intValue()));
                if (sure){
                    form.setNon("Y");
                }
                forms.add(form);
            }
        }
        return forms;
    }
}
