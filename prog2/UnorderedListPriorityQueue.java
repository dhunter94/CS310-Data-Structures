/*
   Daniel Hernandez
   cssc0928
*/

package data_structures;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class UnorderedListPriorityQueue<E extends Comparable<E>> implements PriorityQueue<E> {
	private UnorderedList<E> list;
	
	public UnorderedListPriorityQueue() {
		list = new UnorderedList<E>();
	}

	public boolean insert(E obj) {
		list.addLast(obj);
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
