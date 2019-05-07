package com.study.data.structure.queue;

/**
 * 队列组类
 */
public interface Queue<E> {
	
	//add 增加一个元索 如果队列已满，则抛出一个IIIegaISlabEepeplian异常(在尾部添加)
	void add(E e);
	
	//添加一个元素并返回true 如果队列已满，则返回false(在尾部添加)
	boolean offer(E e);
	
	//添加一个元素 如果队列满，则阻塞
	void put(E e);
	 
	
	//移除并返回队列头部的元素 如果队列为空，则抛出一个NoSuchElementException异常
	E remove();
	
	//移除并返问队列头部的元素 如果队列为空，则返回null
	E poll();
	
	//移除并返回队列头部的元素 如果队列为空，则阻塞
	E take();
	
	// 返回队列头部的元素 如果队列为空，则抛出一个NoSuchElementException异常
	E element();
	
	//返回队列头部的元素 如果队列为空，则返回null
	E peek();


	//入队
	void enqueue(E e);

	//出队
	E dequeue();

}
