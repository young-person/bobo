package com.study.data.structure.array;

/**
 * 所有数组的祖类
 */
public interface ParentArray<E> {
	 // int size; 接口只能定义常量的成员变量,而且成员变量只能是public 类型
	public int DEFAULT_CAPACITY = 10;
	
	int size();
	
	boolean isEmpty();
	
	boolean add(E e);
	
	E get(int index);
	
	boolean contains(E e);
	
	E set(int index, E e);

	boolean remove(E e);

	boolean addFirst(E e);

	boolean add(int index, E e);
	
	E remove(int index);
	
	int indexOf(E e);
	
	int lastIndexOf(E e);
	
	int capacity();
	
}
