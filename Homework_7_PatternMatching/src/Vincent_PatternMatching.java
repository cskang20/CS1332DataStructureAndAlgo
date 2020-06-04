import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertArrayEquals;

/**
 * My tests.
 *
 * @author Vincent Huang
 * @version 1.0
 */

public class Vincent_PatternMatching {
    private CharacterComparator comparator;

    @Before
    public void setup() {
        comparator = new CharacterComparator();
    }

    @Test(expected = IllegalArgumentException.class)
    public void e_KMP1() {
        PatternMatching.kmp(null, "hello", comparator);
    }

    @Test(expected = IllegalArgumentException.class)
    public void e_KMP2() {
        PatternMatching.kmp("", "hello", comparator);
    }

    @Test(expected = IllegalArgumentException.class)
    public void e_KMP3() {
        PatternMatching.kmp("hello", null, comparator);
    }

    @Test(expected = IllegalArgumentException.class)
    public void e_KMP4() {
        PatternMatching.kmp("hello", "hellohello", null);
    }

    @Test
    public void KMP1() {
        assertEquals(new ArrayList<>(), PatternMatching.kmp("abcdefghijklmnop", "hello", comparator));
        assertEquals(0, comparator.getComparisonCount());

        assertEquals(new ArrayList<>(), PatternMatching.kmp("abcdefghijklmnop", "", comparator));
        assertEquals(0, comparator.getComparisonCount());
    }

    @Test
    public void KMP2() {
        List<Integer> expected = Arrays.asList(0, 3, 6, 9, 11);
        assertEquals(expected, PatternMatching.kmp("ab", "abaabbabbabab", comparator));
        assertEquals(1+14, comparator.getComparisonCount());
    }

    @Test
    public void KMP3() {
        List<Integer> expected = Arrays.asList(0, 2);
        assertEquals(expected, PatternMatching.kmp("abab", "ababab", comparator));
        assertEquals(3+6, comparator.getComparisonCount());
    }

    @Test
    public void KMP4() {
        List<Integer> expected = Arrays.asList(5);
        assertEquals(expected, PatternMatching.kmp("ababa", "ababbababa", comparator));
        assertEquals(4+12, comparator.getComparisonCount());
    }

    @Test
    public void KMP5() {
        List<Integer> expected = Arrays.asList(0);
        assertEquals(expected, PatternMatching.kmp("abcd", "abcd", comparator));
        assertEquals(3+4, comparator.getComparisonCount());
    }

    @Test(expected = IllegalArgumentException.class)
    public void e_BF1() {
        PatternMatching.buildFailureTable(null, comparator);
    }

    @Test(expected = IllegalArgumentException.class)
    public void e_BF2() {
        PatternMatching.buildFailureTable("hello", null);
    }

    @Test
    public void BF1() {
        int[] fail = PatternMatching.buildFailureTable("", comparator);
        int[] expected = {};
        assertArrayEquals(expected, fail);
        assertEquals(0, comparator.getComparisonCount());
    }

    @Test
    public void BF2() {
        int[] fail = PatternMatching.buildFailureTable("abacab", comparator);
        int[] expected = {0, 0, 1, 0, 1, 2};
        assertArrayEquals(expected, fail);
        assertEquals(6, comparator.getComparisonCount());
    }

    @Test
    public void BF3() {
        int[] fail = PatternMatching.buildFailureTable("ababac", comparator);
        int[] expected = {0, 0, 1, 2, 3, 0};
        assertArrayEquals(expected, fail);
        assertEquals(7, comparator.getComparisonCount());
    }

    @Test
    public void BF4() {
        int[] fail= PatternMatching.buildFailureTable("abaababa", comparator);
        int[] expected = {0, 0, 1, 1, 2, 3, 2, 3};
        assertArrayEquals(expected, fail);
        assertEquals(9, comparator.getComparisonCount());
    }

    @Test
    public void BF5() {
        int[] fail= PatternMatching.buildFailureTable("aaaaa", comparator);
        int[] expected = {0, 1, 2, 3, 4};
        assertArrayEquals(expected, fail);
        assertEquals(4, comparator.getComparisonCount());
    }

    @Test
    public void BF6() {
        int[] fail= PatternMatching.buildFailureTable("babaabbababa", comparator);
        int[] expected = {0, 0, 1, 2, 0, 1, 1, 2, 3, 4, 3, 4};
        assertArrayEquals(expected, fail);
        assertEquals(14, comparator.getComparisonCount());
    }

    @Test(expected = IllegalArgumentException.class)
    public void e_BM1() {
        PatternMatching.boyerMoore(null, "hello", comparator);
    }

    @Test(expected = IllegalArgumentException.class)
    public void e_BM2() {
        PatternMatching.boyerMoore("", "hello", comparator);
    }

    @Test(expected = IllegalArgumentException.class)
    public void e_BM3() {
        PatternMatching.boyerMoore("hello", null, comparator);
    }

    @Test(expected = IllegalArgumentException.class)
    public void e_BM4() {
        PatternMatching.boyerMoore("hello", "hellohello", null);
    }

    @Test
    public void BM1() {
        assertEquals(new ArrayList<>(), PatternMatching.boyerMoore("abcdefghijklmnop", "hello", comparator));
        assertEquals(0, comparator.getComparisonCount());

        assertEquals(new ArrayList<>(), PatternMatching.boyerMoore("abcdefghijklmnop", "", comparator));
        assertEquals(0, comparator.getComparisonCount());
    }

    @Test
    public void BM2() {
        List<Integer> expected = Arrays.asList(10, 16);
        assertEquals(expected, PatternMatching.boyerMoore("abacab", "abacbabadcabacababacab", comparator));
        assertEquals(25, comparator.getComparisonCount());
    }

    @Test
    public void BM3() {
        List<Integer> expected = Arrays.asList(11, 17);
        assertEquals(expected, PatternMatching.boyerMoore("happy", "because im happy happy", comparator));
        assertEquals(14, comparator.getComparisonCount());
    }

    @Test
    public void BM4() {
        List<Integer> expected = Arrays.asList(0, 2);
        assertEquals(expected, PatternMatching.boyerMoore("abab", "ababab", comparator));
        assertEquals(9, comparator.getComparisonCount());
    }

    @Test(expected = IllegalArgumentException.class)
    public void e_BL1() {
        PatternMatching.buildLastTable(null);
    }

    @Test
    public void BL1() {
        Map<Character, Integer> expected = new HashMap<>();
        char[] keyArr = {'h', 'e', 'l', 'o'};
        int[] valArr =  {  0,   1,   3,   4};
        for(int i = 0; i < keyArr.length; i++) {
            expected.put(keyArr[i], valArr[i]);
        }
        assertEquals(expected, PatternMatching.buildLastTable("hello"));
    }

    @Test
    public void BL2() {
        Map<Character, Integer> expected = new HashMap<>();
        char[] keyArr = {'a', 'b', 'c', 'd', 'e'};
        int[] valArr =  {  3,   4,   5,  6,   7};
        for(int i = 0; i < keyArr.length; i++) {
            expected.put(keyArr[i], valArr[i]);
        }
        assertEquals(expected, PatternMatching.buildLastTable("abcabcde"));
    }

    @Test
    public void BL3() {
        Map<Character, Integer> expected = new HashMap<>();
        char[] keyArr = {'o', 'c', 't', 'a'};
        int[] valArr =  {  3,   4,   6,  5};
        for(int i = 0; i < keyArr.length; i++) {
            expected.put(keyArr[i], valArr[i]);
        }
        assertEquals(expected, PatternMatching.buildLastTable("octocat"));
    }

    @Test
    public void BL4() {
        assertEquals(new HashMap<Character, Integer>(), PatternMatching.buildLastTable(""));
    }

    @Test(expected = IllegalArgumentException.class)
    public void e_RK1() {
        PatternMatching.rabinKarp(null, "hello", comparator);
    }

    @Test(expected = IllegalArgumentException.class)
    public void e_RK2() {
        PatternMatching.rabinKarp("", "hello", comparator);
    }

    @Test(expected = IllegalArgumentException.class)
    public void e_RK3() {
        PatternMatching.rabinKarp("hello", null, comparator);
    }

    @Test(expected = IllegalArgumentException.class)
    public void e_RK4() {
        PatternMatching.rabinKarp("hello", "hellohello", null);
    }

    @Test
    public void RK1() {
        assertEquals(new ArrayList<>(), PatternMatching.rabinKarp("abcdefghijklmnop", "hello", comparator));
        assertEquals(0, comparator.getComparisonCount());

        assertEquals(new ArrayList<>(), PatternMatching.rabinKarp("abcdefghijklmnop", "", comparator));
        assertEquals(0, comparator.getComparisonCount());
    }

    @Test
    public void RK2() {
        List<Integer> expected = Arrays.asList(0, 3, 6, 9, 11);
        assertEquals(expected, PatternMatching.rabinKarp("ab", "abaabbabbabab", comparator));
        assertEquals(10, comparator.getComparisonCount());
    }

    @Test
    public void RK3() {
        List<Integer> expected = Arrays.asList(0, 2);
        assertEquals(expected, PatternMatching.rabinKarp("abab", "ababab", comparator));
        assertEquals(8, comparator.getComparisonCount());
    }

    /*
    @Test
    public void RK4() {
        List<Integer> expected = Arrays.asList(5);
        assertEquals(expected, PatternMatching.rabinKarp("ababa", "ababbababa", comparator));
        assertEquals(5, comparator.getComparisonCount());
    }
    */

    @Test
    public void RK5() {
        List<Integer> expected = Arrays.asList(0);
        assertEquals(expected, PatternMatching.rabinKarp("abcd", "abcd", comparator));
        assertEquals(4, comparator.getComparisonCount());
    }

    /*
    @Test
    public void RK6() {
        List<Integer> expected = Arrays.asList(10, 16);
        assertEquals(expected, PatternMatching.rabinKarp("abacab", "abacbabadcabacababacab", comparator));
        assertEquals(12, comparator.getComparisonCount());
    }
    @Test
    public void RK7() {
        List<Integer> expected = Arrays.asList(11, 17);
        assertEquals(expected, PatternMatching.rabinKarp("happy", "because im happy happy", comparator));
        assertEquals(10, comparator.getComparisonCount());
    }
    */


}