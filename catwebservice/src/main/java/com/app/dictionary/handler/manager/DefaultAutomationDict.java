package com.app.dictionary.handler.manager;

import com.alibaba.fastjson.JSONObject;
import com.app.dictionary.handler.AutomationDict;
import com.app.dictionary.templet.model.StoreRoom;
import com.bobo.base.BaseClass;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.Objects;

public class DefaultAutomationDict extends BaseClass implements AutomationDict {

    static {


    }

    private static String defaultPath = DefaultAutomationDict.class.getClass().getResource("/").getPath()+"dictionary/json/";

    private String path = "";

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public JSONObject getParam() {

        InputStream inputStream = null;
        StringBuilder sb = new StringBuilder();
        try {

            File file = new File(defaultPath);
            if (!file.exists()){
                file.mkdirs();
            }else{
                if (file.isDirectory()){

                }
            }

            inputStream = new FileInputStream(new File(defaultPath+"test.json"));
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        JSONObject object = JSONObject.parseObject(sb.toString());
        return object;
    }

    @Override
    public String getKeyName(String tableName, String columnName) {
        JSONObject object = getParam();
        JSONObject tableObject = object.getJSONObject(tableName);
        String columnDetail = null;
        if (!Objects.isNull(tableObject)){
            columnDetail = tableObject.getString (columnName);
        }else{
        	LOGGER.error("没有对应数据解释表名:{}",tableName);
        }
        if (StringUtils.isBlank(columnDetail)){
        	LOGGER.error("存在对表:{}解释但是不存在对该字段:{}的解释",new Object[]{tableName,columnName});
        }
        return null;
    }


    @Override
    public void startAutomation(StoreRoom storeRoom) {

    }
}
