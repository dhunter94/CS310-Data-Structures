/*  Daniel Hernandez
    cssc0928
*/

package data_structures;

import java.lang.RuntimeException;
import java.util.NoSuchElementException;	//Additional exception for Iterator
import java.util.Iterator;

public class ArrayLinearList<E> implements LinearListADT<E> {
	private E[] array;
	private int currentSize;
	private int maxCapacity;
	
	public ArrayLinearList() {
		array = (E[]) new Object[DEFAULT_MAX_CAPACITY + 1];
		currentSize = 0;
		maxCapacity = DEFAULT_MAX_CAPACITY;
	}
	
	public void addLast(E obj) {
		insert(obj, currentSize + 1);
	}

	public void addFirst(E obj) {
		insert(obj, 1);
	}

	public void insert(E obj, int location) {
		//Checks if array needs to be larger
		if(currentSize == maxCapacity)
			doubleArraySize();
		//Checks if list doesn't start at index[0]
		if(location == 0)
			throw new RuntimeException("List cannot start at 0.\n");
		//Checks if list is contiguous
		else if(location > size() + 1)	
			throw new RuntimeException("List elements must be 
				contiguous.\n");
		else if(array[location] == null) {
			array[location] = obj;
			currentSize++;
		}
		else {
			//Shift elements to the right
			for(int i = currentSize; i >= location; i--)
				array[i + 1] = array[i];
			array[location] = obj;
			currentSize++;
		}
	}
	
	public E remove(int location) {
		E obj = null;
		//Checks if location is at 0 or if no object is present
		if(location == 0 || array[location] == null)
			throw new RuntimeException("Location does not map to a valid 
				position within the list.\n");
		obj = array[location];
		//Shift elements to the left
		for(int i = location; i < currentSize; i++)
			array[i] = array[i + 1];
		currentSize--;
		//Checks if array is less that 25% full
		if (currentSize < maxCapacity * .25)
			halveArraySize();
		return obj;
	}

	public E remove(E obj) {
		if(isEmpty())
			return null;
		else {
			for(int i = 1; i <= currentSize; i++){
				//Compares if two objects are the same
				if(((Comparable<E>)obj).compareTo(array[i]) == 0) {
					remove(i);
					return obj;
				}
			}
		}
		return null;	
	}

	public E removeFirst() {
		if(isEmpty())
			return null;
		return remove(1);
	}

	public E removeLast() {
		if(isEmpty())
			return null;
		return remove(currentSize);
	}
	
	public E get(int location) {
		//Checks to see if location is not 0 or valid
		if(location == 0 || array[location] == null)
			throw new RuntimeException("Location does not map to a valid 
				position within the list.\n");
		return array[location];
	}
	
	public boolean contains(E obj) {
		for(int i = 1; i <= currentSize; i++){
			//Compares if two objects are the same
			if(((Comparable<E>)obj).compareTo(array[i]) == 0)
				return true;
		}
		return false;
	}

	public int locate(E obj) {
		for(int i = 1; i <= currentSize; i++){
			//Compares if two objects are the same
			if(((Comparable<E>)obj).compareTo(array[i]) == 0)
				return i;
		}
		return -1;
	}

	public void clear() {
		currentSize = 0;
	}

	public boolean isEmpty() {
		if(currentSize == 0)
			return true;
		else
			return false;
	}

	public int size() {
		return currentSize;
	}

	public Iterator<E> iterator() {
		return new IteratorHelper();
	}
	
	class IteratorHelper implements Iterator<E> {
		int iterIndex;
		
		public IteratorHelper() {
			iterIndex = 1;
		}
		
		public boolean hasNext() {
			return iterIndex <= currentSize;
		}
		
		public E next() {
			if(!hasNext())
				throw new NoSuchElementException();
			return array[iterIndex++];
		}
		
		public void remove(){
			throw new UnsupportedOperationException();
		}
	}
	
	private void doubleArraySize() {
		maxCapacity *= 2;
		E[] tempArray = (E[]) new Object[maxCapacity + 1];
		//Copy all elemenets starting at index[1]
		System.arraycopy(array, 1, tempArray, 1, currentSize);
		array = tempArray;
	}
	
	private void halveArraySize(){
		maxCapacity /= 2;
		E[] tempArray = (E[]) new Object[maxCapacity + 1];
		//Copy all elemenets starting at index[1]
		System.arraycopy(array, 1, tempArray, 1, currentSize);
		array = tempArray;
	}
}
