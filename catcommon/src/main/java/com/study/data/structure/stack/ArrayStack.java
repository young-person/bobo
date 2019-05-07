package com.study.data.structure.stack;

import com.study.data.structure.array.Array;

/**
 * 数组实现栈
 * 数组的最后一个元素 新增和移除操作是 O(1)的复杂度
 * 入栈: 往数组的最后一个位置新增一个元素O(1)
 * 出栈: 删除数组的最后一个元素 O(1)
 *
 */
@SuppressWarnings("unchecked")
public class ArrayStack<E> implements Stack<E> {
	private Array<E> data;
	
	public ArrayStack(){
		data = new  Array<E>();
	}
	
	public void push(E e) {
		data.add(e);
	}

	public E peek() {
		return data.get(data.size()-1);
	}

	public E pop() {
		E temp = data.get(data.size()-1);
		data.remove(data.size()-1);
		return temp;
	}

	public boolean isEmpty() {
		return data.size()==0;
	}

	public int search(E e) {
		return data.indexOf(e);
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("size:").append(data.size()).append("  ").append("capacity:").append(data.capacity());
		sb.append("\r\n");
		sb.append("[");
		for(int i =0; i<data.size();i++){
			sb.append(data.get(i));
			if(i!=data.size()-1){
				sb.append(",");
			}
		}
		sb.append("]");
		return sb.toString();
	}

}
