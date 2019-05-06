package com.app.dictionary.handler;

import com.alibaba.fastjson.JSONObject;
import com.app.dictionary.templet.model.StoreRoom;

public interface AutomationDict {

    public void startAutomation(StoreRoom storeRoom);

    public JSONObject getParam();

    public String getKeyName(String tableName,String columnName);
}
