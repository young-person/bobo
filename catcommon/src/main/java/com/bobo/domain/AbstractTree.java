package com.bobo.domain;

import com.google.common.collect.Lists;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public abstract class AbstractTree<T extends BaseTree<T, ID>, ID extends Serializable> implements ITree<T, ID> {
    /**
     * Gets child tree objects.
     *
     * @param list     the list
     * @param parentId the parent id
     *
     * @return the child tree objects
     */
    @Override
    public List<T> getChildTreeObjects(List<T> list, ID parentId) {
        List<T> returnList = Lists.newArrayList();
        for (T res : list) {
            if (res.getPid() == null) {
                continue;
            }
            if (Objects.equals(res.getPid(), parentId)) {
                recursionFn(list, res);
                returnList.add(res);
            }
        }
        return returnList;
    }

    /**
     * 递归列表
     *
     * @param list the list
     * @param t    the t
     */
    @Override
    public void recursionFn(List<T> list, T t) {
        List<T> children = getChildList(list, t);
        if (isNotEmpty(children)) {
            t.setChildren(children);
            t.setHasChild(true);
        }
        for (T nextChild : children) {
            // 下一个对象，与所有的资源集进行判断
            if (hasChild(list, nextChild)) {
                // 有下一个子节点,递归
                for (T node : children) {
                    // 所有的对象--跟当前这个childList 的对象子节点
                    recursionFn(list, node);
                }
                nextChild.setHasChild(true);
            }
        }
    }

    /**
     * 获得指定节点下的所有子节点
     *
     * @param list the list
     * @param t    the t
     *
     * @return the child list
     */
    @Override
    public List<T> getChildList(List<T> list, T t) {
        List<T> childList = Lists.newArrayList();
        for (T child : list) {
            if (isEmpty(child.getPid())) {
                continue;
            }
            // 判断集合的父ID是否等于上一级的id
            if (Objects.equals(child.getPid(), t.getId())) {
                childList.add(child);
            }
        }
        return childList;
    }

    /**
     * 判断是否还有下一个子节点
     *
     * @param list the list
     * @param t    the t
     *
     * @return the boolean
     */
    @Override
    public boolean hasChild(List<T> list, T t) {
        return !getChildList(list, t).isEmpty();
    }

    /**
     * 判断对象是否Empty(null或元素为0)
     * 实用于对如下对象做判断:String Collection及其子类 Map及其子类
     *
     * @param pObj 待检查对象
     *
     * @return boolean 返回的布尔值
     */
    public boolean isEmpty(Object pObj) {
        if (pObj == null) {
            return true;
        }
        if (pObj == "") {
            return true;
        }
        if (pObj instanceof String) {
            return ((String) pObj).length() == 0;
        } else if (pObj instanceof Collection) {
            return ((Collection) pObj).isEmpty();
        } else if (pObj instanceof Map) {
            return ((Map) pObj).size() == 0;
        }
        return false;
    }
    /**
     * 判断对象是否为NotEmpty(!null或元素大于0)
     * 实用于对如下对象做判断:String Collection及其子类 Map及其子类
     *
     * @param pObj 待检查对象
     *
     * @return boolean 返回的布尔值
     */
    public boolean isNotEmpty(Object pObj) {
        if (pObj == null) {
            return false;
        }
        if (pObj == "") {
            return false;
        }
        if (pObj instanceof String) {
            return ((String) pObj).length() != 0;
        } else if (pObj instanceof Collection) {
            return !((Collection) pObj).isEmpty();
        } else if (pObj instanceof Map) {
            return ((Map) pObj).size() != 0;
        }
        return true;
    }
}
