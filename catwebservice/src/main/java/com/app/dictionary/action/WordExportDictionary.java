package com.app.dictionary.action;

import com.app.dictionary.handler.manager.AbstractRepertory;
import com.app.dictionary.handler.manager.ExportDictionary;
import com.app.dictionary.templet.model.StoreRoom;
import com.app.utils.FreeMakerUtils;
import com.mybatis.pojo.Dbs;

import javax.servlet.ServletOutputStream;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class WordExportDictionary extends ExportDictionary {
    @Override
    public void doExport(AbstractRepertory repertory, Dbs dbs, OutputStream outputStream) throws IOException {
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
