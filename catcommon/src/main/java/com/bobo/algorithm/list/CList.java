package com.bobo.algorithm.list;

public interface CList<E> {

    int size();

    boolean isEmpty();

    boolean contains(Object o);

    boolean add(E e);

    boolean remove(Object o);

    void clear();

    boolean equals(Object o);

    int hashCode();

    E get(int index);

    E set(int index, E element);


    E remove(int index);
    void add(int index, E element);

    int indexOf(Object o);

    int lastIndexOf(Object o);



}
