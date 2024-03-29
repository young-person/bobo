package com.study.data.structure.queue;

/**
 * 循环数组 实现队列:
 * 出队和入队 O(1)
 */
public class LoopQueue<E> implements Queue<E> {

	private E[] data;
	private int front; //队列的头部
	private int	tail;  //队列的尾部
	private int size;

	public LoopQueue(int capacity){
		data = (E[])new Object[capacity];
		size= 0;
		front = 0;
		tail = 0;
	}

	public LoopQueue(){
		this(10);
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

	public int getCapacity(){
		return data.length - 1;
	}

	public boolean isEmpty(){
		return front == tail;
	}

	@Override
	public void enqueue(E e) {
		if((tail + 1) % data.length == front)
			resize(getCapacity() * 2);

		data[tail] = e;
		tail = (tail + 1) % data.length;
		size ++;
	}

	@Override
	public E dequeue() {
		if(isEmpty())
			throw new IllegalArgumentException("Cannot dequeue from an empty queue.");

		E ret = data[front];
		data[front] = null;
		front = (front + 1) % data.length;
		size --;
		if(size == getCapacity() / 4 && getCapacity() / 2 != 0)
			resize(getCapacity() / 2);
		return ret;
	}

	private void resize(int newCapacity){
		E[] newData = (E[])new Object[newCapacity + 1];
		for(int i = 0 ; i < size ; i ++) {
			newData[i] = data[(i + front) % data.length];
		}
		data = newData;
		front = 0;
		tail = size;
	}


	@Override
	public String toString(){
		StringBuilder res = new StringBuilder();
		res.append(String.format("Queue: size = %d , capacity = %d\n", size, getCapacity()));
		res.append("front [");
		for(int i = front ; i != tail ; i = (i + 1) % data.length){
			res.append(data[i]);
			if((i + 1) % data.length != tail)
				res.append(", ");
		}
		res.append("] tail");
		return res.toString();
	}


}
