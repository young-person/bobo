package com.study.data.structure.linked;

/**
 *  双向链表
 *   虚拟节点 0 <--> 1 <--> 2
 */
public class DoubleLinked<E> implements Linked<E> {

    /**
     * node 节点  链表的基本元素
     */
    private class Node{
        private E data; //数据的存储
        private Node prev;  //前一个节点
        private Node next;  //后一个节点
        private String desc; //描述

        public Node(E e, Node prev, Node next,String desc){
            this.data = e;
            this.prev = prev;
            this.next = next;
            this.desc =desc;
        }

        public Node(E e,String desc){
            this(e,null,null,desc);
        }

        public Node(E e){
            this(e,null,null,"");
        }

        public Node(String desc){
            this(null,null,null,desc);
        }

        public Node(){
            this(null,null,null,"");
        }
    }

    private int size; //节点个书
    private Node head; //虚拟头
    private Node tail; //虚拟尾

    public DoubleLinked(){
        head = new Node("head");
        tail = new Node("tail");
        head.next = tail;
        tail.prev = head;
        size = 0;
    }

    @Override
    public E get(int index) {
        return null;
    }

    @Override
    public boolean contain(E e) {
        return false;
    }

    @Override
    public void addFirst(E e) {
        add(0,e);
    }

    @Override
    public void addLast(E e) {
        add(size,e);
    }

    @Override
    public void set(int index, E e) {
        if(index<0 || index>= size){
            throw new IllegalArgumentException(" index is illegal");
        }
        //Node newNode = new Node(e);
        if(index <= size/2){ //当索引在 size 一半之前, 向头部节点新加节点
            Node prev = head;
            for (int i =0 ; i< index ;i++){
                prev = prev.next;
            }
            //循环完,prev 指向的是 index-1 的节点
            Node cur = prev.next; //当前节点(index位置)
            cur.data = e;
        }else{ //在尾部加
            Node next = tail;
            for (int i = size ; i> index ;i--){
                next = next.prev;
            }
            //循环完毕 next 指向 index 位置
            next.data = e;
        }
    }

    @Override
    public void add(int index, E e) {
        if(index<0 || index> size){
            throw new IllegalArgumentException(" index is illegal");
        }
        Node newNode = new Node(e);
        if(index <= size/2){ //当索引在 size 一半之前, 向头部开始遍历
            Node prev = head;
            for (int i =0 ; i< index ;i++){
                prev = prev.next;
            }
            //循环完,prev 指向的是 index-1 的节点
            Node next = prev.next; //当前节点(index位置)
            next.prev = newNode;
            newNode.next = next;
            newNode.prev = prev;
            prev.next = newNode;
        }else{ //在从尾部开始遍历
            Node next = tail;
            for (int i = size ; i> index ; i--){
                next = next.prev;
            }
            //循环完毕后, next 对应的是 index 的节点
            Node cur = next.prev; //当前节点( index-1 位置)
            cur.next = newNode;
            newNode.prev = cur;
            newNode.next = next;
            next.prev = newNode;
        }
        size++;
    }

    @Override
    public E getFirst() {
        return null;
    }

    @Override
    public E getLast() {
        return null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public E remove(int index) {
        if(index<0 || index>= size){
            throw new IllegalArgumentException(" index is illegal");
        }
        if(head.next==tail){
            throw new IllegalArgumentException(" empty list can't remove element");
        }
        Node res = null;
        if(index <= size/2){ //当索引在 size 一半之前, 向头部节点新加节点
            Node prev = head;
            for (int i =0 ; i< index ;i++){
                prev = prev.next;
            }
            //循环完,prev 指向的是 index-1 的节点
            Node cur = prev.next; //当前节点(index位置)
            res = cur;
            cur.prev.next = cur.next;
            cur.next.prev = cur.prev;
        }else{ //在尾部加
            Node cur = tail;
            for (int i = size ; i> index ;i--){
                cur = cur.prev;
            }
            res = cur;
            //循环完毕 得到的就是index 位置的节点
            cur.prev.next = cur.next;//当前节点的前一个节点的下一个节点 指向 当前节点的下一个几点
            cur.next.prev = cur.prev;
        }
        size--;
        return res.data;
    }

    @Override
    public E removeFirst() {
        return remove(0);
    }

    @Override
    public E removeLast() {
        return remove(size-1);
    }

    private boolean isEmpty(){
        return size == 0;
    }


    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("[");
        Node cur = head;
        while( cur.next.data!= null ){
            sb.append("<--> ");
            sb.append(cur.next.data);
            cur = cur.next;
        }
        sb.append("]");
        return sb.toString().replaceFirst("<-->","");
    }
}
