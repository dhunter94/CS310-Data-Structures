import java.util.Iterator;
import data_structures.*;

public class LatinDictionary {
    private DictionaryADT<String,String> dictionary;

    // constructor takes no arguments.  Size depends on the datafile.
    // creates an instance of the DictionaryADT. Use your HashTable 
    // implementation in this class (though all four should work).
    // Methods that make modifications to the dictionary modify the
    // DictionaryADT object, not the datafile.
    public LatinDictionary() {
        }

    // reads the key=value pairs from the datafile and builds a dictionary structure 
    public void loadDictionary(String fileName) {      
        }

    // inserts a new Latin word and its definition
    public boolean insertWord(String word, String definition) {
        }

    // removes the key value pair that is identified by the key from the dictionary
    public boolean deleteWord(String word) {
        }

    // looks up the definition of the Latin word
    public String getDefinition(String word) {
        }

    // returns true if the Latin word is already in the dictionary
    public boolean containsWord(String word) {
        }
    
    // returns all of the keys in the dictionary within the range start .. finish
    // inclusive, in sorted order. Neither value 'start' or 'finish' need be in the
    // dictionary.  Returns null if there are no keys in the range specified.    
    public String[] getRange(String start, String finish) {
        }
            
    // returns an Iterator of the latin words (the keys) in the dictionary,
    // in sorted order.
    public Iterator<String> words() {
        }

    // returns the definitions in the dictionary, in exactly the same order
    // as the words() Iterator
    public Iterator<String> definitions() {
        }
}   
