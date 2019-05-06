package com.app.dictionary.action;

import com.app.dictionary.handler.manager.ExportDictionary;
import com.app.dictionary.handler.manager.AbstractRepertory;
import com.mybatis.pojo.Dbs;

import java.io.IOException;
import java.io.OutputStream;

public class WordExportDictionary extends ExportDictionary {
    @Override
    public void doExport(AbstractRepertory repertory, Dbs dbs, OutputStream outputStream) throws IOException {

    }
}
