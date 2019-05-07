package com.study.data.structure.queue;


/**
 * 基于单向链表的优化
 *
 * 新增了一个变量记录链表尾部的位置
 *
 *
 * 在链表末端添加(入队)
 * 在链表头部移除(出队)
 *
 * 因为 如果是头部入队,尾部出队的话 ,不好找倒数第二个节点
 *
 */
public class LinkedQueue<E> implements Queue<E> {

	/**
	 * 节点对象
	 */
	private class Node{

		E data;

		Node next; //下一个节点

		public Node(E e , Node next){
			this.data =e;
			this.next = next;
		}

		public Node(E e){
			this.data = e;
			next = null;
		}

		public Node(){
			this(null);
		}

	}

	//成员变量
	private int size; //节点个数
	private Node tail; //尾部节点
	private Node head; //头部节点


	public LinkedQueue(){
		size = 0;
		tail = null;
		head = null;
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


	private boolean isEmpty(){
		return size==0;
	}

	// 向链表的尾部新增节点
	@Override
	public void enqueue(E e) {
		if(size==0){  //如果 队列一个节点都没有
			tail = new Node(e); //头和尾 指向同一个节点
			head = tail;
		}else{
			//向尾部节点后插入一个节点
			Node lastNode = new Node(e); //新增一个节点
			tail.next = lastNode;  //把新增的节点 放到当前tail的后面
			tail = tail.next;  //在把最后的节点 重新赋值给 tail
		}
		size++;
	}

	//取出链表头部的节点
	@Override
	public E dequeue() {
		if(isEmpty()){
			throw new IllegalArgumentException("Cannot dequeue from an empty queue.");
		}
		Node resNode = head; //把头部节点给一个临时变量
		head = head.next;  //把head 向后移动一位
		resNode.next=null; //令临时变量的下一个指向空,也就是原来的第一个节点的next 指向null,那么原来的第一个节点 就会跟原来的链表脱离
		E e = resNode.data;
		resNode = null;  //把这个临时的变量销毁,那么 这个resNode 指向的对象 也会被内存回收
		if(head==null){
			tail=null;
		}
		size--;
		return e;
	}

	@Override
	public String toString() {
		StringBuilder res = new StringBuilder();
		res.append("Queue: front ");
		Node cur = head;
		while (cur!=null){
			res.append(cur.data + "->");
			cur = cur.next;
		}
		res.append(" tail");
		return res.toString();
	}
}
