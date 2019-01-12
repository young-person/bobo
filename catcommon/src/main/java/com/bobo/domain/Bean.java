package com.bobo.domain;

import java.util.ArrayList;
import java.util.List;

public class Bean{
    private List<Bean> children = new ArrayList<Bean>();
    public void setChildren(List<Bean> children) {
        this.children = children;
    }
    public List<Bean> getChildren() {
        return children;
    }
}
