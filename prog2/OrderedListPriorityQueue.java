/*
   Daniel Hernandez
   cssc0928
*/

package data_structures;

import java.util.Iterator;

public class OrderedListPriorityQueue<E extends Comparable<E>> implements PriorityQueue<E> {
	private OrderedList<E> list;
	
	public OrderedListPriorityQueue() {
		list = new OrderedList<E>();
	}
	
	public boolean insert(E obj) {
		list.insert(obj);
		return true;
	}

    	public E remove() {
		return list.removeMin();
	}
   
    	public E peek() {
		return list.findMin();
	} 

    	public boolean contains(E obj) {
		return list.find(obj);
	}

    	public int size() {
		return list.listSize();
	}

    	public void clear() {
		list.makeEmpty();
	}

    	public boolean isEmpty() {
		return list.isEmpty();
	}
   
   	public boolean isFull() {
		return false;
	}
	
    	public Iterator<E> iterator() {
		return list.iterator();
	}  

}
