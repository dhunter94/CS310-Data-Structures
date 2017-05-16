/*
   Daniel Hernandez
   cssc0928
*/

package data_structures;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.ConcurrentModificationException;

public class HashTable<K extends Comparable<K>,V> implements DictionaryADT<K,V>{

	private int currentSize, maxSize, tableSize;
	private long modCounter;
	private UnorderedList<DictionaryNode<K,V>>[] list;
	
	public HashTable(int n){
		maxSize = n;
		currentSize = 0;
		tableSize = (int) (maxSize * 1.3f);
		modCounter = 0;
		list = new UnorderedList[tableSize];
		//Initialize array of lists
		for(int i = 0; i < tableSize; i++)
			list[i] = new UnorderedList<DictionaryNode<K,V>>();
	}
	
	public boolean contains(K key) {
		return list[getIndex(key)].find(new DictionaryNode<K,V>(key,null));
	}

	public boolean add(K key, V value) {
		if(isFull())
			return false;
		//Check for duplicates
		if(list[getIndex(key)].find(new DictionaryNode<K,V>(key,null)))
			return false;
		list[getIndex(key)].addLast(new DictionaryNode<K,V>(key,value));
		currentSize++;
		modCounter++;
		return true;
	}

	public boolean delete(K key) {
		if(list[getIndex(key)].find(new DictionaryNode<K,V>(key,null))) {
			list[getIndex(key)].remove(new DictionaryNode<K,V>(key,null));
			currentSize--;
			modCounter++;
			return true;
		}
		//Key not found
		else
			return false;
	}

	public V getValue(K key) {
		DictionaryNode<K,V> tmp = list[getIndex(key)].get(new DictionaryNode<K,V>(key,null));
		//Key no found
		if(tmp == null)
			return null;
		return tmp.value;
	}

	public K getKey(V value) {
		//Iterate through each list, in array, to find value
		for(int i = 0; i < tableSize; i++) {
			for(DictionaryNode<K,V> node : list[i]) {
				if(((Comparable<V>) node.value).compareTo(value) == 0)
					return node.key;
			}
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
		for(int i = 0; i < tableSize; i++)
			list[i].makeEmpty();
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
	
	private int getIndex(K key) {
		return (key.hashCode() & 0x7FFFFFFF) % tableSize;
	}
	
	abstract class IteratorHelper<E> implements Iterator<E> {
		protected DictionaryNode<K,V> [] nodes;
		protected int idx;
		protected long modCheck;
		
		public IteratorHelper() {
			nodes = new DictionaryNode[currentSize];
			idx = 0;
			int j = 0;
			modCheck = modCounter;
			//Fill temp nodes array to sort our HashTable
			for(int i = 0; i < tableSize; i++) {
				for(DictionaryNode<K,V> n : list[i])
					nodes[j++] = n;
			}
			nodes = shellSort(nodes);
		}
		
		public boolean hasNext() {
			if(modCheck != modCounter)
				throw new ConcurrentModificationException();
		return idx < currentSize;
		}
		
		public abstract E next();
		
		public void remove() {
			throw new UnsupportedOperationException();
		}
		
		private DictionaryNode<K,V>[] shellSort(DictionaryNode<K,V>[] arr) {
			DictionaryNode<K,V> tmp = null;
			int in, out, h = 1;
			int size = arr.length;
			while(h <= size/3)
				h = h*3+1;
			while(h > 0) {
				for(out = h; out < size; out++) {
					tmp = arr[out];
					in = out;
					while(in > h-1 && (arr[in - h].compareTo(tmp) > 0 || arr[in - h].compareTo(tmp) == 0)) {
						arr[in] = arr[in - h];
						in -= h;
					}
					arr[in] = tmp;
				}
				h = (h - 1) / 3;
			}
			return arr;
		}
	}
	
	class KeyIteratorHelper<K> extends IteratorHelper<K> {
		public KeyIteratorHelper() {
			super();
		}
		public K next() {
			if(!hasNext())
				throw new NoSuchElementException();
			return (K) nodes[idx++].key;
		}
	}
	
	class ValueIteratorHelper<V> extends IteratorHelper<V> {
		public ValueIteratorHelper() {
			super();
		}
		
		public V next() {
			if(!hasNext())
				throw new NoSuchElementException();
			return (V) nodes[idx++].value;
		}
	}
	
	private class DictionaryNode<K,V> implements Comparable<DictionaryNode<K,V>> {
		K key;
		V value;
		
		public DictionaryNode(K k, V v) {
			key = k;
			value = v;
		}
		
		public int compareTo(DictionaryNode<K,V> node) {
			return ((Comparable<K>) key).compareTo((K)node.key);
		}
	}
}
