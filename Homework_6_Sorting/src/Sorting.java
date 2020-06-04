import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;

/**
 * Your implementation of various sorting algorithms.
 *
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class Sorting {

    /**
     * Implement insertion sort.
     *
     * It should be:
     *  in-place
     *  stable
     *
     * Have a worst case running time of:
     *  O(n^2)
     *
     * And a best case running time of:
     *  O(n)
     *
     * @throws IllegalArgumentException if the array or comparator is null
     * @param <T> data type to sort
     * @param arr the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     */
    public static <T> void insertionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException(
                    "Given array is null or comparator is null. Cannot sort.");
        }

        for (int i = 1; i < arr.length; i++) {
            int j = i;
            while (j > 0 && comparator.compare(arr[j - 1], arr[j]) > 0) {
                T temp = arr[j];
                arr[j] = arr[j - 1];
                arr[j - 1] = temp;
                j--;
            }
        }

    }

    /**
     * Implement merge sort.
     *
     * It should be:
     *  out-of-place
     *  stable
     *
     * Have a worst case running time of:
     *  O(n log n)
     *
     * And a best case running time of:
     *  O(n log n)
     *
     * You can create more arrays to run mergesort, but at the end, everything
     * should be merged back into the original T[] which was passed in.
     *
     * When splitting the array, if there is an odd number of elements, put the
     * extra data on the right side.
     *
     * @throws IllegalArgumentException if the array or comparator is null
     * @param <T> data type to sort
     * @param arr the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     */
    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException(
                    "Given array is null or comparator is null. Cannot sort.");
        }

        if (arr.length > 1) {
            int split = arr.length / 2;
            T[] leftArr = (T[]) new Object[split];
            for (int i = 0; i < split; i++) {
                leftArr[i] = arr[i];
            }
            T[] rightArr = (T[]) new Object[arr.length - split];
            for (int j = 0; j < arr.length - split; j++) {
                rightArr[j] = arr[j + split];
            }
            mergeSort(leftArr, comparator);
            mergeSort(rightArr, comparator);
            mergeHelper(arr, leftArr, rightArr, comparator);
        }

    }

    /**
     * merges arrays together
     * @param arr which is array that is merged
     * @param leftArr left part the array
     * @param rightArr right part of the array
     * @param comparator comparator to compare two variables
     * @param <T> generic creation of array
     */
    private static <T> void mergeHelper(T[] arr, T[] leftArr,
                                        T[] rightArr,
                                        Comparator<T> comparator) {
        int left = 0;
        int right = 0;
        for (int i = 0; i < arr.length; i++) {
            if (right >= rightArr.length || left < leftArr.length && comparator.compare(leftArr[left], rightArr[right]) <= 0) {
                arr[i] = leftArr[left];
                left++;
            } else {
                arr[i] = rightArr[right];
                right++;
            }
        }
    }

    /**
     * Implement LSD (least significant digit) radix sort.
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
     * credit if you do not implement the one we have taught you!
     *
     * Remember you CANNOT convert the ints to strings at any point in your
     * code! Doing so may result in a 0 for the implementation.
     *
     * It should be:
     *  out-of-place
     *  stable
     *
     * Have a worst case running time of:
     *  O(kn)
     *
     * And a best case running time of:
     *  O(kn)
     *
     * You are allowed to make an initial O(n) passthrough of the array to
     * determine the number of iterations you need.
     *
     * At no point should you find yourself needing a way to exponentiate a
     * number; any such method would be non-O(1). Think about how how you can
     * get each power of BASE naturally and efficiently as the algorithm
     * progresses through each digit.
     *
     * Refer to the PDF for more information on LSD Radix Sort.
     *
     * You may use {@code java.util.ArrayList} or {@code java.util.LinkedList}
     * if you wish, but it may only be used inside radix sort and any radix sort
     * helpers. Do NOT use these classes with other sorts.
     *
     * Do NOT use anything from the Math class except Math.abs().
     *
     * @throws IllegalArgumentException if the array is null
     * @param arr the array to be sorted
     */
    public static void lsdRadixSort(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException(
                    "Given array is null. Cannot Sort.");
        }
        ArrayList<LinkedList<Integer>> arrBuckets =
                new ArrayList<LinkedList<Integer>>(19);
        for (int i = 0; i < 19; i++) {
            arrBuckets.add(new LinkedList<Integer>());
        }
        int maxNum = Math.abs(arr[0]);
        if (arr[0] == Integer.MIN_VALUE) {
            maxNum = Integer.MAX_VALUE;
        }
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] == Integer.MIN_VALUE) {
                maxNum = Integer.MAX_VALUE;
            } else if (Math.abs(arr[i]) > maxNum) {
                maxNum = Math.abs(arr[i]);
            }
        }
        int counter = 0;
        while (maxNum > 0) {
            maxNum = maxNum / 10;
            counter++;
        }
        int position = 1;
        for (int i = 0; i < counter; i++) {
            for (int j = 0; j < arr.length; j++) {
                arrBuckets.get((arr[j] / position) % 10 + 9).add(arr[j]);
            }
            position *= 10;
            int num = 0;
            for (int k = 0; k < 19; k++) {
                while (!arrBuckets.get(k).isEmpty()) {
                    arr[num] = arrBuckets.get(k).remove();
                    num++;
                }
            }
        }
    }
}
