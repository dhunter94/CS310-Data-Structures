/*
   Daniel Hernandez
   cssc0928
*/

package data_structures;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.ConcurrentModificationException;

public class BinarySearchTree<K extends Comparable<K>, V> implements DictionaryADT<K, V> {
	
	private int currentSize, modCounter;
	private Node<K,V> root;
	private K foundKey;
	
	public BinarySearchTree() {
		currentSize = 0;
		modCounter = 0;
		root = null;
	}
	
	public boolean contains(K key) {
		if(find(key,root) == null)
			return false;
		return true;
	}

	
	public boolean add(K key, V value) {
		//Check for duplicates
		if(contains(key))
			return false;
		//Check to see if it's the first entry
		if(root == null)
			root = new Node<K,V>(key,value);
		else
			insert(key,value,root,null,false);
		currentSize++;
		modCounter++;
		return true;
	}

	
	public boolean delete(K key) {
		if(isEmpty())
			return false;
		//Not found condition
		if(!remove(key,root,null,false))
			return false;
		currentSize--;
		modCounter++;
		return true;
	}

	
	public V getValue(K key) {
		return findValue(key, root);
	}

	
	public K getKey(V value) {
		foundKey = null;
		findKey(root,value);
		return foundKey;
	}

	
	public int size() {
		return currentSize;
	}

	
	public boolean isFull() {
		return false;
	}

	
	public boolean isEmpty() {
		return currentSize == 0;
	}

	
	public void clear() {
		currentSize = 0;
		modCounter = 0;
		root = null;
	}

	
	public Iterator<K> keys() {
		return new KeyIteratorHelper<K>();
	}

	
	public Iterator<V> values() {
		return new ValueIteratorHelper<V>();
	}

//////////////////////////////////////////////////////////////////////////////////////////////
	
	private K find(K key, Node<K,V> node) {
		if(node == null)
			return null;
		//Go left
		if(key.compareTo(node.key) < 0)
			return find(key, node.leftChild);
		//Go right
		else if(key.compareTo(node.key) > 0)
			return find(key, node.rightChild);
		else
		//Found key
			return node.key;
	}
	
	private V findValue(K key, Node<K,V> node) {
		if(node == null)
			return null;
		//Go left
		if(key.compareTo(node.key) < 0)
			return findValue(key, node.leftChild);
		//Go right
		else if(key.compareTo(node.key) > 0)
			return findValue(key, node.rightChild);
		else
		//Found value
			return node.value;
	}
	
	private void findKey(Node<K,V> n, V value) {
		if(n == null) return;
		findKey(n.leftChild, value);
		if(((Comparable<V>)value).compareTo(n.value) == 0)
			foundKey = n.key;
		findKey(n.rightChild, value);
	}
	
	private void insert(K k, V v, Node<K,V> n, Node<K,V> parent, boolean wasLeft) {
		if(n == null) {
			if(wasLeft)
				parent.leftChild = new Node<K,V>(k,v);
			else
				parent.rightChild = new Node<K,V>(k,v);
		}
		else if(k.compareTo(n.key) < 0)
			insert(k,v,n.leftChild,n,true);
		else
			insert(k,v,n.rightChild,n,false);
	}
	
	private boolean remove(K k, Node<K,V> n, Node<K,V> parent, boolean wasLeft) {
		if(n == null)
			return false;
		
		Node<K,V> successor;
		
		//Go left
		if(k.compareTo(n.key) < 0)
			return remove(k,n.leftChild,n,true);
		//Go right
		else if(k.compareTo(n.key) > 0)
			return remove(k,n.rightChild,n,false);
		//Found node with key
		else {
			//Node with no children
			if(n.leftChild == null && n.rightChild == null) {
				if(n == root)
					root = null;
				else {
					if(wasLeft)
						parent.leftChild = null;
					else
						parent.rightChild = null;
				}
			}
			
			//Node with left child
			else if(n.leftChild != null && n.rightChild == null) {
				if(n == root)
					root = n.leftChild;
				else {
					if(wasLeft)
						parent.leftChild = n.leftChild;
					else
						parent.rightChild = n.leftChild;
				}
			}
			
			//Node with right child
			else if(n.rightChild != null && n.leftChild == null) {
				if(n == root)
					root = n.rightChild;
				else {
					if(wasLeft)
						parent.leftChild = n.rightChild;
					else
						parent.rightChild = n.rightChild;
				}
			}
			
			//Node with two children
			else {
				//Inorder successor has no left child
				if(n.rightChild.leftChild == null) {
					successor = n.rightChild;
					successor.leftChild = n.leftChild;
					if(n == root)
						root = successor;
					else {
						if(wasLeft)
							parent.leftChild = successor;
						else
							parent.rightChild = successor;
					}
				}
				//Inorder successor has a left child
				else {
					successor = getSuccessor(n.rightChild,null);
					n.key = successor.key;
					n.value = successor.value;
				}
			}
				
		}
		
		return true;
	}
			
	
	private Node<K,V> getSuccessor(Node<K,V> n, Node<K,V> prev) {
		if(n.leftChild == null) {
			if(n.rightChild == null)
				prev.leftChild = null;
			else
				prev.leftChild = n.rightChild;
			return n;
		}
		return getSuccessor(n.leftChild,n);
	}
	
	abstract class IteratorHelper<E> implements Iterator<E> {
		protected int iterIdx, idx;
		protected int modCount;
		Node<K,V>[] arr;
		
		public IteratorHelper() {
			iterIdx = 0;
			idx = 0;
			modCount = modCounter;
			//Array with all key/value pairs
			arr = new Node[currentSize];
			inorderFillArray(root);
		}
		
		public boolean hasNext() {
			if(modCount != modCounter)
				throw new ConcurrentModificationException();
			return iterIdx < currentSize;
		}
		
		public void remove() {
			throw new UnsupportedOperationException();
		}
		
		private void inorderFillArray(Node<K,V> n) {
			if(n != null) {
				inorderFillArray(n.leftChild);
				arr[idx++] = n;
				inorderFillArray(n.rightChild);
			}
		}
	}
	
	class KeyIteratorHelper<K> extends IteratorHelper<K> {
		public KeyIteratorHelper() {
			super();
		}
		public K next() {
			if(!hasNext())
				throw new NoSuchElementException();
			return (K) arr[iterIdx++].key;
		}
	}
	
	class ValueIteratorHelper<V> extends IteratorHelper<V> {
		public ValueIteratorHelper() {
			super();
		}
		
		public V next() {
			if(!hasNext())
				throw new NoSuchElementException();
			return (V) arr[iterIdx++].value;
		}
	}
	
	private class Node<K,V> {
		K key;
		V value;
		Node<K,V> leftChild;
		Node<K,V> rightChild;
		
		public Node(K k, V v) {
			key = k;
			value = v;
			leftChild = rightChild = null;
		}
	}
	
}
