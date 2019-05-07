package com.study.data.structure.array;
/**
 * 动态的数组
 */
@SuppressWarnings("unchecked")
public class Array<E> implements ParentArray<E> {
	
	private Object[] data; //数据容器
	private static final Object[] DEFAULT_DATA = new Object[DEFAULT_CAPACITY];
	private int size ;
	
	public Array(){
		size = 0;
		data = DEFAULT_DATA;
	}
	
	public Array(int capacity){
		size = 0;
		data = new Object[capacity];
	}
	
	public int size() {
		return size;
	}

	public boolean isEmpty() {
		return size==0;
	}

	public boolean add(E e) {
		add(size,e);
		return true;
	}

	public E get(int index) {
		return (E)data[index];
	}

	public boolean contains(E e) {
		if(size==0){
			return false;
		}
		for(Object obj: data){
			if(e.equals((E)obj)){
				return true;
			}
		}
		return false;
	}

	public E set(int index, E e) {
		 E oldValue = get(index);
		 data[index] = e;
		 return oldValue;
	}

	public boolean remove(E e) {
		int index = indexOf(e);
		remove(index);
		size--;
		return true;
	}
	
	public boolean addFirst(E e) {
		return add(0,e);
	}

	public boolean add(int index, E e) {
		if(data.length==size){
			resize(size*2);
		}
		for(int i =size-1; i>=index ;i--){
			data[i+1] = data[i];
		}
		data[index] = e;
		size++;
		return true;
	}

	public E remove(int index) {
		if(index<0){
			throw new IllegalArgumentException(" index should be more than zero!");
		}
		if(index > size-1){
			throw new IllegalArgumentException(" index should be less than size!");
		}
		if(size<=data.length/4){
			resize(data.length/2);
		}
		
		E e = (E) data[index];
		for(int i = index ; i<size ;i++){
			data[i] = data[i+1];
		}
		size--;
		return e; 
	}

	public int indexOf(E e) {
		if(e==null ){
			for(int i=0;i<size;i++){
				if(data[i]==e){
					return i;
				}
			}
			return -1;
		}else{
			for(int i=0;i<size;i++){
				if(e.equals(data[i])){
					return i;
				}
			}
			return -1;
		}
	}

	public int lastIndexOf(E e) {
		if(e==null ){
			for(int i=size-1;i>=0;i--){
				if(data[i]==e){
					return i;
				}
			}
			return -1;
		}else{
			for(int i=size-1;i>=0;i--){
				if(e.equals(data[i])){
					return i;
				}
			}
			return -1;
		}
	}
	
	private void resize(int capacity){
		E[] newData = (E[]) (new Object[capacity]);
		for(int i=0;i<size ;i++){
			newData[i] = (E) data[i];
		}
		data = newData;
	}
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("size:").append(size).append("  ").append("capacity:").append(data.length);
		sb.append("\r\n");
		sb.append("[");
		for(int i =0; i<size;i++){
			sb.append(data[i]);
			if(i!=size-1){
				sb.append(",");
			}
		}
		sb.append("]");
		return sb.toString();
	}

	public int capacity() {
		return data.length;
	}
	
}
