package com.app.dictionary.handler.manager;

import java.util.List;
import java.util.Map;

import com.bobo.dbconnection.ConnectionHolderDefault;
import com.bobo.dbconnection.DBType;

/**
 * Sybase数据库字典
 *
 * @author bobo
 */
public class SybaseRepertory extends AbstractRepertory<DBType> {

    @Override
    public List<String> queryTablesByConnection(DBType dbs) {
        ConnectionHolderDefault holderDefault = new ConnectionHolderDefault();
        StringBuilder builder = new StringBuilder("SELECT name ");
        builder.append(TABLE_Name);
        builder.append(" from sysobjects WHERE type ='U'");
        List<Map<String, Object>> list = holderDefault.query(dbs, builder.toString());
        return filter.filterTables(list);
    }


    @Override
    public List<Map<String, Object>> queryColumnsByDB(DBType dbs) {
        ConnectionHolderDefault holderDefault = new ConnectionHolderDefault();

        StringBuilder builder = new StringBuilder("SELECT sysobjects.name ");
        builder.append(TABLE_Name);
        builder.append(",syscolumns.name");
        builder.append(COLUMN_Name);
        builder.append(",syscolumns.length ");
        builder.append(_LENGTH);
        builder.append(",systypes.name ");
        builder.append(_TYPE);
        builder.append(",allownulls ");
        builder.append(NULLABLE);
        builder.append(" from syscolumns left join sysobjects on syscolumns.id = sysobjects.id and (sysobjects.type = 'U' OR  sysobjects.type = 'V' )  left join systypes on syscolumns.usertype = systypes.usertype order  by sysobjects.name,syscolumns.name");
        List<Map<String, Object>> list = holderDefault.query(dbs, builder.toString());

        return filter.simpleExchange(list);
    }

}
