package com.app.controller;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Enumeration;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.app.dictionary.action.ExcelExportDictionary;
import com.app.dictionary.action.WordExportDictionary;
import com.app.dictionary.demo.CherryxFilter;
import com.app.dictionary.handler.manager.AbstractRepertory;
import com.app.dictionary.handler.manager.ExportDictionary;
import com.app.dictionary.handler.manager.MysqlRepertory;
import com.app.dictionary.handler.manager.OracleRepertory;
import com.app.dictionary.handler.manager.SybaseRepertory;
import com.bobo.base.BaseClass;
import com.bobo.dbconnection.DBType;

@Controller
@RequestMapping(value = "dictionary")
public class DictionaryController extends BaseClass {

    @RequestMapping(value = "/export/excel", method = RequestMethod.GET)
    public void centerTemplateDown(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.ms-excel;application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=ISO8859_1");
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("数据字典.xls", "UTF-8") + "");
        ServletOutputStream out = response.getOutputStream();

        ExportDictionary excelExportDictionary = new ExcelExportDictionary();
        AbstractRepertory<DBType> repertory = new OracleRepertory();
        repertory.setFilter(new CherryxFilter());
        DBType dbs = null;
        excelExportDictionary.doExport(repertory, dbs, out);

        out.flush();
        out.close();
    }

    @RequestMapping(value = "/export/word", method = RequestMethod.GET)
    @ResponseBody
    public void exportUserForDoc(HttpServletRequest request,HttpServletResponse response) throws IOException{
        @SuppressWarnings("rawtypes")
        Enumeration e =request.getParameterNames();
        String val = null;
        while(e.hasMoreElements()){
            String key = (String)e.nextElement();
            val = request.getParameter(key);
            LOGGER.info(val);
        }
        DBType dbs = null;
        AbstractRepertory<DBType> repertory = null;
        switch (dbs.getType()) {
            case "mysql":
                repertory = new MysqlRepertory();
                break;
            case "sybase":
                repertory = new SybaseRepertory();
                break;
            case "oracle":
                repertory = new OracleRepertory();
                break;
            default:
                break;
        }
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/msword");
        // 设置浏览器以下载的方式处理该文件默认名为resume.doc
        response.addHeader("Content-Disposition", "attachment;filename="+URLEncoder.encode(dbs.getDBName()+"数据字段.doc","UTF-8")+"");

        ExportDictionary wordExportDictionary = new WordExportDictionary();
        wordExportDictionary.doExport(repertory,dbs,response.getOutputStream());
    }
    
}
