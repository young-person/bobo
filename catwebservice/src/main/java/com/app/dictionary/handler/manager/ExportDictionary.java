package com.app.dictionary.handler.manager;

import com.mybatis.pojo.Dbs;

import java.io.IOException;
import java.io.OutputStream;

public abstract class ExportDictionary {

    public abstract void doExport(AbstractRepertory repertory, Dbs dbs, OutputStream outputStream) throws IOException;

    public String getClassName(String tableName) {

        String[] clazzName = tableName.split("_");
        StringBuilder builder = new StringBuilder();
        for (int index = 0; index < clazzName.length; index++) {
            if (0 == index)
                continue;
            String name = clazzName[index];
            name = name.toLowerCase();
            builder.append(String.valueOf(name.charAt(0)).toUpperCase());
            builder.append(name.substring(1));
        }

        return builder.toString();
    }

}
