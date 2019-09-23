package com.bobo.table.handler.impl;

import com.bobo.base.BaseClass;
import com.bobo.table.bean.Baseid;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DataCacheUtil extends BaseClass {

    private static volatile DataCacheUtil instance = null;

    private DataCacheUtil(){}

    public static DataCacheUtil getInstance(){
        if (null == instance){
            synchronized (DataCacheUtil.class){
                if(null == instance){
                    instance = new DataCacheUtil();
                }
            }
        }
        return instance;
    }

    /**
     * baseid 数据点
     */
    private Map<String,Baseid> baseidMap = new ConcurrentHashMap<>();

    public void start(){
        LOGGER.info("------------start------------");

        LOGGER.info("------------end------------");
    }

    /**
     * 存在多线程问题
     */
    private Map<String,Map<String, List<Baseid>>> groupBaseids = new ConcurrentHashMap<>();

    public void put(String index, String key, Baseid baseid){
        Map<String,List<Baseid>> group = groupBaseids.get(index);

        if ( null == group.get(key)){
            group.put(key, Collections.singletonList(baseid));
        }else{
            List<Baseid> arr = group.get(key);
            if (!arr.contains(baseid))
                arr.add(baseid);
        }
    }


    public Map<String, Baseid> getBaseidMap() {
        return baseidMap;
    }
    public Map<String, Map<String, List<Baseid>>> getGroupBaseids() {
        return groupBaseids;
    }
}
