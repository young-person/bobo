package com.study.data.structure.queue;


import com.study.data.structure.linked.SingleLinked;

/**
 * 单向链表实现队列:
 *
 * 单向链表:(没有记录尾部节点的情况下)
 *  新增和删除第一个节点都是O(1)
 *  新增和删除最后一个节点都是0(n)
 *
 *  入队: 向链表的第一个位置添加节点 O(1)
 *  出队: 移除链表的最后一个节点 O(n)
 *
 */
public class SingleLinkedQueue<E> implements Queue<E> {

	SingleLinked<E> data ;

	public SingleLinkedQueue(){
		data = new SingleLinked();
	}

	@Override
	public void add(E e) {

	}

	@Override
	public boolean offer(E e) {
		return false;
	}

	@Override
	public void put(E e) {

	}

	@Override
	public E remove() {
		return null;
	}

	@Override
	public E poll() {
		return null;
	}

	@Override
	public E take() {
		return null;
	}

	@Override
	public E element() {
		return null;
	}

	@Override
	public E peek() {
		return null;
	}

	@Override
	public void enqueue(E e) {
		data.addFirst(e);
	}

	@Override
	public E dequeue() {
		return data.removeLast();
	}
}
