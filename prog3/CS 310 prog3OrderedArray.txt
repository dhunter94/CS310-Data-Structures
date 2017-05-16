/*
   Daniel Hernandez
   cssc0928
*/

package data_structures;

import java.util.Iterator;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

public class OrderedArrayDictionary<K extends Comparable<K>, V> implements DictionaryADT<K, V> {
	
	private Node<K,V> [] map;
	private int currentSize, maxSize, modCounter;
	
	public OrderedArrayDictionary(int n) {
		currentSize = 0;
		maxSize = n;
		modCounter = 0;
		map = new Node[maxSize + 1];
	}
	
	public boolean contains(K key) {
		for(int i = 0; i < currentSize; i++) {
			if(key.compareTo(map[i].key) == 0)
				return true;
		}
		return false;
	}
	
	public boolean add(K key, V value) {
		if(isFull())
			return false;
		//Check for duplicate keys
		if(contains(key))
			return false;
		//Find ordered ascending insertion location
		int insertionPoint = findInsertionPoint(key,0,currentSize-1);
		//Adds key/value pairs in ascending order
		for(int i = currentSize-1; i >= insertionPoint; i--)
			map[i+1] = map[i];
		map[insertionPoint] = new Node<K,V>(key,value);
		currentSize++;
		modCounter++;
		return true;
	}
	
	public boolean delete(K key) {
		if(isEmpty())
			return false;
		int idx = findIdx(key,0,currentSize-1);
		//Key not found
		if(idx == -1)
			return false;
		else {
			//Shift array left to delete key/value pair
			for(int i = idx; i < currentSize; i++)
				map[i] = map[i+1];
			currentSize--;
			modCounter++;
			return true;
		}
	}

	public V getValue(K key) {
		if(isEmpty())
			return null;
		for(int i = 0; i < currentSize; i++) {
			if(key.compareTo(map[i].key) == 0)
				return map[i].value;
		}
		return null;
	}

	public K getKey(V value) {
		for(int i = 0; i < currentSize; i++) {
			if(((Comparable<V>)value).compareTo(map[i].value) == 0)
				return map[i].key;
		}
		return null;
	}

	public int size() {
		return currentSize;
	}

	public boolean isFull() {
		return currentSize == maxSize;
	}

	public boolean isEmpty() {
		return currentSize == 0;
	}
	
	public void clear() {
		currentSize = 0;
		modCounter = 0;
	}
	
	public Iterator<K> keys() {
		return new KeyIteratorHelper<K>();
	}
	
	public Iterator<V> values() {
		return new ValueIteratorHelper<V>();
	}
	
//////////////////////////////////////////////////////////////////////////////////////////////
	
	//Binary search
	private int findInsertionPoint(K k, int lo, int hi) {
		if(hi < lo)
			return lo;
		int mid = (lo + hi) >> 1;
		if(k.compareTo(map[mid].key) <= 0)
			return findInsertionPoint(k, lo, mid - 1);
		return findInsertionPoint(k, mid + 1, hi);
	}
	
	//Find index through binary search
	private int findIdx(K k, int lo, int hi) {
		if(hi < lo)
			return -1;
		int mid = (lo + hi) >> 1;
		int c = k.compareTo(map[mid].key);
		if(c == 0)
			return mid;
		if(c < 0)
			return findIdx(k,lo,mid-1);
		return findIdx(k,mid+1,hi);
	}
	
	abstract class IteratorHelper<E> implements Iterator<E> {
		protected int iterIdx;
		protected long modCheck;
		
		public IteratorHelper() {
			iterIdx = 0;
			modCheck = modCounter;
		}
		
		public boolean hasNext() {
			if(modCheck != modCounter)
				throw new ConcurrentModificationException();
		return iterIdx < currentSize;
		}
		
		public abstract E next();
		
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
	
	class KeyIteratorHelper<K> extends IteratorHelper<K> {
		public KeyIteratorHelper() {
			super();
		}
		public K next() {
			if(!hasNext())
				throw new NoSuchElementException();
			return (K) map[iterIdx++].key;
		}
	}
	
	class ValueIteratorHelper<V> extends IteratorHelper<V> {
		public ValueIteratorHelper() {
			super();
		}
		
		public V next() {
			if(!hasNext())
				throw new NoSuchElementException();
			return (V) map[iterIdx++].value;
		}
	}
	
	private class Node<K,V> {
		K key;
		V value;
		
		public Node(K k, V v){
			key = k;
			value = v;
		}
	}
}
