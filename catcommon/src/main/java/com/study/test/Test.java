package com.study.test;


import com.study.data.structure.array.Array;
import com.study.data.structure.linked.SingleLinked;
import com.study.data.structure.queue.ArrayQueue;
import com.study.data.structure.queue.LinkedQueue;
import com.study.data.structure.stack.SingleLinkedStack;

public class Test {
	public static void textArray(){
		Array<Integer> array = new Array<Integer>();
		System.out.println(array);
		for(int i =0 ;i<11 ;i++){
			array.add(i);
		}
		System.out.println(array);
		array.remove(3);
		System.out.println(array);
		array.remove(3);
		System.out.println(array);
		array.remove(3);
		System.out.println(array);
		array.remove(3);
		System.out.println(array);
		array.remove(3);
		System.out.println(array);
		array.remove(3);
		System.out.println(array);
		array.remove(3);
		System.out.println(array);
		array.remove(3);
		System.out.println(array);
		array.remove(2);
		System.out.println(array);
		array.remove(1);
		System.out.println(array);
		array.remove(0);
		System.out.println(array);
		array.add(1);
		System.out.println(array);
		for(int i =0 ;i<11 ;i++){
			array.add(i);
		}
		System.out.println(array);
		for(int i =0 ;i<11 ;i++){
			array.add(i);
		}
		System.out.println(array);
		
		array.addFirst(8);
		System.out.println(array);
		//array.contains(8);
		System.out.println(array.contains(15));
		array.get(1);
		System.out.println(array.get(15));
		System.out.println(array.indexOf(2));
		System.out.println(array.lastIndexOf(2));
		array.set(2, 333);
		System.out.println(array);
	}
	
	public static void testStack(){
		//数组栈
		/*ArrayStack<String> stack = new ArrayStack<String>();
		System.out.println(stack.isEmpty());
		stack.push("小强");
		stack.push("小王");
		System.out.println(stack.peek());
		System.out.println(stack);
		System.out.println(stack.pop());
		System.out.println(stack);*/

		//链表栈
		SingleLinkedStack<Integer> linkedStack = new SingleLinkedStack();
		linkedStack.push(1);
		linkedStack.push(2);
		linkedStack.push(3);
		System.out.println(linkedStack);
		System.out.println(linkedStack.peek());

	}
	
	public static void testArrayQueue(){
		ArrayQueue<Long> queue = new ArrayQueue<Long>();
		queue.add(1L);
		queue.add(2L);
		queue.remove();
		System.out.println(queue.element());
		System.out.println(queue);

	}


	public static void testSingleLinked(){
		SingleLinked<Integer> a = new SingleLinked();
		a.add(0,1);
		a.remove(0);
		a.add(0,2);
		a.add(1,3);
		a.addFirst(4);
		a.addLast(9);
		System.out.println(a);
		System.out.println(a.getFirst());
		System.out.println("size:"+a.size());
		System.out.println(a.getLast());


		/*LinkedList linkedList = new LinkedList();
		linkedList.add(0,1);
		System.out.println(linkedList.size());
		linkedList.add(1,2);
		System.out.println(linkedList);*/

	}

	public static void testLinkedQueue(){
		LinkedQueue<Integer> queue = new LinkedQueue();
		queue.enqueue(1);
		queue.enqueue(2);
		queue.enqueue(3);
		System.out.println(queue);
		System.out.println(queue.dequeue());
		System.out.println(queue);
	}


	public static void main(String[] args) {
		//textArray();
		//testStack();
		//testArrayQueue();
		//testSingleLinked();
		testLinkedQueue();

	}
}
