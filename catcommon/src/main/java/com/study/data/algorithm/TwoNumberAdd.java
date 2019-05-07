package com.study.data.algorithm;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;


/**
 *给出两个 非空 的链表用来表示两个非负的整数。其中，它们各自的位数是按照 逆序 的方式存储的，并且它们的每个节点只能存储 一位 数字。如果，
 *我们将这两个数相加起来，则会返回一个新的链表来表示它们的和。
 *您可以假设除了数字 0 之外，这两个数都不会以 0 开头 
 */
public class TwoNumberAdd {
	
	class ListNode{
		
		List<Integer> data;
		public ListNode(Integer[] arr){
			data = Arrays.asList(arr);
			sort();
		}
		List<Integer> getList(){
			return data;
		}
		void sort(){
			Collections.sort(data);
			Collections.reverse(data);
		}
		
		ListNode append(ListNode other){
			Integer[] arr = new Integer[other.data.size()];
			
			return null;
			//return ListNode;
		}
	}
	
	public ListNode addTwoNumbers(ListNode one, ListNode two) {
		
		return null;
    }
	
	public static void main(String[] args) {
		
	}
	
}
