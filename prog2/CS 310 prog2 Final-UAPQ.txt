/*
   Daniel Hernandez
   cssc0928
*/

package data_structures;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.ConcurrentModificationException;

public class UnorderedArrayPriorityQueue<E extends Comparable<E>> implements PriorityQueue<E> {
	private int currentSize, maxSize;
	private long modCounter;
	private E[] array;
	
   	public UnorderedArrayPriorityQueue(int max) {
		currentSize = 0;
		maxSize = max;
		modCounter = 0;
		array = (E[]) new Comparable[maxSize];
	}
	
	public UnorderedArrayPriorityQueue() {
		this(DEFAULT_MAX_CAPACITY);
	}
	
	public boolean insert(E obj) {
		if(isFull())
			return false;
		array[currentSize] = obj;
		currentSize++;
		modCounter++;
		return true;
	}
	
	public E remove() {
		if(isEmpty())
			return null;
		E highestPriorityItem;
		int priorityIndex = 0;
		//Goes through array to comparing objects
		for(int i = 1; i < currentSize; i++) {
			if(array[i].compareTo(array[priorityIndex]) < 0)
				priorityIndex = i;
		}
		highestPriorityItem = array[priorityIndex];
		//Shift array left from the removed object
		for(int i = priorityIndex; i < currentSize-1; i++)
			array[i] = array[i + 1];
		currentSize--;
		modCounter++;
		return highestPriorityItem;
	}
	
	public E peek() {
		if(isEmpty())
			return null;
		E bestSoFar = array[0];
		for(int i = 1; i < currentSize; i++) {
			if(array[i].compareTo(bestSoFar) < 0)
				bestSoFar = array[i];
		}
		return bestSoFar;
	}
	
	public boolean contains(E obj) {
		if(isEmpty())
			return false;
		for(int i = 0; i < currentSize; i++) {
			if(obj.compareTo(array[i]) == 0)
				return true;
		}
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
} 
