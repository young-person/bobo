package com.bobo.table.handler.impl;

import com.bobo.table.bean.*;
import com.bobo.table.db.DBean;
import com.bobo.table.handler.DynamicData;

import java.util.*;

public class DynamicDataDefault implements DynamicData {
    @Override
    public void dynamicCreate(Dimension hdimension, Dimension vdimension,String dimensions, List<SimpleResult> results) {

        List<DBean> dBeans = hdimension.getResult();

        //固定X轴 动态最后的Y轴
        Dimension xdimension = hdimension.clone();

        List<Dimension> reverse = new ArrayList<>();
        //如果存在子 则链表反转再组装数据
        do{
            reverse.add(xdimension);
        }while(null != xdimension);



        for(int index = reverse.size(); index >=0; index --){
            Dimension d = reverse.get(index);
            List<DBean> dbeans = d.getResult();//根据维度数据 获取对应值

            TableResult tableResult = new TableResult();
            List<List<RNode>> datas = tableResult.getDatas();

            int j = 0;
            for(DBean dBean :dbeans){
                //根据对应 字段结果ID 生成 对应列数据
                String name = dBean.getName();
                String code = dBean.getCode();

                List<RNode> rows = datas.get(j);

                for(int k = 0; k < results.size(); k ++){


                    SimpleResult result = results.get(k);
                    List<Baseid> baseids = result.getBaseids();
                    List<Map<String, Object>> list = result.getResult();
                    for(Baseid b : baseids){
                        RNode n = new RNode();
                        n.setValue(d.getId());

                        for(Map<String, Object> map : list){
                            Object o = map.get(b.getId());//获取对应 baseid 数据点的值
                            Object v = map.get(d.getId());
                            if (code.equals(v)){
                                RNode node = new RNode();
                                node.setValue(o);
                                break;
                            }
                        }
                    }
                }

            }
        }

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
                Map<String,Object> map = new HashMap<String,Object>();
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
