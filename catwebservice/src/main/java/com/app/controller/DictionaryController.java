package com.app.controller;

import com.app.dictionary.action.ExcelExportDictionary;
import com.app.dictionary.demo.CherryxFilter;
import com.app.dictionary.handler.manager.AbstractRepertory;
import com.app.dictionary.handler.manager.MysqlRepertory;
import com.app.dictionary.handler.manager.OracleRepertory;
import com.app.dictionary.handler.manager.SybaseRepertory;
import com.app.dictionary.templet.model.StoreRoom;
import com.app.utils.FreeMakerUtils;
import com.bobo.base.BaseClass;
import com.mybatis.pojo.Dbs;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "dictionary")
public class DictionaryController extends BaseClass {

    @RequestMapping(value = "/export/excel", method = RequestMethod.GET)
    public void centerTemplateDown(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/vnd.ms-excel;application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=ISO8859_1");
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("数据字典.xls", "UTF-8") + "");
        ServletOutputStream out = response.getOutputStream();

        ExcelExportDictionary excelExportDictionary = new ExcelExportDictionary();
        AbstractRepertory<Dbs> repertory = new OracleRepertory();
        repertory.setFilter(new CherryxFilter());

        Dbs dbs = new Dbs("oracle.jdbc.OracleDriver", "jdbc:oracle:thin:@10.14.122.60:1521:portaldb", "CHERRYX", "CHERRYX");

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
            logger.info(val);
        }
        Dbs dbs = new Dbs("oracle.jdbc.OracleDriver", "jdbc:oracle:thin:@10.14.122.60:1521:portaldb", "CHERRYX", "CHERRYX");

        AbstractRepertory repertory = null;
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



        StoreRoom storeRoom = repertory.createDBInfOBean(dbs, "基础");

        // 提示：在调用工具类生成Word文档之前应当检查所有字段是否完整
        // 否则Freemarker的模板殷勤在处理时可能会因为找不到值而报错 这里暂时忽略这个步骤了
        File file = null;
        InputStream fin = null;
        ServletOutputStream out = null;
        try {
            // 调用工具类WordGenerator的createDoc方法生成Word文档
            Map<String, Object> dataMap = new HashMap<String, Object>();
            dataMap.put("storeRoom", storeRoom);
            file = FreeMakerUtils.createDoc(dataMap, "datadictionary");
            fin = new FileInputStream(file);

            response.setCharacterEncoding("utf-8");
            response.setContentType("application/msword");
            // 设置浏览器以下载的方式处理该文件默认名为resume.doc
            response.addHeader("Content-Disposition", "attachment;filename="+URLEncoder.encode(dbs.getDbname()+"数据字段.doc","UTF-8")+"");

            out = response.getOutputStream();
            byte[] buffer = new byte[1024];  // 缓冲区
            int bytesToRead = -1;
            // 通过循环将读入的Word文件的内容输出到浏览器中
            while((bytesToRead = fin.read(buffer)) != -1) {
                out.write(buffer, 0, bytesToRead);
            }
        } finally {
            if(fin != null) fin.close();
            if(out != null) out.close();
            if(file != null) file.delete(); // 删除临时文件
        }
    }
    
}
