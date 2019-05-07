package com.study.data.algorithm;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * 给定一个整数数组 nums 和一个目标值 target，请你在该数组中找出和为目标值的那 两个 整数，并返回他们的数组下标。
 * 你可以假设每种输入只会对应一个答案。但是，你不能重复利用这个数组中同样的元素。
 * 示例:
 * 	给定 nums = [2, 7, 11, 15], target = 9
 * 	因为 nums[0] + nums[1] = 2 + 7 = 9	
 *  所以返回 [0, 1]
 *
 */
public class TwoNumberSum {
	
	/**
	 * 简单粗暴的遍历
	 * 时间复杂度：O(n^2),对于每个元素，我们试图通过遍历数组的其余部分来寻找它所对应的目标元素,这将耗费 O(n)的时间
	 */
	public static int[] twoSum(int[] nums, int target) {
		for(int m =0 ; m< nums.length-1 ;m++){
			for(int n = m+1 ; n< nums.length ;n++){
				if(nums[m]+nums[n]==target){
					return new int[]{nums[m],nums[n]};
				}
			}
		}
		return null;
	}
	
	/**
	 * 两遍哈希表
	 * 	通过以空间换取速度的方式，我们可以将查找时间从 O(n) 降低到  O(1)
	 * 	时间复杂度：O(n)， 我们把包含有 n 个元素的列表遍历两次。由于哈希表将查找时间缩短到 O(1)，所以时间复杂度为 O(n)
	 *  核心: 用set 去遍历key值
	 */
	public static int[] twoSumByTwoHash(int[] nums, int target) {
		Map<Integer,Integer> map = new HashMap<Integer,Integer>();
		for(int i =0 ;i<nums.length ;i++){
			map.put(nums[i], i);
		}
		System.out.println(map.toString());
		for(int i =0 ;i<nums.length ;i++){
			Integer temp = target - nums[i];
			if(map.containsKey(temp) && map.get(temp)!=i){
				return new int[]{i, map.get(temp)};
			} 
		}
		return null;
	}
	
	/**
	 * 一遍哈希表
	 * 在进行迭代并将元素插入到表中的同时，我们还会回过头来检查表中是否已经存在当前元素所对应的目标元素。如果它存在，那我们已经找到了对应解，并立即将其返回
	 * 时间复杂度：O(n)， 我们只遍历了包含有 n 个元素的列表一次。在表中进行的每次查找只花费 O(1) 的时间
	 */
	public static int[] twoSumByOneHash(int[] nums, int target) {
		Map<Integer,Integer> map = new HashMap<Integer,Integer>();
		for(int i =0 ;i<nums.length ;i++){
			Integer temp = target - nums[i];
			if(map.containsKey(temp)){
				return new int[]{i, map.get(temp)};
			} 
			map.put(nums[i], i);
			
		}
		return null;
	}
	
	
	public static void main(String[] args) {
		int[] nums = {3,3};
		System.out.println(Arrays.toString(twoSumByOneHash(nums,6)));
	}
	
}
