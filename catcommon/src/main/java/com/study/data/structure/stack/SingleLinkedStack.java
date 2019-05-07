package com.study.data.structure.stack;

import com.study.data.structure.linked.SingleLinked;

/**
 * 单向列表实现 栈
 * 单向链表的 新增第一个节点和删除第一个节点 都是 O(1)的复杂度,所有 入栈 出栈 都从第一个节点下手
 *
 * 入栈: 往链表的头部新增一个节点
 * 出栈: 删除链表的头部第一个元素
 *
 */
@SuppressWarnings("unchecked")
public class SingleLinkedStack<E> implements Stack<E> {

	private SingleLinked<E> data;

	public SingleLinkedStack(){
		data = new SingleLinked<E>();
	}
	
	public void push(E e) {
		data.addFirst(e);
	}

	public E peek() {
		return data.getFirst();
	}

	public E pop() {
		return data.removeFirst();
	}

	public boolean isEmpty() {
		return data.size()==0;
	}

	public int search(E e) {
		return -1;
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("size:").append(data.size()).append("  ");
		sb.append("\r\n");
		sb.append("[top]  ");
		for(int i =0; i<data.size();i++){
			sb.append(data.get(i));
			if(i!=data.size()-1){
				sb.append(",");
			}
		}
		sb.append("   [bottom]");
		return sb.toString();
	}

}
