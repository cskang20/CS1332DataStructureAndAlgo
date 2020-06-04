import org.junit.Before;
import org.junit.Test;

import java.util.NoSuchElementException;

import static org.junit.Assert.*;

/**
 * HashMap tests, let me know if I made an error and I will
 * rectify it.
 *
 * @author Milan Patel
 *
 * Shout out to Charles Min Woo for pointing out my mistakes
 * :-)
 */
public class MilanHashMapTests {

    private HashMap<Integer, String> map;
    private static final int TIMEOUT = 200;

    @Before
    public void setUp() {
        // [(0, A), (1, B), (2, C), (3, D), (4, E), _, _, _, _, _, _, _, _]
        map = new HashMap<>();
        map.put(new Integer(0), "A");
        map.put(new Integer(1), "B");
        map.put(new Integer(2), "C");
        map.put(new Integer(3), "D");
        map.put(new Integer(4), "E");
    }

    @Test
    public void ensureSize() {
        assertEquals(5, map.size());
        assertEquals("C", map.remove(2));
        assertEquals(4, map.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testException1() {
        map.put(null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testException2() {
        map.put(1, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testException3() {
        map.put(null, "test");
    }

//    @Test(expected = IllegalArgumentException.class)
//    public void testException4() {
//        map.resizeBackingTable(map.size() - 1);
//    }

    @Test(expected = IllegalArgumentException.class)
    public void testException5() {
        map.remove(null);
    }

    @Test(expected = NoSuchElementException.class)
    public void testException6() {
        map.remove(99);
    }
//
//    @Test(expected = IllegalArgumentException.class)
//    public void testException7() {
//        map.get(null);
//    }
//
//    @Test(expected = NoSuchElementException.class)
//    public void testException8() {
//        map.get(99);
//    }
//
//    @Test(expected = IllegalArgumentException.class)
//    public void testException9() {
//        map.containsKey(null);
//    }

    @Test
    public void testPut1() {
        map = new HashMap<>();
        map.put(new Integer(0), "A");
        map.put(new Integer(2), "C");
        map.put(new Integer(3), "D");
        map.put(new Integer(4), "E");
        // [(0, A), _, (2, C), (3, D), (4, E), _, _, _, _, _, _, _, _]
        assertNull(map.getTable()[1]);
        map.put(1, "B");
        assertEquals("B", map.getTable()[1].getValue());
    }

    @Test
    public void testPut2() {
        // [(0, A), x(1, B), (2, C), (3, D), (4, E), _, _, _, _, _, _, _, _]
        assertEquals("B", map.remove(1));
        assertEquals(4, map.size());
        assertTrue(map.getTable()[1].isRemoved());
        assertNull(map.put(1, "F"));
//        assertEquals("F", map.get(1));
        assertEquals("F", map.getTable()[1].getValue());
        assertFalse(map.getTable()[1].isRemoved());
        assertNull(map.getTable()[5]);
    }

    @Test
    public void testPut3() {
        // [(0, A), (1, B), (2, C), (3, D), (4, E), _, _, _, _, _, _, _, _]

        assertNull(map.put(14, "F"));
        // [(0, A), (1, B), (2, C), (3, D), (4, E), (14, F), _, _, _, _, _, _, _]

//        assertEquals("F", map.get(14));
        assertEquals("F", map.getTable()[5].getValue());
    }

    @Test
    public void testPut4() {
        // [(0, A), (1, B), (2, C), (3, D), (4, E), _, _, _, _, _, _, _, _]

        assertEquals("B", map.remove(1));
        // [(0, A), x(1, B), (2, C), (3, D), (4, E), _, _, _, _, _, _, _, _]

        assertTrue(map.getTable()[1].isRemoved());
        assertNull(map.put(14, "F"));
        // [(0, A), (14, F), (2, C), (3, D), (4, E), _, _, _, _, _, _, _, _]

//        assertEquals("F", map.get(14));
        assertEquals("F", map.getTable()[1].getValue());
        assertNull(map.getTable()[5]);
        assertFalse(map.getTable()[1].isRemoved());
    }

    @Test
    public void testPut5() {
        // [(0, A), (1, B), (2, C), (3, D), (4, E), _, _, _, _, _, _, _, _]

        assertEquals("C", map.remove(2));
        // [(0, A), (1, B), x(2, C), (3, D), (4, E), _, _, _, _, _, _, _, _]

        assertNull(map.put(14, "F"));
        // [(0, A), (1, B), (14, F), (3, D), (4, E), _, _, _, _, _, _, _, _]

//        assertEquals("F", map.get(14));
        assertEquals("F", map.getTable()[2].getValue());
        assertNull(map.getTable()[5]);
        assertFalse(map.getTable()[2].isRemoved());
    }

    @Test
    public void testPut6() {
        // [(0, A), (1, B), (2, C), (3, D), (4, E), _, _, _, _, _, _, _, _]

        assertEquals("C", map.remove(2));
        // [(0, A), (1, B), x(2, C), (3, D), (4, E), _, _, _, _, _, _, _, _]

        assertEquals("E", map.remove(4));
        // [(0, A), (1, B), x(2, C), (3, D), x(4, E), _, _, _, _, _, _, _, _]

        assertNull(map.put((Integer) 14, "F"));
        // [(0, A), (1, B), (14, F), (3, D), x(4, E), _, _, _, _, _, _, _, _]

//        assertEquals("F", map.get(14));
        assertEquals("F", map.getTable()[2].getValue());
        assertNull(map.getTable()[5]);
    }

    @Test
    public void testPut7() {
        // [(0, A), (1, B), (2, C), (3, D), (4, E), _, _, _, _, _, _, _, _]

        assertNull(map.put(14, "F"));
        // [(0, A), (1, B), (2, C), (3, D), (4, E), (14, F), _, _, _, _, _, _, _]

//        assertEquals("F", map.get(14));
        assertEquals("F", map.getTable()[5].getValue());

        assertEquals("F", map.put(14, "Z"));
        // [(0, A), (1, B), (2, C), (3, D), (4, E), (14, Z), _, _, _, _, _, _, _]

//        assertEquals("Z", map.get(14));
        assertEquals("Z", map.getTable()[5].getValue());
    }


    @Test
    public void testPut8() {
        // [(0, A), (1, B), (2, C), (3, D), (4, E), _, _, _, _, _, _, _, _]

        assertNull(map.put(14, "F"));
        // [(0, A), (1, B), (2, C), (3, D), (4, E), (14, F), _, _, _, _, _, _, _]

//        assertEquals("F", map.get(14));
        assertEquals("F", map.getTable()[5].getValue());

        assertEquals("D", map.remove(3));
        // [(0, A), (1, B), (2, C), x(3, D), (4, E), (14, F), _, _, _, _, _, _, _]
        assertTrue(map.getTable()[3].isRemoved());

        assertEquals("F", map.put(14, "Z"));
        // [(0, A), (1, B), (2, C), x(3, D), (4, E), (14, Z), _, _, _, _, _, _, _]

//        assertEquals("Z", map.get(14));
        assertEquals("Z", map.getTable()[5].getValue());
    }

    @Test
    public void testPut9() {
        // [(0, A), (1, B), (2, C), (3, D), (4, E), _, _, _, _, _, _, _, _]

        assertNull(map.put(14, "F"));
        // [(0, A), (1, B), (2, C), (3, D), (4, E), (14, F), _, _, _, _, _, _, _]

//        assertEquals("F", map.get(14));
        assertEquals("F", map.getTable()[5].getValue());

        assertEquals("B", map.remove(1));
        // [(0, A), x(1, B), (2, C), (3, D), (4, E), (14, F), _, _, _, _, _, _, _]
        assertTrue(map.getTable()[1].isRemoved());

        assertEquals("F", map.put(14, "Z"));
        // [(0, A), x(1, B), (2, C), (3, D), (4, E), (14, Z), _, _, _, _, _, _, _]

//        assertEquals("Z", map.get(14));
        assertEquals("Z", map.getTable()[5].getValue());
        assertTrue(map.getTable()[1].isRemoved());
    }

    @Test
    public void testPut10() {
        map = new HashMap<>();
        map.put(6, "G");
        map.put(7, "H");
        map.put(8, "I");
        map.put(9, "J");
        map.put(10, "K");
        map.put(11, "L");
        map.put(12, "M");
        // [ _, _, _, _, _, _, (6, G), (7, H), (8, I), (9, J), (10, K), (11, L), (12, M)]

        assertNull(map.put(19, "F"));
        // [ (19, F), _, _, _, _, _, (6, G), (7, H), (8, I), (9, J), (10, K), (11, L), (12, M)]

//        assertEquals("F", map.get(19));
        assertEquals("F", map.getTable()[0].getValue());
    }

    @Test
    public void testPut11() {
        map = new HashMap<>();
        map.put(6, "G");
        map.put(7, "H");
        map.put(8, "I");
        map.put(9, "J");
        map.put(10, "K");
        map.put(11, "L");
        map.put(12, "M");
        // [ _, _, _, _, _, _, (6, G), (7, H), (8, I), (9, J), (10, K), (11, L), (12, M)]

        assertNull(map.put(19, "F"));
        // [ (19, F), _, _, _, _, _, (6, G), (7, H), (8, I), (9, J), (10, K), (11, L), (12, M)]
//        assertEquals("F", map.get(19));
        assertEquals("F", map.getTable()[0].getValue());

        assertEquals("F", map.remove(19));
        // [ x(19, F), _, _, _, _, _, (6, G), (7, H), (8, I), (9, J), (10, K), (11, L), (12, M)]
        assertTrue(map.getTable()[0].isRemoved());

        assertNull(map.put(19 + 13, "FF"));
        // [ (32, FF), _, _, _, _, _, (6, G), (7, H), (8, I), (9, J), (10, K), (11, L), (12, M)]
//        assertEquals("FF", map.get(19 + 13));
        assertEquals("FF", map.getTable()[0].getValue());
        assertFalse(map.getTable()[0].isRemoved());
    }

    @Test
    public void testPut12() {
        // [(0, A), (1, B), (2, C), (3, D), (4, E), _, _, _, _, _, _, _, _]
        assertNull(map.put(Integer.MIN_VALUE, "LOL"));

        // [(0, A), (1, B), (2, C), (3, D), (4, E), _, _, _, _, _, _, (Integer.MIN_VALUE, LOL), _]
//        assertEquals("LOL", map.get(Integer.MIN_VALUE));
        assertEquals("LOL", map.getTable()[11].getValue());

    }

    @Test
    public void testPut13() {
        // [(0, A), (1, B), (2, C), (3, D), (4, E), _, _, _, _, _, _, _, _]
        assertEquals(5, map.size());
        assertEquals("E", map.put(4, "U"));
        assertEquals(5, map.size());
    }

    @Test(expected = NoSuchElementException.class)
    public void testRemove1() {
        // [(0, A), (1, B), (2, C), (3, D), (4, E), _, _, _, _, _, _, _, _]

        assertEquals("C", map.remove(2));
        // [(0, A), (1, B), x(2, C), (3, D), (4, E), _, _, _, _, _, _, _, _]
        assertTrue(map.getTable()[2].isRemoved());
        map.remove(2);
    }

    @Test
    public void testRemove2() {
        // [(0, A), (1, B), (2, C), (3, D), (4, E), _, _, _, _, _, _, _, _]
        for (int i = 0; i < 5; ++i) {
            map.remove(i);
        }
//        assertEquals(0, map.keySet().size());
//        assertEquals(0, map.values().size());

        for (MapEntry removed : map.getTable()) {
            if (null == removed) {
                continue;
            }
            assertTrue(removed.isRemoved());
        }
    }

    @Test
    public void testResize1() {
        // [(0, A), (1, B), (2, C), (3, D), (4, E), _, _, _, _, _, _, _, _]
        int oldSize = map.getTable().length;
        assertEquals(5, map.size());
        map.put(5, "F");
        // [(0, A), (1, B), (2, C), (3, D), (4, E), (5, F), _, _, _, _, _, _, _]

        assertEquals(oldSize, map.getTable().length);
        assertEquals(6, map.size());

        map.put(6, "G");
        // [(0, A), (1, B), (2, C), (3, D), (4, E), (5, F), (6, G), _, _, _, _, _, _]
        assertEquals(oldSize, map.getTable().length);
        assertEquals(7, map.size());

        map.put(7, "H");
        // [(0, A), (1, B), (2, C), (3, D), (4, E), (5, F), (6, G), (7, H), _, _, _, _, _]
        assertEquals(oldSize, map.getTable().length);
        assertEquals(8, map.size());

        map.put(8, "I");
        // [(0, A), (1, B), (2, C), (3, D), (4, E), (5, F), (6, G), (7, H), (8, I),
        //      _, _, _, _, _, _, _, _, _, _, _, _, _, _, _, _, _, _,]

        assertEquals((2 * oldSize) + 1, map.getTable().length);
        assertEquals(9, map.size());
    }

    @Test
    public void testResize2() {
        // [(0, A), (1, B), (2, C), (3, D), (4, E), _, _, _, _, _, _, _, _]
        for (int i = 0; i < 5; ++i) {
            map.remove(i);
        }
//        map.resizeBackingTable(HashMap.INITIAL_CAPACITY);
        MapEntry[] emptyArr = new MapEntry[HashMap.INITIAL_CAPACITY];
        assertArrayEquals(emptyArr, map.getTable());
    }
}
