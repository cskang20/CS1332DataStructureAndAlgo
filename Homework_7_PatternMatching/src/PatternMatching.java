import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * Your implementations of various pattern matching algorithms.
 *
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class PatternMatching {

    /**
     * Knuth-Morris-Pratt (KMP) algorithm that relies on the failure table (also
     * called failure function). Works better with small alphabets.
     *
     * Make sure to implement the failure table before implementing this method.
     *
     * @throws IllegalArgumentException if the pattern is null or of length 0
     * @throws IllegalArgumentException if text or comparator is null
     * @param pattern the pattern you are searching for in a body of text
     * @param text the body of text where you search for pattern
     * @param comparator you MUST use this for checking character equality
     * @return list containing the starting index for each match found
     */
    public static List<Integer> kmp(CharSequence pattern, CharSequence text,
                                    CharacterComparator comparator) {

        if (pattern == null || pattern.length() == 0) {
            throw new IllegalArgumentException("The given pattern is null or"
                    + " of length zero so cannot proceed with KMP");
        }
        if (text == null || comparator == null) {
            throw new IllegalArgumentException("The given text or comparator"
                    + " is null so cannot proceed with KMP");
        }

        List<Integer> matchList = new ArrayList<>();
        int j = 0;
        int k = 0;
        int m = pattern.length();
        int n = text.length();
        int[] failureTable = buildFailureTable(pattern, comparator);

        while (k < n) {
            if (comparator.compare(text.charAt(k), pattern.charAt(j)) == 0) {
                if (j == m - 1) {
                    matchList.add(k - (pattern.length() - 1));
                    j = failureTable[j - 1];
                    k++;
                    j++;
                } else {
                    j++;
                    k++;
                }
            } else if (j == 0) {
                k++;
            } else {
                j = failureTable[j - 1];
            }
        }
        return matchList;
    }


    /**
     * Builds failure table that will be used to run the Knuth-Morris-Pratt
     * (KMP) algorithm.
     *
     * The table built should be the length of the input text.
     *
     * Note that a given index i will be the largest prefix of the pattern
     * indices [0..i] that is also a suffix of the pattern indices [1..i].
     * This means that index 0 of the returned table will always be equal to 0
     *
     * Ex. ababac
     *
     * table[0] = 0
     * table[1] = 0
     * table[2] = 1
     * table[3] = 2
     * table[4] = 3
     * table[5] = 0
     *
     * If the pattern is empty, return an empty array.
     *
     * @throws IllegalArgumentException if the pattern or comparator is null
     * @param pattern a {@code CharSequence} you're building a failure table for
     * @param comparator you MUST use this for checking character equality
     * @return integer array holding your failure table
     */
    public static int[] buildFailureTable(CharSequence pattern,
                                          CharacterComparator comparator) {
        if (pattern == null || comparator == null) {
            throw new IllegalArgumentException("The given text or comparator"
                    + " is null so cannot proceed with KMP.");
        }

        int[] failureTable = new int[pattern.length()];
        int i = 0;
        int j = 1;
        int m = pattern.length();
        failureTable[0] = 0;

        while (j < m) {
            if (comparator.compare(pattern.charAt(i), pattern.charAt(j)) == 0) {
                failureTable[j] = i + 1;
                j++;
                i++;
            } else if (i == 0) {
                failureTable[j] = 0;
                j++;
            } else {
                i = failureTable[i - 1];
            }
        }
        return failureTable;

    }

    /**
     * Boyer Moore algorithm that relies on last occurrence table. Works better
     * with large alphabets.
     *
     * Make sure to implement the last occurrence table before implementing this
     * method.
     *
     * Note: You may find the getOrDefault() method useful from Java's Map.
     *
     * @throws IllegalArgumentException if the pattern is null or of length 0
     * @throws IllegalArgumentException if text or comparator is null
     * @param pattern the pattern you are searching for in a body of text
     * @param text the body of text where you search for the pattern
     * @param comparator you MUST use this for checking character equality
     * @return list containing the starting index for each match found
     */
    public static List<Integer> boyerMoore(CharSequence pattern,
                                           CharSequence text,
                                           CharacterComparator comparator) {
        if (pattern == null || pattern.length() == 0) {
            throw new IllegalArgumentException("The given pattern is null or"
                    + " of length zero so cannot proceed with KMP");
        }
        if (text == null || comparator == null) {
            throw new IllegalArgumentException("The given text or comparator"
                    + " is null so cannot proceed with KMP");
        }

        List<Integer> matchList = new ArrayList<Integer>();
        if (pattern.length() > text.length()) {
            return matchList;
        }
        Map<Character, Integer> lastTableMap = buildLastTable(pattern);
        int i = 0;
        int j = 0;
        while (i <= text.length() - pattern.length()) {
            j = pattern.length() - 1;
            while (j >= 0
                    && comparator.compare(text.charAt(i + j),
                    pattern.charAt(j)) == 0) {
                j = j - 1;
            }
            if (j == -1) {
                matchList.add(i);
                i++;
            } else {
                if (lastTableMap.get(text.charAt(i + j)) == null) {
                    i = i + j + 1;
                } else {
                    int moveIndex = lastTableMap.get(text.charAt(i + j));
                    if (moveIndex < j) {
                        i = i + (j - moveIndex);
                    } else {
                        i++;
                    }
                }
            }
        }
        return matchList;


    }

    /**
     * Builds last occurrence table that will be used to run the Boyer Moore
     * algorithm.
     *
     * Note that each char x will have an entry at table.get(x).
     * Each entry should be the last index of x where x is a particular
     * character in your pattern.
     * If x is not in the pattern, then the table will not contain the key x,
     * and you will have to check for that in your Boyer Moore implementation.
     *
     * Ex. octocat
     *
     * table.get(o) = 3
     * table.get(c) = 4
     * table.get(t) = 6
     * table.get(a) = 5
     * table.get(everything else) = null, which you will interpret in
     * Boyer-Moore as -1
     *
     * If the pattern is empty, return an empty map.
     *
     * @throws IllegalArgumentException if the pattern is null
     * @param pattern a {@code CharSequence} you are building last table for
     * @return a Map with keys of all of the characters in the pattern mapping
     *         to their last occurrence in the pattern
     */
    public static Map<Character, Integer> buildLastTable(CharSequence pattern) {
        if (pattern == null || pattern.length() == 0) {
            throw new IllegalArgumentException("The pattern is null so "
                    + "cannot build last table");
        }

        Map<Character, Integer> map = new HashMap<Character, Integer>();

        for (int i = 0; i < pattern.length(); i++) {
            map.put(pattern.charAt(i), i);
        }

        return map;
    }

    /**
     * Prime base used for Rabin-Karp hashing.
     * DO NOT EDIT!
     */
    private static final int BASE = 101;

    /**
     * Runs the Rabin-Karp algorithm. This algorithms generates hashes for the
     * pattern and compares this hash to substrings of the text before doing
     * character by character comparisons.
     *
     * When the hashes are equal and you do character comparisons, compare
     * starting from the beginning of the pattern to the end, not from the end
     * to the beginning.
     *
     * You must use the Rabin-Karp Rolling Hash for this implementation. The
     * formula for it is:
     *
     * sum of: c * BASE ^ (pattern.length - 1 - i), where c is the integer
     * value of the current character, and i is the index of the character
     *
     * Note that if you were dealing with very large numbers here, your hash
     * will likely overflow; you will not need to handle this case.
     * You may assume that all powers and calculations CAN be done without
     * overflow. However, be careful with how you carry out your calculations.
     * For example, if BASE^(m - 1) is a number that fits into an int, it's
     * possible for BASE^m will overflow. So, you would not want to do
     * BASE^m / BASE to find BASE^(m - 1).
     *
     * For example: Hashing "bunn" as a substring of "bunny" with base 101 hash
     * = b * 101 ^ 3 + u * 101 ^ 2 + n * 101 ^ 1 + n * 101 ^ 0 = 98 * 101 ^ 3 +
     * 117 * 101 ^ 2 + 110 * 101 ^ 1 + 110 * 101 ^ 0 = 102174235
     *
     * Another key step for this algorithm is that updating the hashcode from
     * one substring to the next one must be O(1). To update the hash:
     *
     * remove the oldChar times BASE raised to the length - 1, multiply by
     * BASE, and add the newChar.
     *
     * For example: Shifting from "bunn" to "unny" in "bunny" with base 101
     * hash("unny") = (hash("bunn") - b * 101 ^ 3) * 101 + y =
     * (102174235 - 98 * 101 ^ 3) * 101 + 121 = 121678558
     *
     * Keep in mind that calculating exponents is not O(1) in general, so you'll
     * need to keep track of what BASE^{m - 1} is for updating the hash.
     *
     * Do NOT use Math.pow() for this method.
     *
     * @throws IllegalArgumentException if the pattern is null or of length 0
     * @throws IllegalArgumentException if text or comparator is null
     * @param pattern a string you're searching for in a body of text
     * @param text the body of text where you search for pattern
     * @param comparator the comparator to use when checking character equality
     * @return list containing the starting index for each match found
     */
    public static List<Integer> rabinKarp(CharSequence pattern,
                                          CharSequence text,
                                          CharacterComparator comparator) {
        if (pattern == null || pattern.length() == 0) {
            throw new IllegalArgumentException("The given pattern is null or"
                    + " of length zero so cannot proceed with KMP");
        }
        if (text == null || comparator == null) {
            throw new IllegalArgumentException("The given text or comparator"
                    + " is null so cannot proceed with KMP");
        }
        List<Integer> matchList = new ArrayList<Integer>();
        if (pattern.length() > text.length()) {
            return matchList;
        }
        int patternHashNum = 0;
        int textHashNum = 0;
        int removeNum = 1;
        for (int i = 0; i < pattern.length(); i++) {
            patternHashNum = patternHashNum * BASE + pattern.charAt(i);
            textHashNum = textHashNum * BASE + text.charAt(i);
            removeNum = removeNum * BASE;
        }
        removeNum = removeNum / BASE;
        int i = 0;
        int j = 0;
        while (i <= text.length() - pattern.length()) {
            if (patternHashNum == textHashNum) {
                j = 0;
                while (j < pattern.length()
                        && comparator.compare(text.charAt(i + j),
                        pattern.charAt(j)) == 0) {
                    j++;
                }
                if (j == pattern.length()) {
                    matchList.add(i);
                }
            }
            if (i < text.length() - pattern.length()) {
                textHashNum = (textHashNum - removeNum * text.charAt(i)) * BASE
                        + text.charAt(i + pattern.length());
            }
            i++;
        }
        return matchList;
    }

}

