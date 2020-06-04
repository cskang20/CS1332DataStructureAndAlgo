import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Comparator;

/**
 * My tests.
 * ComparatorPlus code taken from official tests
 * @author Vincent Huang
 * @author 1332 TAs
 */
public class Vincent_Sorting {
    private ComparatorPlus<Item> comp;
    private Item[] arr;
    private Item[] expected;
    @Before
    public void setup() {
        comp = Item.getItemComparator();


    }

    @Test(expected = IllegalArgumentException.class)
    public void e_IS1() {
        Sorting.insertionSort(null, comp);
    }

    @Test(expected = IllegalArgumentException.class)
    public void e_IS2() {
        arr = new Item[5];
        for(Item i : arr) {
            i = new Item(7);
        }
        Sorting.insertionSort(arr, null);
    }

    @Test
    public void IS1() {
        //                                          0  1               2
        int[] intArr = {2, 4, 1, 15, 12, 13, 5, 14, 8, 8, 3, 11, 9, 6, 8, 7, 10};
        arr = new Item[intArr.length];
        int repeatCount = 0;
        for(int i = 0; i < intArr.length; i++) {
            arr[i] = new Item(intArr[i]);
            if(intArr[i] == 8) {
                arr[i].setHiddenValue(repeatCount++);
            }
        }

        //                                   0  1  2
        intArr = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 8, 8, 9, 10, 11, 12, 13, 14, 15};
        expected = new Item[intArr.length];
        repeatCount = 0;
        for(int i = 0; i < intArr.length; i++) {
            expected[i] = new Item(intArr[i]);
            if(intArr[i] == 8) {
                expected[i].setHiddenValue(repeatCount++);
            }
        }

        Sorting.insertionSort(arr, comp);
        for(int i = 0; i < expected.length; i++) {
            assertEquals(expected[i].getValue(), arr[i].getValue());
            assertEquals(expected[i].getHiddenValue(), arr[i].getHiddenValue());
        }
        assertTrue(comp.getCount() < 80);
    }

    @Test
    public void IS2() {
        int[] intArr = {15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1};
        arr = new Item[intArr.length];
        for(int i = 0; i < intArr.length; i++) {
            arr[i] = new Item(intArr[i]);
        }

        intArr = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
        expected = new Item[intArr.length];
        for(int i = 0; i < intArr.length; i++) {
            expected[i] = new Item(intArr[i]);
        }

        Sorting.insertionSort(arr, comp);
        for(int i = 0; i < expected.length; i++) {
            assertEquals(expected[i].getValue(), arr[i].getValue());
        }
        assertTrue(comp.getCount() < 110);
    }

    @Test(expected = IllegalArgumentException.class)
    public void e_MS1() {
        Sorting.mergeSort(null, comp);
    }

    @Test(expected = IllegalArgumentException.class)
    public void e_MS2() {
        Sorting.mergeSort(arr, null);
    }

    @Test
    public void MS1() {
        int[] intArr = {2, 4, 1, 15, 12, 13, 5, 14, 8, 8, 3, 11, 9, 6, 8, 7, 10};
        arr = new Item[intArr.length];
        for(int i = 0; i < intArr.length; i++) {
            arr[i] = new Item(intArr[i]);
        }

        intArr = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 8, 8, 9, 10, 11, 12, 13, 14, 15};
        expected = new Item[intArr.length];
        for(int i = 0; i < intArr.length; i++) {
            expected[i] = new Item(intArr[i]);

        }

        Sorting.mergeSort(arr, comp);
        for(int i = 0; i < expected.length; i++) {
            assertEquals(expected[i].getValue(), arr[i].getValue());
        }
        assertTrue(comp.getCount() < 60);
    }

    @Test
    public void MS2() {
        int[] intArr = {15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1};
        arr = new Item[intArr.length];
        for(int i = 0; i < intArr.length; i++) {
            arr[i] = new Item(intArr[i]);
        }

        intArr = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
        expected = new Item[intArr.length];
        for(int i = 0; i < intArr.length; i++) {
            expected[i] = new Item(intArr[i]);
        }

        Sorting.mergeSort(arr, comp);
        for(int i = 0; i < expected.length; i++) {
            assertEquals(expected[i].getValue(), arr[i].getValue());
        }
        assertTrue(comp.getCount() < 40);
    }

    @Test(expected = IllegalArgumentException.class)
    public void e_LSDRS() {
        int[] arr = {1, 2, 3};
        Sorting.lsdRadixSort(null);
    }

    @Test
    public void LSDRS() {
        int[] arr = {Integer.MAX_VALUE, 7, -999, 4567, 1, Integer.MIN_VALUE, 2, 3, Integer.MIN_VALUE, 19};
        int[] expected = {Integer.MIN_VALUE, Integer.MIN_VALUE, -999, 1, 2, 3, 7, 19, 4567, Integer.MAX_VALUE};

        Sorting.lsdRadixSort(arr);
        for(int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], arr[i]);
        }
    }


    /**
     * Inner class that allows counting how many comparisons were made.
     */
    private abstract static class ComparatorPlus<T> implements Comparator<T> {
        private int count;

        /**
         * Get the number of comparisons made.
         * @return number of comparisons made
         */
        public int getCount() {
            return count;
        }

        /**
         * Increment the number of comparisons made by one. Call this method in
         * your compare() implementation.
         */
        public void incrementCount() {
            count++;
        }
    }

    private static class Item {
        private Integer value;
        private Integer hiddenValue; //used to test stability

        public Item(Integer value) {
            this(value, value);
        }

        public Item(Integer value, Integer hiddenValue) {
            this.value = value;
            this.hiddenValue = hiddenValue;
        }

        public Integer getValue() {
            return value;
        }

        public Integer getHiddenValue() {
            return hiddenValue;
        }

        public void setHiddenValue(Integer val) {
            hiddenValue = val;
        }

        public boolean hiddenEquals(Integer val) {
            return hiddenValue.equals(val);
        }

        @Override
        public String toString() {
            return Integer.toString(value);
        }


        public static ComparatorPlus<Item> getItemComparator() {
            return new ComparatorPlus<Item>() {
                @Override
                public int compare(Item item1, Item item2) {
                    incrementCount();
                    return item1.getValue().compareTo(item2.getValue());
                }
            };
        }
    }
}