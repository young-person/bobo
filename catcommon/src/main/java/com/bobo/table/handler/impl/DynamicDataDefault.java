package com.bobo.table.handler.impl;

import com.bobo.table.bean.Baseid;
import com.bobo.table.bean.Dimension;
import com.bobo.table.bean.SimpleResult;
import com.bobo.table.db.DBean;
import com.bobo.table.handler.DynamicData;

import java.util.*;

public class DynamicDataDefault implements DynamicData {
    @Override
    public void dynamicCreate(Dimension hdimension, Dimension vdimension,String dimensions, List<SimpleResult> results) {

        List<DBean> dBeans = hdimension.getResult();

        //固定X轴 动态最后的Y轴
        Dimension xdimension = hdimension.clone();

        do{
            List<DBean>  dbeans = xdimension.getResult();
            for(DBean dBean :dbeans){
                dBean.getCode();
                dBean.getName();

                for(SimpleResult result : results){
                    List<Map<String, Object>> list = result.getResult();
                }

            }
            xdimension = xdimension.getNext();
        }while(null != xdimension);
    }

    //先将数据 全部合并 同类项
    protected SimpleResult getSingleSimpleResult(List<SimpleResult> results,String dimensions){
        String[] s = dimensions.split(",");
        Set<Baseid> set = new HashSet<Baseid>();
        for(SimpleResult result : results){
            List<Baseid> baseids = result.getBaseids();
            for(Baseid b : baseids){
                set.add(b);
            }
        }
        SimpleResult simpleResult = new SimpleResult();
        List<Map<String, Object>> datas =  new ArrayList<Map<String, Object>>();

        for(SimpleResult result : results){
            result.getBaseids();
            List<Map<String, Object>> list = result.getResult();
            for(Map<String, Object> m : list){
                Map<String,Object> map = new HashMap<>();
                for(String key : m.keySet()){
                    boolean flag = isIn(s,key);
                    if(flag){//维度值

                    }else{
                        isIn(set,key);//数据项
                    }
                    map.put(key,m.get(key));

                    for(Map<String, Object> dMap : datas){


                    }

                }
            }
        }

        return simpleResult;
    }

    private boolean isIn(Set<Baseid> set,String key){
        Iterator<Baseid> iterator = set.iterator();
        while(iterator.hasNext()){
            Baseid b = iterator.next();
            if (b.getId().equals(key))
                return true;
        }
        return false;
    }
    private boolean isIn(String[] s,String key){
        for(int index = 0; index < s.length; index ++){
            if (s.equals(key))
                return s.equals(key);
        }
        return false;
    }
}
