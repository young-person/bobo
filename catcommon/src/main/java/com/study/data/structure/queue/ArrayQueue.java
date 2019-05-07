package com.study.data.structure.queue;

import com.study.data.structure.array.Array;

import java.util.NoSuchElementException;


/**
 * 用动态数组实现的队列
 * 出队操作效率极低,因为移除了数组的第一个元素,后面的元素全部要往前移动一位,时间复杂度O(n)
 *
 * 方式一:(推荐)
 * 入队: 往数组的末尾添加一个元素O(1)
 * 出队: 移除数组的第一个元素O(n)
 *
 * 方式二:
 * 入队: 往数组的第一个位置添加元素 O(n)
 * 出队: 移除数组的最后一个元素O(1)
 *
 */
public class ArrayQueue<E> implements Queue<E> {
	private Array<E> data;
	
	public ArrayQueue(){
		data= new Array<E>();
	}

	public void add(E e) {
		this.enqueue(e);
	}

	public boolean offer(E e) {
		return false;
	}

	
	public E remove() {
		return dequeue();
	}

	public E element() {
		return data.get(0);
	}

	public E poll() {
		return null;
	}

	public E peek() {
		return null;
	}

	public void put(E e) {

	}

	public E take() {
		return null;
	}

	//入队
	public void enqueue(E e){
		data.add(e);
	}
	//出队
	public E dequeue(){
		if(data.size()==0){
			throw new NoSuchElementException("queue is empty");
		}
		E e = data.get(0);
		data.remove(0);
		return e;
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
