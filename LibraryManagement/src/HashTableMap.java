// --== CS400 File Header Information ==--
// Name: Ruoran Huang
// Email: rhuang93@wisc.edu
// Notes to Grader: <optional extra notes>

import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * This class implements a hash table, which maps keys to values.
 * 
 * @author huangruoran
 *
 * @param <KeyType>
 * @param <ValueType>
 */
public class HashTableMap<KeyType, ValueType> implements MapADT<KeyType, ValueType> {

  private static int capacity; // The total number of entries in the hash table.
  private static final int defaultCapacity = 10; // default load capacity of the hash table.
  public static final double maxLoadFactor = 0.85; // max load capacity of the hash table

  @SuppressWarnings("rawtypes")
  private static LinkedList[] table; // The table to store key-value pairs

  /**
   * create a new hash table with default capacity = 10
   */
  public HashTableMap() {
    this(defaultCapacity);
  }

  /**
   * Constructs a new empty hash table with the specified capacity
   * 
   * @param capacity the capacity of the hash table
   */
  @SuppressWarnings("rawtypes")
  public HashTableMap(int capacity) throws IllegalArgumentException {
    if (capacity <= 0)
      throw new IllegalArgumentException("Invalid Capacity: " + capacity);
    this.capacity = capacity;
    this.table = new LinkedList[capacity];
    // set each entry to a linked list
    for (int i = 0; i < capacity; i++) {
      this.table[i] = new LinkedList();
    }
  }


  /**
   * This class is used to store key-value pairs in LinkedList structure instead of single object.
   * 
   * @author huangruoran
   *
   * @param <KeyType>
   * @param <ValueType>
   */
  @SuppressWarnings("hiding")
  class pair<KeyType, ValueType> {

    public KeyType key;
    public ValueType value;

    // store key and value in a pair
    public pair(KeyType key, ValueType value) {
      this.key = key;
      this.value = value;
    }

    // Obtain the key in this key-value pair
    public KeyType getKey() {
      return key;
    }

    // Obtain the value in this key-value pair
    public ValueType getValue() {
      return value;
    }
  }

  /**
   * This method grows the hash table by doubling and rehasing
   */
  @SuppressWarnings({"rawtypes", "unchecked"})
  private void rehash() {
    capacity = capacity * 2; // double the capacity of the table
    LinkedList[] temp = table;
    this.table = new LinkedList[capacity];
    for (int i = 0; i < capacity; i++) {
      // set each entry of new table to linked list
      table[i] = new LinkedList();
    }

    for (int i = 0; i < temp.length; i++) {
      for (int j = 0; j < temp[i].size(); j++) {
        // save the key-value pair in that location
        pair<KeyType, ValueType> pair = (pair<KeyType, ValueType>) temp[i].get(j);
        KeyType key = pair.getKey();
        ValueType value = pair.getValue();
        put(key, value); // put the key-value pair to new table
      }

    }
  }

  //this method is used to iterate the whole hashtable
  public LinkedList iterator() {
      LinkedList<Book> result = new LinkedList();
      for (int i = 0; i < capacity; i++) {
        for (int j = 0; j < table[i].size(); j++) {
            pair<String, Book> bookPair = (pair<String, Book>) table[i].get(j);
            Book book = bookPair.getValue();
            result.add(book);
        }
      }
      return result;
  }

  /**
   * Store new key-value pair into table, if table already contains the pair, return false,
   * otherwise store the new value and return true.
   * 
   * @param key
   * @param value
   * @return boolean
   */
  @SuppressWarnings({"unchecked"})
  @Override
  public boolean put(KeyType key, ValueType value) {
    int hashCode = Math.abs(key.hashCode()); // Get the hashcode of the key
    int index = hashCode % capacity;// Get the index in the hash table
    // create a new pair of key-value
    pair<KeyType, ValueType> pair = new pair<KeyType, ValueType>(key, value);

    if (containsKey(key) || key == null) {
      // return false when table already contains the pair
      return false;
    } else {
      // add pair to the table otherwise
      table[index].add(pair);
    }
    // check if load capacity is greater than or equal to 85%, if so, grow the table
    if ((size() * 1.0) / capacity >= 0.85) {
      rehash();
    }
    return true;
  }

  /**
   * this method returns the paired value with the given key, if not, throw exception
   * 
   * @param key
   * @return the corresponding value
   */
  @SuppressWarnings("unchecked")
  @Override
  public ValueType get(KeyType key) throws NoSuchElementException {
    // the table doesn't contain the key, then throw an NoSuchElementException
    if (!containsKey(key))
      throw new NoSuchElementException();

    // getting the hashcode and corresponding index
    int hashCode = Math.abs(key.hashCode());
    int index = (hashCode % capacity);

    // save the key-value pair at the index
    pair<KeyType, ValueType> pair = null;
    // = (pair<KeyType, ValueType>) this.table[index].get(0);
    for (int i = 0; i < this.table[index].size(); i++) {
      // save the pair with the given key
      pair = (pair<KeyType, ValueType>) table[index].get(i);

      // break out the loop if we find the pair with the given key
      if (pair.getKey().equals(key)) {
        break;
      }
    }

    return (ValueType) pair.getValue();
  }

  /**
   * this method return the total number of current key-value pairs in the hash table
   * 
   * @return size -- total number of pairs
   */
  @Override
  public int size() {
    int total = 0;
    // add up the number of key-value pair at each entry
    for (int i = 0; i < capacity; i++) {
      total += table[i].size();
    }
    return total;
  }

  /**
   * this method checks if the hash table contains the given key
   * 
   * @return boolean return true if contains, otherwise returns false
   */
  @SuppressWarnings("unchecked")
  @Override
  public boolean containsKey(KeyType key) {
    // getting the hashcode and corresponding index
    int hashCode = Math.abs(key.hashCode());
    int index = hashCode % capacity;

    for (int i = 0; i < table[index].size(); i++) {
      // save the pair at the corresponding index
      pair<KeyType, ValueType> pair = (pair<KeyType, ValueType>) table[index].get(i);
      if (pair.getKey().equals(key)) {
        return true;
      }
    }
    return false;
  }

  /**
   * removes the key-value pair with the given key
   * 
   * @return the corresponding value
   */
  @SuppressWarnings({"unchecked", "unused"})
  @Override
  public ValueType remove(KeyType key) {
    // getting the hashcode and corresponding index
    int hashCode = Math.abs(key.hashCode());
    int index = hashCode % capacity;

    if (containsKey(key)) {
      for (int i = 0; i < table[index].size(); i++) {
        /// save the pair at the corresponding index
        pair<KeyType, ValueType> pair = (pair<KeyType, ValueType>) table[index].get(i);
        if (pair.getKey().equals(key)) {
          // remove the key-value pair from hash table at the given key index
          table[index].remove(i);
        }
        return (ValueType) pair.getValue();
      }
    }
    return null;
  }

  /**
   * this method clears all key-value pairs from the hash table
   */
  @Override
  public void clear() {
    // clear every linked list for each entry
    for (int i = 0; i < capacity; i++) {
      table[i].clear();
    }

  }

}
