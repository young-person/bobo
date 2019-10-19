package com.bobo.domain;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bobo.annotation.Tree;
import com.bobo.base.BaseClass;
import com.bobo.utils.CReflectUtil;

/**
 * 树转换生成类
 */
public class TreeUtils extends BaseClass{

    public static  <T> List<Bean> buildListTree(List<T> datas){
        if(null == datas || 0 == datas.size()){
            LOGGER.error("转换数据长度错误");
            return null;
        }
        LOGGER.info("转成树结构数据：{}",datas.size());
        return findRootList(datas);
    }

    private static <T> List<Bean> findRootList(List<T> datas){
        List<Bean> list = changeBeanToDynamicBean(datas);

        String id = null,pid = null,text = null,icon = null,url = null;
        Field[] fields = datas.get(0).getClass().getDeclaredFields();
        for(Field f : fields) {
            f.setAccessible(true);
            Tree tree = f.getAnnotation(Tree.class);
            if (null == tree) continue;
            if(StringUtils.isNoneBlank(tree.id())){
                id = tree.id();
            }
            if(StringUtils.isNoneBlank(tree.pid())){
                pid = tree.pid();
            }
            if(StringUtils.isNoneBlank(tree.text())){
                text = tree.text();
            }
            if(StringUtils.isNoneBlank(tree.icon())){
                icon = tree.icon();
            }
            if(StringUtils.isNoneBlank(tree.url())){
                url = tree.url();
            }
        }
        LOGGER.info("转成树结构对应字段有：{},{},{},{},{}",new Object[]{id ,pid ,text,icon,url});
        List<Bean> notRoots = new ArrayList<Bean>();
        try {
            List<Bean> results = findRootBean(list, pid, id);//获取跟节点
            list.forEach(node->{
                if (!results.contains(node)){
                    notRoots.add(node);
                }
            });
            for(Bean rootNode:results){
                rootNode.setChildren(findChildrenNodes(rootNode,notRoots,pid,id));
            }
            return results;
        } catch (IllegalAccessException e) {
            LOGGER.error(e.getMessage(),e);
        } catch (NoSuchMethodException e) {
            LOGGER.error(e.getMessage(),e);
        } catch (InvocationTargetException e) {
            LOGGER.error(e.getMessage(),e);
        }
        return new ArrayList<Bean>();
    }

    private static List<Bean> findChildrenNodes(Bean rNode,List<Bean> notRoots,String pid,String id) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        String root_id = BeanUtils.getProperty(rNode, id);
        List<Bean> children = new ArrayList<Bean>();

        for(Bean b : notRoots){
            String node_pid = BeanUtils.getProperty(b, pid);
            if(root_id.equals(node_pid)){
                children.add(b);
            }
        }

        Iterator<Bean> iterable = notRoots.iterator();
        while(iterable.hasNext()){
            Bean b=iterable.next();
            if(children.contains(b)){
                iterable.remove();
            }
        }

        for(Bean b: children){
            List<Bean> tmpChildren = findChildrenNodes(b,notRoots,pid,id);
            b.setChildren(tmpChildren);
        }

        return children;
    }

    /**
     * 查找根节点
     * @param list
     * @param pid
     * @param id
     * @return
     * @throws IllegalAccessException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     */
    private static List<Bean> findRootBean(List<Bean> list,String pid,String id) throws IllegalAccessException,
            NoSuchMethodException, InvocationTargetException {
        //节点状态
        Map<String,Boolean> has = new HashMap<String,Boolean>();
        Map<String,String> m = new HashMap<String, String>();
        //父节点为空或者null的父节点
        Set<Bean> pNodes1 = new HashSet<Bean>();
        //完整的父节点
        //Map<String,Bean> pNodes2 = new HashMap<String, Bean>();
        List<Bean> pNodes2 = new ArrayList<Bean>();
        //错误节点进行数据 过滤
        Set<Bean> errorNodes = new HashSet<Bean>();
        for(Bean map : list){//如果pid我最上层节点
            String pValue = BeanUtils.getProperty(map, pid);//  map.get(pid);//父节点值
            String idValue = BeanUtils.getProperty(map, id);//map.get(id);
            if (StringUtils.isBlank(idValue)){
                errorNodes.add(map);
            }else if(StringUtils.isBlank(pValue) && StringUtils.isNotBlank(idValue)){
                pNodes1.add(map);
            }else{
                if (m.isEmpty()){
                    m.put(idValue,pValue);
                    has.put(idValue,false);
                    has.put(pValue,true);
                }else{
                    boolean is = false;
                    int last = 0;
                    for(String key : m.keySet()){
                        String val = m.get(key);
                        if(pValue.equals(val)){
                            m.put(idValue,pValue);
                            has.put(idValue,false);
                            break;
                        }else if (idValue.equals(val)){
                            m.put(idValue,pValue);
                            has.put(pValue,true);
                            has.put(idValue,false);
                            break;
                        }else if(pValue.equals(key)){
                            m.put(idValue,pValue);
                            has.put(idValue,false);
                            has.put(pValue,false);
                            break;
                        }else{
                            if(last == (m.keySet().size() - 1)){
                                is = true;
                            }
                        }
                        if(is){
                            m.put(idValue,pValue);
                            has.put(pValue,true);
                            break;
                        }
                    }
                }
            }
        }
        if (errorNodes.size()>0){
            LOGGER.error("当前数据里有错误节点个数：{}",errorNodes.size());
        }
        Iterator<Map.Entry<String, Boolean>> it = has.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry<String, Boolean> entry = it.next();
            if(entry.getValue()){
                for(Bean map : list){
                    String pValue = BeanUtils.getProperty(map, pid);
                    if (entry.getKey().equals(pValue)){
                        pNodes2.add(map);
                    }
                }
            }
        }
        List<Bean> results = new ArrayList<Bean>();
        /**
         * 如父节点为空 还需做进一步父节点处理
         */
        pNodes1.forEach(node->{
            results.add(node);
        });
        results.addAll(pNodes2);
        errorNodes.forEach(node->{
            results.add(node);
        });
        LOGGER.info("生成根节点：{}，错误节点个数{}，父节点为null节点个数,正确节点个数：{}，总节点个数：{}",new Object[]{errorNodes.size(),pNodes1.size(),
                pNodes2.size(),results.size()});
        return results;
    }


    /**
     * 将对象 包含注解的 转为map
     * @param t
     * @param fields
     * @return
     */
    private static <T> Map<String,String> getFiledValues(T t,Field[] fields){
        Map<String,String> map = new HashMap<String, String>();
        for(Field f : fields){
            f.setAccessible(true);
            Tree tree = f.getAnnotation(Tree.class);
            if (null == tree) continue;
            String id =   tree.id();
            String pid =  tree.pid();
            String text = tree.text();
            String icon = tree.icon();
            String url =  tree.url();
            String fieldName = f.getName();
            String value = "";
            if(PropertyUtils.isReadable(t, fieldName) && PropertyUtils.isWriteable(t, fieldName)) {
                try {
                    value = BeanUtils.getProperty(t, fieldName);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
            }
            if (StringUtils.isNoneBlank(id)){
                map.put(id,value);
            }
            if (StringUtils.isNoneBlank(pid)){
                map.put(pid,value);
            }
            if (StringUtils.isNoneBlank(text)){
                map.put(text,value);
            }
            if (StringUtils.isNotBlank(icon)){
                map.put(icon,value);
            }
            if (StringUtils.isNotBlank(url)){
                map.put(url,value);
            }
        }
        return map;
    }

    /**
     * 将转换数据转为bean
     * @param datas
     * @return
     */
    private static <T> List<Bean> changeBeanToDynamicBean(List<T> datas){
        List<Bean> beans = new ArrayList<Bean>();
        for(T t: datas){
            Field[] fields = t.getClass().getDeclaredFields();
            Map<String,String> map = getFiledValues(t,fields);
            Bean bean = new Bean();
            bean = CReflectUtil.getTargetStr(bean,map);
            beans.add(bean);
        }
        return beans;
    }

}
