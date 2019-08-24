package com.bobo.algorithm.tree;

public class TNode {

    private Object value;

    private TNode left;

    private TNode right;


    public  TNode(Object value){
        this.value = value;
    }

    public boolean add(Object value){

        TNode node = new TNode(value);
        return false;
    }

    public boolean remove(Object value){

        return false;
    }

    public TNode find(Object value){

        return null;
    }

}
