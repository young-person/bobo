package com.bobo.algorithm.list.impl;

import com.bobo.algorithm.list.CList;

public class CListImpl<E> implements CList<E> {

    public static void main(String[] args) {
        CListImpl<Integer> list = new CListImpl<Integer>();


        for(int index = 0; index < 20; index ++){
            list.add(index);
        }
    }

    private int size = 0;
    private  Object[] nodes = new Object[CAPACITY];
    private  static int CAPACITY = 8;
    public int length = CAPACITY;

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return 0 == size;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public boolean add(Object o) {

        if ((size + 1) > length){
           length += CAPACITY;
           Object[] tmpNodes = nodes;
           nodes = new Object[length];
           for(int i = 0; i < tmpNodes.length; i++){
               nodes[i] = tmpNodes[i];
           }
        }
        nodes[size] = o;
        size ++;

        return true;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public E get(int index) {
        return null;
    }

    @Override
    public E set(int index, E element) {
        return null;
    }

    @Override
    public void add(int index, E element) {

    }

    @Override
    public E remove(int index) {
        return null;
    }

    @Override
    public int indexOf(Object o) {
        return 0;
    }

    @Override
    public int lastIndexOf(Object o) {
        return 0;
    }
}
