/*
   Daniel Hernandez
   cssc0928
*/

package data_structures;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.ConcurrentModificationException;

public class UnorderedList<E extends Comparable<E>> implements Iterable<E> {
	private class Node<T> {
		T data;
		Node<T> next;
		
		public Node(T obj) {
			data = obj;
			next = null;
		}
	}
	
	private Node<E> head, tail;
	private int currentSize;
	private long modCounter;
	
	public UnorderedList() {
		head = tail = null;
		currentSize = 0;
		modCounter = 0;
	}
	
	public void addFirst(E obj) {
		Node<E> newNode = new Node<E>(obj);
		//First object in empty list
		if(isEmpty())
			head = tail = newNode;
		else {
			newNode.next = head;
			head = newNode;
		}
		modCounter++;
		currentSize++;
	}
	
	public void addLast(E obj) {
		Node<E> newNode = new Node<E>(obj);
		//First object in empty list
		if(isEmpty())
			head = tail = newNode;
		else {
			tail.next = newNode;
			tail = newNode;
		}
		modCounter++;
		currentSize++;
	}
	
	public E removeMin() {
		E min = findMin();
		return remove(min);
	}
	
	public E removeMax() {
		E max = findMax();
		return remove(max);
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
		//Object at the head of linked list
		if(curr == head)
			head = head.next;
		//Object at end of linked list
		else if(curr == tail) {
			prev.next = null;
			tail = prev;
		}
		//Object in between nodes
		else
			prev.next = curr.next;
		//Checks to see if linked list is now empty
		if(head == null)
			tail = null;
		modCounter++;
		--currentSize;
		return curr.data;
	}
	
	public E findMin() {
		if(isEmpty())
			return null;
		E min = head.data;
		Node<E> curr = head.next;
		while(curr != null) {
			if(curr.data.compareTo(min) < 0)
				min = curr.data;
			curr = curr.next;
		}
		return min;
	}
	
	public E findMax() {
		if(isEmpty()) 
			return null;
		E max = head.data;
		Node<E> curr = head.next;
		while(curr != null) {
			if(curr.data.compareTo(max) > 0)
				max = curr.data;
			curr = curr.next;
		}
		return max;
	}
	
	public boolean find(E obj) {
		Node<E> tmp = head;
		while(tmp != null) {
			if(obj.compareTo(tmp.data) == 0)
				return true;
			tmp = tmp.next;
		}
		return false;
	}
	
	public E get(E obj){
		Node<E> curr = head;
		while(curr != null && obj.compareTo(curr.data) != 0)
			curr = curr.next;
		if(curr == null)
			return null;
		return curr.data;
	}
	
	public void makeEmpty() {
		head = tail = null;
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
