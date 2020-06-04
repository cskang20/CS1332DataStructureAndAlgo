import java.util.List;
import java.util.Set;

/**
 * Your implementation of HashMap.
 *
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class HashMap<K, V> {

    // DO NOT MODIFY OR ADD NEW GLOBAL/INSTANCE VARIABLES
    public static final int INITIAL_CAPACITY = 13;
    public static final double MAX_LOAD_FACTOR = 0.67;
    private MapEntry<K, V>[] table;
    private int size;

    /**
     * Creates a hash map with no entries. The backing array should have an
     * initial capacity of INITIAL_CAPACITY.
     * <p>
     * Use constructor chaining.
     */
    public HashMap() {
        table = (MapEntry<K, V>[]) new MapEntry[INITIAL_CAPACITY];

    }

    /**
     * Creates a hash map with no entries. The backing array should have an
     * initial capacity of the initialCapacity parameter.
     * <p>
     * You may assume the initialCapacity parameter will always be positive.
     *
     * @param initialCapacity initial capacity of the backing array
     */
    public HashMap(int initialCapacity) {
        table = (MapEntry<K, V>[]) new MapEntry[initialCapacity];
        size = 0;

    }

    /**
     * Adds the given key-value pair to the HashMap.
     * <p>
     * In the case of a collision, use linear probing as your resolution
     * strategy.
     * <p>
     * If you find a duplicate key, then replace the entry's value with the new
     * one passed in and return the old value.
     * <p>
     * At the start of the method, you should check to see if the array would
     * violate the max load factor after adding the data (regardless of
     * duplicates). For example, let's say the array is of length 5 and the
     * current size is 3 (LF = 0.6). For this example, assume that no elements
     * are removed in between steps. If another entry is attempted to be added,
     * before doing anything else, you should check whether (3 + 1) / 5 = 0.8
     * is larger than the max LF. It is, so you would trigger a resize before
     * you even attempt to add the data or figure out if it's a duplicate. As a
     * warning, be careful about using integer division in the LF calculation!
     * <p>
     * When regrowing, resize the length of the backing table to
     * 2 * old length + 1. You must use the resizeBackingTable method to do so.
     * <p>
     * Return null if the key was not already in the map. If it was in the map,
     * return the old value associated with it.
     *
     * @param key   key to add into the HashMap
     * @param value value to add into the HashMap
     * @return null if the key was not already in the map. If it was in the
     * map, return the old value associated with it
     * @throws IllegalArgumentException if key or value is null
     */

    public V put(K key, V value) {

        if (key == null || value == null) {
            throw new IllegalArgumentException(
                    "Given Key or Value is null. Cannot Add to HashMap.");
        }

        if (((double) size + 1) / table.length > MAX_LOAD_FACTOR) {
            resizeBackingTable(2 * table.length + 1);
        }

        int index = Math.abs(key.hashCode() % table.length);

        int i = index;

        V returnValue = null;

        do {
            if (table[i] == null || table[i].isRemoved()) {
                MapEntry<K, V> newNode = new MapEntry<K, V>(key, value);
                table[i] = newNode;
                size++;
                return returnValue;
            }

            if (table[i].getKey().equals(key)) {
                returnValue = table[i].getValue();
                table[i].setValue(value);
                return returnValue;
            }

            i = (i + 1) % table.length;

        } while (i != index);

        return returnValue;

    }
//
//        int index = Math.abs(key.hashCode() % table.length);
//        V returnValue = null;
//
//        //index is null
//        if (table[index] == null){
//            MapEntry<K, V> newNode = new MapEntry<K, V>(key, value);
//            table[index] = newNode;
//            size++;
//
//            //Duplicate Key with different value. Return Old value and Set new Value.
//        } else if (table[index] != null && table[index].getKey().equals(key)) {
//            returnValue = table[index].getValue();
//            table[index].setValue(value);
//
//            //Index is already occupied. Need to probe.
//        } else if (table[index] != null && !table[index].getKey().equals(key)) {
//
//            while (table[index] != null) {
//                index = (index + 1) % table.length;
//
//                //Valid and equal Key
//                if (table[index] == null && table[index].getKey().equals(key)) {
//                    returnValue = table[index].getValue();
//                    table[index].setValue(value);
//
//                //Valid and Unequal Key
//                } else if (table[index] == null && !table[index].getKey().equals(key) && !table[index].isRemoved()) {
//                    MapEntry<K, V> newNode = new MapEntry<K, V>(key, value);
//                    table[index] = newNode;
//                    size++;
//                }
//            }
//        }
//        return returnValue;
//    }



    /**
     * Resizes the backing table to the specified length.
     *
     * Disregard the load factor for this method. So, if the passed in length is
     * smaller than the current capacity, and this new length causes the table's
     * load factor to exceed MAX_LOAD_FACTOR, you should still resize the table
     * to the specified length and leave it at that capacity.
     *
     * You should iterate over the old table in order of increasing index and
     * add entries to the new table in the order in which they are traversed.
     *
     * Remember, you cannot just simply copy the entries over to the new array.
     * You will have to rehash all of the entries and add them to the new index
     * of the new table.
     *
     * Also, since resizing the backing table is working with the non-duplicate
     * data already in the table, you shouldn't need to explicitly check for
     * duplicates like the put method does. You also wouldn't want to carry
     * over removed entries either, so you can place the entry in the new table
     * as soon as you find a null spot while probing.
     *
     * @param length new length of the backing table
     * @throws IllegalArgumentException if length is non-positive or less than
     * the number of items in the hash map.
     */

    public void resizeBackingTable(int length) {
        if (length <= 0 || length < size) {
            throw new IllegalArgumentException(
                    "Length of data structure cannot be negative "
                            + "or size of data structure cannot be "
                            + "greater than a length of data structure.");
        }
        MapEntry<K, V>[] newTable = (MapEntry<K, V>[]) new MapEntry[length];

        for (int i = 0; i < table.length; i++) {

            if (table[i] != null && !table[i].isRemoved()) {
                int newIndex = Math.abs(table[i].getKey().hashCode() % newTable.length);

                if (newTable[newIndex] == null) {

                    MapEntry<K, V> newNode = new MapEntry<>(table[i].getKey(), table[i].getValue());
                    newTable[newIndex] = newNode;

                } else {
                    while (newTable[newIndex] != null) {
                        newIndex++;
                        if (newIndex > newTable.length - 1) {
                            newIndex = 0;
                        }
                    }
                    MapEntry<K, V> newNode = new MapEntry<>(table[i].getKey(), table[i].getValue());
                    newTable[newIndex] = newNode;
                }
            }
        }
        table = newTable;
    }

    /**
     * Removes the entry with a matching key from the HashMap.
     *
     * @param key the key to remove
     * @throws IllegalArgumentException if key is null
     * @throws java.util.NoSuchElementException if the key does not exist
     * @return the value previously associated with the key
     */
    public V remove(K key) {

        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null.");
        }

        if (!containsKey(key)) {
            throw new java.util.NoSuchElementException(
                    "Cannot find a matching key value in data structure");

        }

        int index = Math.abs(key.hashCode() % table.length);
        V returnValue = null;
//        boolean end = false;
//
//        for (int i = 0; i < table.length && !end; i ++) {
//            index = (index + i ) % table.length;
//
//            if (table[index] == null) {
//                end = true;
//            } else if (!table[index].isRemoved() && table[index].getKey().equals(key)) {
//                returnValue = table[index].getValue();
//                table[index].setRemoved(true);
//                size--;
//
//            }
//        }

//        V returnValue = null;

        while (!table[index].getKey().equals(key)) {
            index = (index + 1) % table.length;
        }

        if (table[index].getKey().equals(key)) {
            returnValue = table[index].getValue();
            table[index].setRemoved(true);
            size--;
        }

        return returnValue;
//        throw new java.util.NoSuchElementException("");

    }

    /**
     * Gets the value associated with the given key.
     *
     * @param key the key to search for
     * @throws IllegalArgumentException if key is null
     * @throws java.util.NoSuchElementException if the key is not in the map
     * @return the value associated with the given key
     */
    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Cannot find a value associated with null key.");
        }
        int index = Math.abs(key.hashCode() % table.length);


        for (int i = 0; i < table.length && table[index] != null; i ++) {
            index = (index + i ) % table.length;

            if(!table[index].isRemoved() && table[index].getKey().equals(key)) {
                return table[index].getValue();
            }
        }

        throw new java.util.NoSuchElementException("No such element found");
    }

    /**
     * Returns whether or not the key is in the map.
     *
     * @param key the key to search for
     * @throws IllegalArgumentException if key is null
     * @return whether or not the key is in the map
     */
    public boolean containsKey(K key) {
        if (key == null) {
            throw new IllegalArgumentException(
                    "No matching key value found since Key is null.");
        }

        int index = Math.abs(key.hashCode() % table.length);

        while (table[index] != null && index < table.length) {
            if (table[index].getKey().equals(key) && !table[index].isRemoved()) {
                return true;
            }
            index = (index + 1) % table.length;
        }
        return false;
    }



    /**
     * Returns a Set view of the keys contained in this map. The Set view is
     * used instead of a List view because keys are unique in a HashMap, which
     * is a property that elements of Sets also share.
     *
     * Use java.util.HashSet.
     *
     * @return set of keys in this map
     */
    public Set<K> keySet() {
        List<K> list = new java.util.ArrayList<K>();

        for (int i = 0; i < table.length; i++) {
            if (table[i] != null && !table[i].isRemoved()) {

                list.add(table[i].getKey());
            }
        }

        Set<K> set = new java.util.HashSet<K>(list);
        return set;

    }

    /**
     * Returns a List view of the values contained in this map.
     *
     * You should iterate over the table in order of increasing index.
     * Add entries to the List in the order in which they are traversed.
     *
     * Use java.util.ArrayList or java.util.LinkedList.
     *
     * @return list of values in this map
     */
    public List<V> values() {
        List<V> list = new java.util.ArrayList<V>();

        for (int i = 0; i < table.length; i++) {
            if (table[i] != null && !table[i].isRemoved()) {
                list.add(table[i].getValue());
            }
        }
        return list;

    }

    /**
     * Clears the table and resets it to a new table of length INITIAL_CAPACITY.
     */
    public void clear() {
        size = 0;
        table = (MapEntry<K, V>[]) new MapEntry[INITIAL_CAPACITY];
    }

    /**
     * Returns the size of the HashMap.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return number of items in the HashMap
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }

    /**
     * Returns the backing table of the HashMap.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the backing table of the HashMap
     */
    public MapEntry<K, V>[] getTable() {
        // DO NOT MODIFY THIS METHOD!
        return table;
    }

}
