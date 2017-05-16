/*
   Daniel Hernandez
   cssc0928
*/

import java.util.Iterator;
import data_structures.*;

public class LatinDictionary {
	
    private DictionaryADT<String,String> dictionary;

    public LatinDictionary() {
    	dictionary = new HashTable<String,String>(8320);
    }

    public void loadDictionary(String fileName) {      
    	DictionaryEntry[] entries = DictionaryReader.getDictionaryArray(fileName);
      
    	for(int i = 0; i < entries.length; i++) {
    		if(entries[i] != null)
    			dictionary.add(entries[i].getKey(), entries[i].getValue());
    	}
    }

    public boolean insertWord(String word, String definition) {
	return dictionary.add(word,definition);
    }

    public boolean deleteWord(String word) {
	return dictionary.delete(word);  
    }

    public String getDefinition(String word) {
	return dictionary.getValue(word);
    }

    public boolean containsWord(String word) {
	return dictionary.contains(word);
    }
    
    public String[] getRange(String start, String finish) {
	Iterator<String> keys = dictionary.keys();
        UnorderedList<String> words = new UnorderedList<String>();
        String []  arr;
        int i = 0;
	//Iterate through keys and compare values
        while(keys.hasNext()) {
        	String word = keys.next();
        	if(word.compareTo(start) >= 0 && word.compareTo(finish) <= 0) {
        		words.addLast(word);
        	}
        }
	//Add matching keys to array
        arr = new String[words.listSize()];
        for(String w : words) {
        	arr[i++] = w;
        }
        return arr;
    }
            
    public Iterator<String> words() {
	return dictionary.keys();
    }

    public Iterator<String> definitions() {
	return dictionary.values();
    }
    
}
