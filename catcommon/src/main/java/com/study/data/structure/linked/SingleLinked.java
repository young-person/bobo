package com.study.data.structure.linked;

/**
 * 单向链表
 *   虚拟节点(dummyHead) --> 0 --> 1 --> 2
 *
 */
public class SingleLinked<E> implements Linked<E> {

    private int size; //节点个书
    private Node dummyHead; //虚拟节点(虚拟头)



    /**
     * node 节点  链表的基本元素
     */
    private class Node{
        private E data; //数据的存储
        private Node next;     //节点的指向

        public Node(E e, Node next){
            this.data = e;
            this.next = next;
        }

        public Node(E e){
            this(e,null);
        }

        public Node(){
            this(null,null);
        }
    }

    public SingleLinked(){
        size = 0;
        dummyHead = new Node();
    }

    @Override
    public E get(int index) {
        if(index < 0 || index > size){
            throw new IllegalArgumentException(" index is illegal");
        }
        Node cur = dummyHead.next;
        for(int i=0 ;i<index; i++){
            cur = cur.next;
        }
        return cur.data;
    }

    @Override
    public boolean contain(E e) {
        Node cur = dummyHead;
        while (cur.next!=null){
            if(e.equals(cur.data)){
                return true;
            }
            cur = cur.next;
        }
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

    /**
     * set 实际替换的是 node里面的数据,没必要把整个node替换掉
     */
    @Override
    public void set(int index, E e) {
        if(index < 0 || index > size){
            throw new IllegalArgumentException(" index is illegal");
        }
        Node cur = dummyHead;
        for(int i=0; i<index; i++){
            cur = cur.next;
        }
        cur.data = e;
    }


    @Override
    public void add(int index, E e) {
        if(index < 0 || index > size){
            throw new IllegalArgumentException(" index is illegal");
        }
        Node prev = dummyHead;
        for(int i=0; i<index; i++){
            prev = prev.next;
        }
        //循环完毕后, prev 对应的是 index-1 的节点
        Node node = new Node(e);
        node.next = prev.next;
        prev.next = node;
        size++;
    }

    @Override
    public E getFirst() {
        return get(0);
    }

    @Override
    public E getLast() {
        return get(size-1);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public E remove(int index) {
        if(index < 0 || index > size){
            throw new IllegalArgumentException(" index is illegal");
        }
        Node cur = dummyHead;
        for(int i=0 ; i<index; i++){
            cur = cur.next;
        }
        //循环走完之后, cur指向index所对应的节点的[前一个]节点
        Node temp = cur.next;
        Node temp2 = temp.next;
        cur.next = temp2;
        temp.next = null;
        size--;
        return cur.data;
    }

    @Override
    public E removeFirst() {
        return remove(0);
    }

    @Override
    public E removeLast() {
        return remove(size);
    }


    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("[");
        Node cur = dummyHead;
        while( cur.next!= null ){
            sb.append("--> ");
            sb.append(cur.next.data);
            cur = cur.next;
        }
        sb.append("]");
        return sb.toString().replaceFirst("-->","");
    }
}
