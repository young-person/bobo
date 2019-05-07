package com.study.data.structure.linked;

/**
 * Created by dd on 2019/4/26.
 */
public interface Linked<E> {

    E get(int index);
    boolean contain(E e);
    void addFirst(E e);
    void addLast(E e);
    void set(int index, E e);
    void add(int index, E e);
    E getFirst();
    E getLast();
    int size();
    E remove(int index);
    E removeFirst();
    E removeLast();
}
