package com.bobo.domain;

import com.bobo.annotation.Tree;
import com.bobo.utils.CReflectUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class TreeEntity<T> {
    private static Logger logger = LoggerFactory.getLogger(TreeEntity.class);
    private static ObjectMapper objectMapper = new ObjectMapper();

    public List<Bean> buildListTree(List<T> datas){
        if(null == datas || 0 == datas.size()){
            logger.error("转换数据长度错误");
            return null;
        }
        logger.info("转成树结构数据：{}",datas.size());
        return findRootList(datas);
    }

    private List<Bean> findRootList(List<T> datas){
        List<Bean> list = changeBeanToDynamicBean(datas);

        String id = null,pid = null,text = null,icon = null,url = null;
        Field[] fields = datas.get(0).getClass().getDeclaredFields();
        List<Map<String,String>> children = new ArrayList<Map<String,String>>();
        for(Field f : fields) {
            f.setAccessible(true);
            Tree tree = f.getAnnotation(Tree.class);
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
        logger.info("转成树结构对应字段有：{},{},{},{},{}",new Object[]{id ,pid ,text,icon,url});
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
            logger.error(e.getMessage(),e);
        } catch (NoSuchMethodException e) {
            logger.error(e.getMessage(),e);
        } catch (InvocationTargetException e) {
            logger.error(e.getMessage(),e);
        }
        return new ArrayList<Bean>();
    }

    private List<Bean> findChildrenNodes(Bean rNode,List<Bean> notRoots,String pid,String id) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        String root_id = BeanUtils.getProperty(rNode, id);
        List<Bean> children = new ArrayList<Bean>();

        for(Bean b : notRoots){
            String node_pid = BeanUtils.getProperty(b, pid);
            if(root_id.equals(node_pid)){
                children.add(b);
            }
        }

/*        List<Bean> notChildren = new ArrayList<Bean>();
        for(int index = 0; index < notRoots.size(); index ++){
            if (!children.contains(notRoots.get(index))){
                notChildren.add(notRoots.get(index));
            }
        }
        notRoots = notChildren;*/
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
    private List<Bean> findRootBean(List<Bean> list,String pid,String id) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        //节点状态
        Map<String,Boolean> has = new HashMap<String,Boolean>();
        Map<String,String> m = new HashMap<String, String>();
        //父节点为空或者null的父节点
        Set<Bean> pNodes1 = new HashSet<Bean>();
        //完整的父节点
        Map<String,Bean> pNodes2 = new HashMap<String, Bean>();
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
                m.put(idValue,pValue);
                has.put(pValue,null != has.get(pValue));//false

                if (null == pNodes2.get(pValue)){
                    if(!has.get(pValue) || null == has.get(pValue)){//一直是false
                        pNodes2.put(pValue,map);
                    }
                }else{
                    if(has.get(pValue)){//如果里面有pid
                        pNodes2.remove(pValue);
                    }
                }
            }
        }
        if (errorNodes.size()>0){
            logger.error("当前数据里有错误节点个数：{}",errorNodes.size());
        }
        Iterator<Map.Entry<String, Boolean>> it = has.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry<String, Boolean> entry = it.next();
            if(has.get(entry.getKey()) || null != m.get(entry.getKey())){//处理父节点为子节点的
                it.remove();
                if(null != pNodes2.get(entry.getKey())){
                    pNodes2.remove(entry.getKey());
                }
            }
        }
        List<Bean> results = new ArrayList<Bean>();
        pNodes1.forEach(node->{
            results.add(node);
        });
        pNodes2.forEach((K,V)->{
            results.add(V);
        });
        errorNodes.forEach(node->{
            results.add(node);
        });
        logger.info("生成根节点：{}，错误节点个数{}，父节点为null节点个数,正确节点个数：{}，总节点个数：{}",new Object[]{errorNodes.size(),pNodes1.size(),
                pNodes2.size(),results.size()});
        return results;
    }


    /**
     * 将对象 包含注解的 转为map
     * @param t
     * @param fields
     * @return
     */
    private Map<String,String> getFiledValues(T t,Field[] fields){
        Map<String,String> map = new HashMap<String, String>();
        for(Field f : fields){
            f.setAccessible(true);
            Tree tree = f.getAnnotation(Tree.class);
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
    private List<Bean> changeBeanToDynamicBean(List<T> datas){
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

    public static void main(String[] args) {
        TreeEntity entity =  new TreeEntity();
        InputStream stream = entity.getClass().getResourceAsStream("/tree.json");

        String path = entity.getClass().getResource("/tree.json").getPath();
        System.out.println(path);
        File file = new File(path);
        String cxt = readTextAllContent(file);
        System.out.println(cxt);
    }


    public static String readTextAllContent(File file) {
        StringBuilder result = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String s = null;
            while ((s = br.readLine()) != null) {
                result.append(System.lineSeparator() + s);
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    }

}
