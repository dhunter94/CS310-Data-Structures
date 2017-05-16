/*
   Daniel Hernandez
   cssc0928
*/

package data_structures;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.ConcurrentModificationException;

public class OrderedList<E extends Comparable<E>> implements Iterable<E> {
	public class Node<T> {
		T data;
		Node<T> next;
		
		public Node(T obj) {
			data = obj;
			next = null;
		}
	}
	
	private Node<E> head;
	private int currentSize;
	private long modCounter;
	
	public OrderedList() {
		head = null;
		modCounter = 0;
		currentSize = 0;
	}
	
	public void insert(E obj) {
		Node<E> newNode = new Node<E>(obj);
		Node<E> prev = null, curr = head;
		//Goes through linked list to find ordered insertion point
		while(curr != null && obj.compareTo(curr.data) >= 0) {
			prev = curr;
			curr = curr.next;
		}
		//Insert new node in first position/head
		if(prev == null) {
			newNode.next = head;
			head = newNode;
		}
		//Insert in between nodes or at end of linked list
		else {
			prev.next = newNode;
			newNode.next = curr;
		}
		modCounter++;
		currentSize++;
	}
	
	public E removeMin() {
		if(isEmpty())
			return null;
		E min = head.data;
		head = head.next;
		modCounter++;
		--currentSize;
		return min;
	}
	
	public E remove(E obj) {
		Node<E> prev = null, curr = head;
		//Goes through linked list comparing objects
		while(curr != null && obj.compareTo(curr.data) != 0) {
			prev = curr;
			curr = curr.next;
		}
		//Object not found / Empty linked list
		if(curr == null)
			return null;
		//Object at head of linked list
		if(curr == head) {
			head = head.next;
		}
		//Object at end of linked list or in between nodes
		else {
			prev.next = curr.next;
		}
		modCounter++;
		--currentSize;
		return curr.data;
	}
	
	public E findMin() {
		if(isEmpty())
			return null;
		return head.data;
	}
	
	public boolean find(E obj) {
		Node<E> prev = null, curr = head;
		// Goes through linked list comparing objects
		while(curr != null && obj.compareTo(curr.data) != 0) {
			prev = curr;
			curr = curr.next;
		}
		//Object not found in list
		if(curr == null)
			return false;
		else
		//Object found in list
			return true;
	} 
	
	public void makeEmpty() {
		head = null;
		currentSize = 0;
		modCounter = 0;
	}
	
	public boolean isEmpty() {
		return currentSize == 0;
	}
	
	public int listSize() {
		return currentSize;
	}
	
	public Iterator<E> iterator() {
		return new IteratorHelper();
	}
	
	class IteratorHelper implements Iterator<E> {
		Node<E> iterPtr;
		long modCheck;
		
		public IteratorHelper() {
			iterPtr = head;
			modCheck = modCounter;
		}
		
		public boolean hasNext() {
			if(modCheck != modCounter)
				throw new ConcurrentModificationException();
			return iterPtr != null;
		}
		
		public E next() {
			if(!hasNext())
				throw new NoSuchElementException();
			E tmp = iterPtr.data;
			iterPtr = iterPtr.next;
			return tmp;
		}
		
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
}
