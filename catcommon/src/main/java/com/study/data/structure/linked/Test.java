package com.study.data.structure.linked;

/**
 * Created by Administrator on 2019/4/30.
 */
public class Test {
    public static void testDoubleLinked(){
        DoubleLinked<Integer> list = new DoubleLinked();
        list.add(0,1);
        //System.out.println(list);
        list.add(0,2);
        //System.out.println(list);
        list.add(1,3);
        //System.out.println(list);
        list.add(1,4);
        //System.out.println(list);
        list.add(2,5);
        //System.out.println(list);
        list.add(4,9);
        //System.out.println(list);
        list.add(6,9);
        //System.out.println(list);
        list.addLast(10);
        //System.out.println(list);
        list.addFirst(7);
        System.out.println(list);
        list.removeLast();
        System.out.println(list);
        list.set(2,898);
        System.out.println(list);
    }

    public static void main(String[] args) {
        testDoubleLinked();
    }
}
