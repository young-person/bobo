package com.bobo.table.handler.impl;

import com.bobo.table.bean.Baseid;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public class ResultCache {

    private static volatile ResultCache instance = null;

    private ResultCache(){}

    public static ResultCache getInstance(){
        if (null == instance){
            synchronized (ResultCache.class){
                if(null == instance){
                    instance = new ResultCache();
                }
            }
        }
        return instance;
    }

    /**
     * 存在多线程问题
     */
    private List<Map<String, List<Baseid>>> groupBaseids = new CopyOnWriteArrayList<Map<String,List<Baseid>>>();

    public void put(int index, String key, Baseid baseid){
        Map<String,List<Baseid>> group = groupBaseids.get(index);

        if ( null == group.get(key)){
            group.put(key, Arrays.asList(baseid));
        }else{
            List<Baseid> arr = group.get(key);
            if (!arr.contains(baseid))
                arr.add(baseid);
        }

    }


    public List<Map<String, List<Baseid>>> getGroupBaseids() {
        return groupBaseids;
    }

}
