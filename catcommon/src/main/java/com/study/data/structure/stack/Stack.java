package com.study.data.structure.stack;

/**
 * 栈的祖类
 */
public interface Stack<E> {
	//入栈
	void push(E e);
	
	//返回位于栈顶的元素，但是并不在堆栈中删除它
	E peek();
	
	//返回位于栈顶的元素，并在进程中删除它
	E pop();
	
	boolean isEmpty();
	
	//在堆栈中搜索element，如果发现了，则返回它相对于栈顶
	int search(E e);
	
}
