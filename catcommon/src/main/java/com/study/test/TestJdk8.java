package com.study.test;

import java.util.function.Consumer;

public class TestJdk8 {
	
	interface MathOperation {
	      int operation(int a, int b);
	}
	
	
	interface GreetingService {
	      void sayMessage(String message);
	}
	
	public static void main(String[] args) {
		TestJdk8 jdk = new TestJdk8();
		MathOperation subtraction = (a, b) -> a - b;


		testCon("你好",(s) -> System.out.println(s));

	}


	/**
	 *
	 * @param str 传入参数
	 * @param con
	 */
	public static void testCon(String str, Consumer<String> con) {
		// 执行
		con.accept(str);

	}
	
}
