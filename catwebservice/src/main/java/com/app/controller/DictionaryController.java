package com.app.controller;

import com.app.dictionary.action.ExcelExportDictionary;
import com.app.dictionary.demo.CherryxFilter;
import com.app.dictionary.handler.manager.AbstractRepertory;
import com.app.dictionary.handler.manager.OracleRepertory;
import com.mybatis.pojo.Dbs;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

@Controller
public class DictionaryController {

    @RequestMapping(value = "/export/template", method = RequestMethod.GET)
    public void centerTemplateDown(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.ms-excel;application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=ISO8859_1");
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("上诉案件模板.xls", "UTF-8") + "");
        ServletOutputStream out = response.getOutputStream();

        ExcelExportDictionary excelExportDictionary = new ExcelExportDictionary();
        AbstractRepertory repertory = new OracleRepertory();
        repertory.setFilter(new CherryxFilter());

        Dbs dbs = new Dbs("oracle.jdbc.OracleDriver", "jdbc:oracle:thin:@10.14.122.60:1521:portaldb", "CHERRYX", "CHERRYX");

        excelExportDictionary.doExport(repertory, dbs, out);

        out.flush();
        out.close();
    }
}
