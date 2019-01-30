package com.bobo.domain;

import java.io.Serializable;
import java.util.List;

public class BaseTree<E, ID> implements Serializable {
    private static final long serialVersionUID = -5703964834600572016L;

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }

    public ID getPid() {
        return pid;
    }

    public void setPid(ID pid) {
        this.pid = pid;
    }

    public boolean isHasChild() {
        return hasChild;
    }

    public void setHasChild(boolean hasChild) {
        this.hasChild = hasChild;
    }

    public List<E> getChildren() {
        return children;
    }

    public void setChildren(List<E> children) {
        this.children = children;
    }

    /**
     * ID
     */
    private ID id;

    /**
     * 父ID
     */
    private ID pid;

    /**
     * 是否含有子节点
     */
    private boolean hasChild = false;

    /**
     * 子节点集合
     */
    private List<E> children;
}
