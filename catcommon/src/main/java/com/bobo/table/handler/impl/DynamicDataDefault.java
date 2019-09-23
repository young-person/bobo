package com.bobo.table.handler.impl;

import com.bobo.table.bean.*;
import com.bobo.table.db.DBean;
import com.bobo.table.handler.DynamicData;
import com.bobo.utils.Calculator;

import java.util.*;

public class DynamicDataDefault implements DynamicData {
    @Override
    public void dynamicCreate(Dimension hdimension, Dimension vdimension, List<SimpleResult> results,Condition condition) {

        List<DBean> dBeans = hdimension.getResult();

        //固定X轴 动态最后的Y轴
        Dimension xdimension = hdimension.clone();

        List<Dimension> reverse = new ArrayList<>();
        //如果存在子 则链表反转再组装数据
        do{
            reverse.add(xdimension);
        }while(null != xdimension);


        TableResult tableResult = new TableResult();
        for(int index = reverse.size(); index >=0; index --){
            if (index < (reverse.size() - 1)){
                break;
            }
            Dimension d = reverse.get(index);
            List<DBean> dbeans = d.getResult();//根据维度数据 获取对应值

            tableResult.setDatas(new TNode[dbeans.size()][]);
            int j = 0;
            for(DBean dBean :dbeans){// 维度轴
                //根据对应 字段结果ID 生成 对应列数据
                String name = dBean.getName();
                String code = dBean.getCode();

                TNode[] rows = tableResult.getDatas()[j];
                j ++;
                int m = 0;
                for(int k = 0; k < results.size(); k ++){

                    SimpleResult result = results.get(k);
                    List<Baseid> baseids = null;// result.getBaseids();
                    List<Map<String, Object>> list = result.getResult();
                    for(Baseid b : baseids){
                        if (0 == k){
                            YNode n = new YNode();
                            n.setId(b.getId());
                            n.setCode(code);
                            n.setName(name);
                            n.setHdimension(hdimension);
                            n.setVdimension(vdimension);

                            rows[m] = n;
                            m ++;
                        }

                        for(Map<String, Object> map : list){
                            Object o = map.get(b.getId());//获取对应 baseid 数据点的值
                            Object v = map.get(d.getId());
                            if (code.equals(v)){
                                XNode node = new XNode();
                                node.setValue(o);
                                node.setHdimension(hdimension);
                                node.setVdimension(vdimension);
                                node.setId(b.getId());

                                rows[m] = node;
                                m ++;
                                break;
                            }
                        }
                    }
                }

            }
        }
        LinkedHashMap<String, List<Baseid>> relativeMap = condition.getRelativeMap();
        this.getTableDatas(tableResult,condition.getCorns(),relativeMap);
    }

    private void getTableDatas(TableResult tableResult,List<String> corns,LinkedHashMap<String, List<Baseid>> relativeMap){

        TNode[][] tableDatas = tableResult.getDatas();
        for(int m = 0; m < tableDatas.length; m ++){

            TNode[] rowData = tableDatas[m];

            Map<String,Object> data = new HashMap<>();
            List<TNode> tmpNodes = new ArrayList<>();

            for(int n = 0; n < rowData.length; n ++){
                TNode node = rowData[n];
                if (node instanceof YNode){
                    tmpNodes.add(node);//保障最左Y列数据集
                }else{
                    data.put(node.getId(),((XNode)node).getValue());
                }
            }

            for(int index = 0; index < corns.size(); index ++){
                String s = corns.get(index);
                List<Baseid> baseids = relativeMap.get(s);
                StringBuilder builder = new StringBuilder();
                int preIndex = 0;
                for(Baseid baseid : baseids){
                    Object o = data.get(baseid.getId());
                    builder.append(s.substring(preIndex, baseid.getEnd()));
                    builder.append(o);
                    preIndex = baseid.getEnd();
                }
                //TODO 根据不同数据类型进行处理 通过计算表达式直接将数据转为运行结果
                Calculator calculator = new Calculator(builder.toString());
                String result = calculator.start();
                XNode node = new XNode();
                node.setValue(result);
                tmpNodes.add(node);

                TNode[] tmp = new TNode[tmpNodes.size()];
                for(int k = 0; k < tmpNodes.size(); k ++){
                    tmp[k] = tmpNodes.get(k);
                }
                tableDatas[m] = tmp;
            }
        }
    }

}
