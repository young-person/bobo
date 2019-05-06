package com.bobo.enums;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum Message {


    OK("200","成功"),
    HAS_ONLINE("300", "您已经在其他地方登录，请重新登录！"),
    /**
     * 登录日志
     */
    LOGIN_LOG("20", "登录日志"),
    /**
     * 异常日志
     */
    EXCEPTION_LOG("30", "异常日志");

    public String getJson(){
        Map<String,String> result = new HashMap<>();
        try {
            result.put("code",this.getCode());
            result.put("value",this.getValue());
            return mapper.writeValueAsString(result);
        } catch (JsonProcessingException e) {
            result.clear();
            e.printStackTrace();
        }
        return result.toString();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static String getValue(String code){
        for(Message e:Message.values()){
            if (code.equals(e.getCode()))
                return e.getValue();
        }
        return "代码错误!";
    }


    public static Message getEnum(String code) {
        for (Message e : Message.values()) {
            if (code.equals(e.getCode())) {
                return e;
            }
        }
        return Message.OK;
    }


    public static List<Map<String, Object>> getList() {
        List<Map<String, Object>> list = Lists.newArrayList();
        for (Message ele : Message.values()) {
            Map<String, Object> map = Maps.newHashMap();
            map.put("code", ele.getCode());
            map.put("value", ele.getValue());
            list.add(map);
        }
        return list;
    }
    private static final transient  ObjectMapper mapper = new ObjectMapper();
    private String code;
    private String value;

    Message(String code, String value) {
        this.code = code;
        this.value = value;
    }
}
