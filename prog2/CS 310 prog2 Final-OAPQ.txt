/*
   Daniel Hernandez
   cssc0928
*/

package data_structures;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.ConcurrentModificationException;

public class OrderedArrayPriorityQueue<E extends Comparable<E>> implements PriorityQueue<E> {
	private int currentSize, maxSize;
	private long modCounter;
	private E[] array;
	
   	public OrderedArrayPriorityQueue(int max) {
		currentSize = 0;
		maxSize = max;
		modCounter = 0;
		array = (E[]) new Comparable[maxSize];
	}
	
	public OrderedArrayPriorityQueue() {
		this(DEFAULT_MAX_CAPACITY);
	}
	
	public boolean insert(E obj) {
		if(isFull())
			return false;
		int insertionPoint = findInsertionPoint(obj, 0, currentSize-1);
		//Shift array right from new point
		for(int i = currentSize - 1; i >= insertionPoint; i--)
			array[i+1] = array[i];
		//Insert new object
		array[insertionPoint] = obj;
		currentSize++;
		modCounter++;
		return true;
	}
	
	public E remove() {
		if(isEmpty())
			return null;
		modCounter++;
		return array[--currentSize];
	}
	
	public E peek() {
		if(isEmpty())
			return null;
		return array[currentSize-1];
	}
	
	public boolean contains(E obj) {
		if(isEmpty())
			return false;
		int c = binSearch(obj, 0, currentSize-1);
		if(c != -1)
			return true;
		else
			return false;
	}
	
	public int size() {
		return currentSize;
	}
	
	public void clear() {
		currentSize = 0;
		modCounter = 0;
	}
	
	public boolean isEmpty() {
		return currentSize == 0;
	}
	
	public boolean isFull() {
		return currentSize == maxSize;
	}
	
	public Iterator<E> iterator() {
		return new IteratorHelper();
	}
	
	class IteratorHelper implements Iterator<E> {
		int iterIndex;
		long modCheck;
		
		public IteratorHelper() {
			iterIndex = 0;
			modCheck = modCounter;
		}
		
		public boolean hasNext() {
			if(modCheck != modCounter)
				throw new ConcurrentModificationException();
			return iterIndex < currentSize;
		}
		
		public E next() {
			if(!hasNext())
				throw new NoSuchElementException();
			return array[iterIndex++];
		}
		
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
	
	private int findInsertionPoint(E obj, int lo, int hi) {
		if(hi < lo)
			return lo;
		int mid = (lo + hi) >> 1;
		if(obj.compareTo(array[mid]) >= 0)
			return findInsertionPoint(obj, lo, mid - 1);
		return findInsertionPoint(obj, mid + 1, hi);
	}
	
	private int binSearch(E obj, int lo, int hi) {
		if(hi < lo) return -1;
		int mid = (lo + hi) >> 1;
		int c = obj.compareTo(array[mid]);
		if(c == 0) 
			return mid;
		if(c >  0)
			return binSearch(obj, lo, mid-1);
		return binSearch(obj, mid+1, hi);
	}
}
