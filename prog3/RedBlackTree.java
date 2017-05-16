/*
   Daniel Hernandez
   cssc0928
*/

package data_structures;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class RedBlackTree<K extends Comparable<K>, V> implements DictionaryADT<K,V>{

	private TreeMap<K,V> map;
	
	public RedBlackTree() {
		map = new TreeMap<K,V>();
	}
	
	public boolean contains(K key) {
		return map.containsKey(key);
	}

	
	public boolean add(K key, V value) {
		//Check for duplicates
		if(contains(key))
			return false;
		else {
			map.put(key,value);
			return true;
		}
	}

	
	public boolean delete(K key) {
		if(contains(key)) {
			map.remove(key);
			return true;
		}
		//Key not found
		else
			return false;
	}

	
	public V getValue(K key) {
		//Key not found or empty map
		if(isEmpty() || map.get(key) == null)
			return null;
		else
			return map.get(key);
	}

	
	public K getKey(V value) {
		//Iterate through our key set to compare values
		for(Map.Entry<K,V> k : map.entrySet()) {
			if(((Comparable<V>)k.getValue()).compareTo(value) == 0)
				return (K) k.getKey();
		}
		return null;
	}

	
	public int size() {
		return map.size();
	}

	
	public boolean isFull() {
		return false;
	}

	
	public boolean isEmpty() {
		return map.isEmpty();
	}

	
	public void clear() {
		map.clear();
	}

	
	public Iterator<K> keys() {
		return map.keySet().iterator();
	}

	
	public Iterator<V> values() {
		return map.values().iterator();
	}

}
