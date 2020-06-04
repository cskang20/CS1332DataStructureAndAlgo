import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.HashSet;

import static org.junit.Assert.*;

/**
 * My tests.
 *
 * @author Vincent Huang
 * @version 1.0
 */

public class Vincent_HashMaps {
    //edge cases:
    //Have delete markers at every index and test all the methods
    //resize so that load factor is 100% and test all the methods

    private HashMap<Integer, String> map;
    private static final int TIMEOUT = 200;

    @Before
    public void setUp() {
        map = new HashMap<>(5);
    }

    @Test(timeout = TIMEOUT)
    public void C1() {
        map = new HashMap<>();
        assertEquals(0, map.size());
        for (int i = 0; i < 13; i++) {
            assertNull(map.getTable()[i]);
        }
    }

    @Test(timeout = TIMEOUT)
    public void C2() {
        map = new HashMap<>(50);
        assertEquals(0, map.size());
        for (int i = 0; i < 50; i++) {
            assertNull(map.getTable()[i]);
        }
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void e_P1() {
        map.put(null, "hello");
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void e_P2() {
        map.put(new Integer(5), null);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void e_P3() {
        map.put(null, null);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void e_P4() {
        map.put(new Integer(5), "hello");
        map.put(new Integer(5), null);
    }

    @Test(timeout = TIMEOUT)
    public void P1() {
        String[] expected = {null, null, null, "A", null};
        assertNull(map.put(new Integer(3), "A"));
        assertEquals(1, map.size());
        for (int i = 0; i < map.getTable().length; i++) {
            if (expected[i] == null) {
                assertNull(map.getTable()[i]);
            } else {
                assertEquals(expected[i], map.getTable()[i].getValue());
            }
        }

        expected = new String[]{null, "B", null, "A", null};
        assertNull(map.put(new Integer(1), "B"));
        assertEquals(2, map.size());
        for (int i = 0; i < map.getTable().length; i++) {
            if (expected[i] == null) {
                assertNull(map.getTable()[i]);
            } else {
                assertEquals(expected[i], map.getTable()[i].getValue());
            }
        }

        expected = new String[]{null, "B", "C", "A", null};
        assertNull(map.put(new Integer(7), "C"));
        assertEquals(3, map.size());
        for (int i = 0; i < map.getTable().length; i++) {
            if (expected[i] == null) {
                assertNull(map.getTable()[i]);
            } else {
                assertEquals(expected[i], map.getTable()[i].getValue());
            }
        }

        expected = new String[]{null, "B", null, "A", null, null, null, "C", "D", null, null};
        assertNull(map.put(new Integer(8), "D"));
        assertEquals(4, map.size());
        for (int i = 0; i < map.getTable().length; i++) {
            if (expected[i] == null) {
                assertNull(map.getTable()[i]);
            } else {
                assertEquals(expected[i], map.getTable()[i].getValue());
            }
        }

        expected = new String[]{null, "B", "E", "A", null, null, null, "C", "D", null, null};
        assertNull(map.put(new Integer(12), "E"));
        assertEquals(5, map.size());
        for (int i = 0; i < map.getTable().length; i++) {
            if (expected[i] == null) {
                assertNull(map.getTable()[i]);
            } else {
                assertEquals(expected[i], map.getTable()[i].getValue());
            }
        }

        expected = new String[]{null, "F", "E", "A", null, null, null, "C", "D", null, null};
        assertEquals("B", map.put(new Integer(1), "F"));
        assertEquals(5, map.size());
        for (int i = 0; i < map.getTable().length; i++) {
            if (expected[i] == null) {
                assertNull(map.getTable()[i]);
            } else {
                assertEquals(expected[i], map.getTable()[i].getValue());
            }
        }
    }

    @Test(timeout = TIMEOUT)
    public void P2() {
        String[] expected = {"A", "B", "C", null, null};
        assertNull(map.put(new Integer(0), "A"));
        assertNull(map.put(new Integer(1), "B"));
        assertNull(map.put(new Integer(2), "C"));
        assertEquals(3, map.size());
        for (int i = 0; i < map.getTable().length; i++) {
            if (expected[i] == null) {
                assertNull(map.getTable()[i]);
            } else {
                assertEquals(expected[i], map.getTable()[i].getValue());
            }
        }

        expected = new String[]{"D", "B", "C", null, null, null, null, null, null, null, null};
        assertEquals("A", map.put(new Integer(0), "D"));
        assertEquals(3, map.size());
        for (int i = 0; i < map.getTable().length; i++) {
            if (expected[i] == null) {
                assertNull(map.getTable()[i]);
            } else {
                assertEquals(expected[i], map.getTable()[i].getValue());
            }
        }
    }

    @Test(timeout = TIMEOUT)
    public void P3() {
        String[] expected = {null, null, null, null, "A"};
        assertNull(map.put(new Integer(4), "A"));
        assertEquals(1, map.size());
        for (int i = 0; i < map.getTable().length; i++) {
            if (expected[i] == null) {
                assertNull(map.getTable()[i]);
            } else {
                assertEquals(expected[i], map.getTable()[i].getValue());
            }
        }

        expected = new String[]{"B", null, null, null, "A"};
        assertNull(map.put(new Integer(9), "B"));
        assertEquals(2, map.size());
        for (int i = 0; i < map.getTable().length; i++) {
            if (expected[i] == null) {
                assertNull(map.getTable()[i]);
            } else {
                assertEquals(expected[i], map.getTable()[i].getValue());
            }
        }

        expected = new String[]{"B", "C", null, null, "A"};
        assertNull(map.put(new Integer(14), "C"));
        assertEquals(3, map.size());
        for (int i = 0; i < map.getTable().length; i++) {
            if (expected[i] == null) {
                assertNull(map.getTable()[i]);
            } else {
                assertEquals(expected[i], map.getTable()[i].getValue());
            }
        }

        expected = new String[]{null, null, null, "C", "A", null, null, null, "D", "B", null};
        assertNull(map.put(new Integer(19), "D"));
        assertEquals(4, map.size());
        for (int i = 0; i < map.getTable().length; i++) {
            if (expected[i] == null) {
                assertNull(map.getTable()[i]);
            } else {
                assertEquals(expected[i], map.getTable()[i].getValue());
            }
        }

        expected = new String[]{null, null, null, "C", "A", null, null, null, "D", "B", "E"};
        assertNull(map.put(new Integer(31), "E"));
        assertEquals(5, map.size());
        for (int i = 0; i < map.getTable().length; i++) {
            if (expected[i] == null) {
                assertNull(map.getTable()[i]);
            } else {
                assertEquals(expected[i], map.getTable()[i].getValue());
            }
        }

        expected = new String[]{"F", null, null, "C", "A", null, null, null, "D", "B", "E"};
        assertNull(map.put(new Integer(42), "F"));
        assertEquals(6, map.size());
        for (int i = 0; i < map.getTable().length; i++) {
            if (expected[i] == null) {
                assertNull(map.getTable()[i]);
            } else {
                assertEquals(expected[i], map.getTable()[i].getValue());
            }
        }

        expected = new String[]{"F", "G", null, "C", "A", null, null, null, "D", "B", "E"};
        assertNull(map.put(new Integer(0), "G"));
        assertEquals(7, map.size());
        for (int i = 0; i < map.getTable().length; i++) {
            if (expected[i] == null) {
                assertNull(map.getTable()[i]);
            } else {
                assertEquals(expected[i], map.getTable()[i].getValue());
            }
        }

        expected = new String[]{"G", null, null, null, "A", null, null, null, "E", "B", null, "H", null, null, "C", null, null, null, null, "F", "D", null, null};
        assertNull(map.put(new Integer(11), "H"));
        assertEquals(8, map.size());
        for (int i = 0; i < map.getTable().length; i++) {
            if (expected[i] == null) {
                assertNull(map.getTable()[i]);
            } else {
                assertEquals(expected[i], map.getTable()[i].getValue());
            }
        }
    }

    @Test(timeout = TIMEOUT)
    public void P4() {
        map.put(new Integer(0), "A");
        map.remove(new Integer("0"));
        map.put(new Integer(1), "B");
        map.remove(new Integer("1"));
        map.put(new Integer(2), "C");
        map.remove(new Integer("2"));
        map.put(new Integer(3), "D");
        map.remove(new Integer("3"));
        map.put(new Integer(4), "E");
        map.remove(new Integer("4"));

        map.put(new Integer(5), "F");
        String[] expected = new String[]{"F", "B_DEL", "C_DEL", "D_DEL", "E_DEL"};
        assertEquals(1, map.size());
        for (int i = 0; i < map.getTable().length; i++) {
            if (expected[i] == null) {
                assertNull(map.getTable()[i]);
            } else if (expected[i].substring(1).equals("_DEL")) {
                assertEquals(expected[i].substring(0, 1), map.getTable()[i].getValue());
                assertTrue(map.getTable()[i].isRemoved());
            } else {
                assertEquals(expected[i], map.getTable()[i].getValue());
            }
        }
    }

    @Test(timeout = TIMEOUT)
    public void P5() {
        map.put(new Integer(0), "A");
        map.put(new Integer(1), "B");
        map.put(new Integer(2), "C");
        map.resizeBackingTable(3);

        assertNull(map.put(new Integer(19), "D"));
        String[] expected = new String[]{"A", "B", "C", null, null, "D", null};
        assertEquals(4, map.size());
        for (int i = 0; i < map.getTable().length; i++) {
            if (expected[i] == null) {
                assertNull(map.getTable()[i]);
            } else if (expected[i].substring(1).equals("_DEL")) {
                assertEquals(expected[i].substring(0, 1), map.getTable()[i].getValue());
                assertTrue(map.getTable()[i].isRemoved());
            } else {
                assertEquals(expected[i], map.getTable()[i].getValue());
            }
        }
    }

    @Test(timeout = TIMEOUT)
    public void P6() {
        assertNull(map.put(new Integer(0), "A"));
        assertEquals("A", map.put(new Integer(0), "A"));
    }

    @Test(timeout = TIMEOUT)
    public void P7() {
        assertNull(map.put(new Integer(0), "A"));
        map.remove(new Integer(0));
        assertNull(map.put(new Integer(0), "A"));
    }

    /*
     * This test is designed to test negative hash values.
     * The exact ordering of the values may differ based on how you dealt with
     * the negatives. The important part is that all of the entries are there.
     */
    @Test(timeout = TIMEOUT)
    public void P8() {
        assertNull(map.put(new Integer(0), "A"));
        assertNull(map.put(new Integer(-1), "B"));
        assertNull(map.put(new Integer(1), "C"));

        String[] expected = new String[]{"A", "B", "C", null, null};
        assertEquals(3, map.size());
        for (int i = 0; i < map.getTable().length; i++) {
            if (expected[i] == null) {
                assertNull(map.getTable()[i]);
            } else if (expected[i].substring(1).equals("_DEL")) {
                assertEquals(expected[i].substring(0, 1), map.getTable()[i].getValue());
                assertTrue(map.getTable()[i].isRemoved());
            } else {
                assertEquals(expected[i], map.getTable()[i].getValue());
            }
        }
    }

    @Test(timeout = TIMEOUT)
    public void P9() {
        assertNull(map.put(new Integer(0), "A"));
        assertNull(map.put(new Integer(5), "A"));
        assertNull(map.put(new Integer(10), "A"));

        String[] expected = new String[]{"A", "A", "A", null, null};
        assertEquals(3, map.size());
        for (int i = 0; i < map.getTable().length; i++) {
            if (expected[i] == null) {
                assertNull(map.getTable()[i]);
            } else if (expected[i].substring(1).equals("_DEL")) {
                assertEquals(expected[i].substring(0, 1), map.getTable()[i].getValue());
                assertTrue(map.getTable()[i].isRemoved());
            } else {
                assertEquals(expected[i], map.getTable()[i].getValue());
            }
        }
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void e_RBA1() {
        map.resizeBackingTable(-7);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void e_RBA2() {
        map.resizeBackingTable(0);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void e_RBA3() {
        map.put(new Integer(1), "A");
        map.put(new Integer(2), "B");
        map.put(new Integer(3), "C");
        map.resizeBackingTable(2);
    }

    @Test(timeout = TIMEOUT)
    public void RBA1() {
        map.put(new Integer(17), "A");

        map.resizeBackingTable(7);
        String[] expected = new String[]{null, null, null, "A", null, null, null};
        assertEquals(1, map.size());
        for (int i = 0; i < map.getTable().length; i++) {
            if (expected[i] == null) {
                assertNull(map.getTable()[i]);
            } else if (expected[i].substring(1).equals("_DEL")) {
                assertEquals(expected[i].substring(0, 1), map.getTable()[i].getValue());
                assertTrue(map.getTable()[i].isRemoved());
            } else {
                assertEquals(expected[i], map.getTable()[i].getValue());
            }
        }

        map.put(new Integer(2), "B");
        map.remove(new Integer(2));

        map.resizeBackingTable(5);
        expected = new String[]{null, null, "A", null, null};
        assertEquals(1, map.size());
        for (int i = 0; i < map.getTable().length; i++) {
            if (expected[i] == null) {
                assertNull(map.getTable()[i]);
            } else if (expected[i].substring(1).equals("_DEL")) {
                assertEquals(expected[i].substring(0, 1), map.getTable()[i].getValue());
                assertTrue(map.getTable()[i].isRemoved());
            } else {
                assertEquals(expected[i], map.getTable()[i].getValue());
            }
        }
    }

    @Test(timeout = TIMEOUT)
    public void RBA2() {
        map.put(new Integer(17), "A");
        map.put(new Integer(2), "B");
        map.put(new Integer(5), "C");

        map.resizeBackingTable(15);
        String[] expected = new String[]{null, null, "A", "B", null, "C", null, null, null, null, null, null, null, null, null};
        assertEquals(3, map.size());
        for (int i = 0; i < map.getTable().length; i++) {
            if (expected[i] == null) {
                assertNull(map.getTable()[i]);
            } else if (expected[i].substring(1).equals("_DEL")) {
                assertEquals(expected[i].substring(0, 1), map.getTable()[i].getValue());
                assertTrue(map.getTable()[i].isRemoved());
            } else {
                assertEquals(expected[i], map.getTable()[i].getValue());
            }
        }

        map.resizeBackingTable(3);
        expected = new String[]{"B", "C", "A"};
        assertEquals(3, map.size());
        for (int i = 0; i < map.getTable().length; i++) {
            if (expected[i] == null) {
                assertNull(map.getTable()[i]);
            } else if (expected[i].substring(1).equals("_DEL")) {
                assertEquals(expected[i].substring(0, 1), map.getTable()[i].getValue());
                assertTrue(map.getTable()[i].isRemoved());
            } else {
                assertEquals(expected[i], map.getTable()[i].getValue());
            }
        }

        map.resizeBackingTable(4);
        expected = new String[]{null, "C", "B", "A"};
        assertEquals(3, map.size());
        for (int i = 0; i < map.getTable().length; i++) {
            if (expected[i] == null) {
                assertNull(map.getTable()[i]);
            } else if (expected[i].substring(1).equals("_DEL")) {
                assertEquals(expected[i].substring(0, 1), map.getTable()[i].getValue());
                assertTrue(map.getTable()[i].isRemoved());
            } else {
                assertEquals(expected[i], map.getTable()[i].getValue());
            }
        }
    }

    @Test(timeout = TIMEOUT)
    public void RBA3() {
        map.put(new Integer(0), "A");
        map.remove(new Integer("0"));
        map.put(new Integer(1), "B");
        map.remove(new Integer("1"));
        map.put(new Integer(2), "C");
        map.remove(new Integer("2"));
        map.put(new Integer(3), "D");
        map.remove(new Integer("3"));
        map.put(new Integer(4), "E");
        map.remove(new Integer("4"));

        map.resizeBackingTable(1);
        String[] expected = new String[]{null};
        assertEquals(0, map.size());
        for (int i = 0; i < map.getTable().length; i++) {
            if (expected[i] == null) {
                assertNull(map.getTable()[i]);
            } else if (expected[i].substring(1).equals("_DEL")) {
                assertEquals(expected[i].substring(0, 1), map.getTable()[i].getValue());
                assertTrue(map.getTable()[i].isRemoved());
            } else {
                assertEquals(expected[i], map.getTable()[i].getValue());
            }
        }
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void e_R1() {
        map.remove(null);
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void e_R2() {
        map = new HashMap<>();
        map.remove(null);
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void e_R3() {
        map.remove(new Integer(7));
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void e_R4() {
        map = new HashMap<>();
        map.remove(new Integer(7));
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void e_R5() {
        map.put(new Integer(2), "A");
        map.remove(new Integer(2));
        map.remove(new Integer(2));
    }

    @Test(timeout = TIMEOUT)
    public void R1() {
        assertNull(map.put(new Integer(0), "A"));
        assertNull(map.put(new Integer(1), "B"));
        assertNull(map.put(new Integer(2), "C"));

        assertEquals("B", map.remove(new Integer(1)));
        String[] expected = {"A", "B_DEL", "C", null, null};
        assertEquals(2, map.size());
        for (int i = 0; i < map.getTable().length; i++) {
            if (expected[i] == null) {
                assertNull(map.getTable()[i]);
            } else if (expected[i].substring(1).equals("_DEL")) {
                assertEquals(expected[i].substring(0, 1), map.getTable()[i].getValue());
                assertTrue(map.getTable()[i].isRemoved());
            } else {
                assertEquals(expected[i], map.getTable()[i].getValue());
            }
        }

        assertNull(map.put(new Integer(1), "D"));
        expected = new String[]{"A", "D", "C", null, null};
        assertEquals(3, map.size());
        for (int i = 0; i < map.getTable().length; i++) {
            if (expected[i] == null) {
                assertNull(map.getTable()[i]);
            } else if (expected[i].substring(1).equals("_DEL")) {
                assertEquals(expected[i].substring(0, 1), map.getTable()[i].getValue());
                assertTrue(map.getTable()[i].isRemoved());
            } else {
                assertEquals(expected[i], map.getTable()[i].getValue());
            }
        }

        assertEquals("C", map.remove(new Integer(2)));
        expected = new String[]{"A", "D", "C_DEL", null, null};
        assertEquals(2, map.size());
        for (int i = 0; i < map.getTable().length; i++) {
            if (expected[i] == null) {
                assertNull(map.getTable()[i]);
            } else if (expected[i].substring(1).equals("_DEL")) {
                assertEquals(expected[i].substring(0, 1), map.getTable()[i].getValue());
                assertTrue(map.getTable()[i].isRemoved());
            } else {
                assertEquals(expected[i], map.getTable()[i].getValue());
            }
        }

        assertNull(map.put(new Integer(3), "E"));
        expected = new String[]{"A", "D", "C_DEL", "E", null};
        assertEquals(3, map.size());
        for (int i = 0; i < map.getTable().length; i++) {
            if (expected[i] == null) {
                assertNull(map.getTable()[i]);
            } else if (expected[i].substring(1).equals("_DEL")) {
                assertEquals(expected[i].substring(0, 1), map.getTable()[i].getValue());
                assertTrue(map.getTable()[i].isRemoved());
            } else {
                assertEquals(expected[i], map.getTable()[i].getValue());
            }
        }

        assertNull(map.put(new Integer(4), "F"));
        expected = new String[]{"A", "D", null, "E", "F", null, null, null, null, null, null};
        assertEquals(4, map.size());
        for (int i = 0; i < map.getTable().length; i++) {
            if (expected[i] == null) {
                assertNull(map.getTable()[i]);
            } else if (expected[i].substring(1).equals("_DEL")) {
                assertEquals(expected[i].substring(0, 1), map.getTable()[i].getValue());
                assertTrue(map.getTable()[i].isRemoved());
            } else {
                assertEquals(expected[i], map.getTable()[i].getValue());
            }
        }
    }

    @Test(timeout = TIMEOUT)
    public void R2() {
        map.put(new Integer(0), "A");
        map.remove(new Integer("0"));
        map.put(new Integer(1), "B");
        map.remove(new Integer("1"));
        map.put(new Integer(2), "C");
        map.remove(new Integer("2"));
        map.put(new Integer(3), "D");
        map.remove(new Integer("3"));
        map.put(new Integer(4), "E");
        map.remove(new Integer("4"));

        String[] expected = new String[]{"A_DEL", "B_DEL", "C_DEL", "D_DEL", "E_DEL"};
        assertEquals(0, map.size());
        for (int i = 0; i < map.getTable().length; i++) {
            if (expected[i] == null) {
                assertNull(map.getTable()[i]);
            } else if (expected[i].substring(1).equals("_DEL")) {
                assertEquals(expected[i].substring(0, 1), map.getTable()[i].getValue());
                assertTrue(map.getTable()[i].isRemoved());
            } else {
                assertEquals(expected[i], map.getTable()[i].getValue());
            }
        }

        assertNull(map.put(new Integer(3), "F"));
        expected = new String[]{"A_DEL", "B_DEL", "C_DEL", "F", "E_DEL"};
        assertEquals(1, map.size());
        for (int i = 0; i < map.getTable().length; i++) {
            if (expected[i] == null) {
                assertNull(map.getTable()[i]);
            } else if (expected[i].substring(1).equals("_DEL")) {
                assertEquals(expected[i].substring(0, 1), map.getTable()[i].getValue());
                assertTrue(map.getTable()[i].isRemoved());
            } else {
                assertEquals(expected[i], map.getTable()[i].getValue());
            }
        }
    }

    @Test(timeout = TIMEOUT)
    public void R3() {
        map.put(new Integer(0), "A");
        map.put(new Integer(1), "B");
        map.put(new Integer(2), "C");
        map.resizeBackingTable(3);

        assertEquals("B", map.remove(new Integer(1)));
        String[] expected = new String[]{"A", "B_DEL", "C"};
        assertEquals(2, map.size());
        for (int i = 0; i < map.getTable().length; i++) {
            if (expected[i] == null) {
                assertNull(map.getTable()[i]);
            } else if (expected[i].substring(1).equals("_DEL")) {
                assertEquals(expected[i].substring(0, 1), map.getTable()[i].getValue());
                assertTrue(map.getTable()[i].isRemoved());
            } else {
                assertEquals(expected[i], map.getTable()[i].getValue());
            }
        }
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void e_G1() {
        map.get(null);
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void e_G2() {
        map.get(new Integer(7));
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void e_G3() {
        map.get(new Integer(2));
        map.remove(new Integer(2));
        map.get(new Integer(2));
    }

    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void e_G4() {
        map.put(new Integer(0), "A");
        map.remove(new Integer("0"));
        map.put(new Integer(1), "B");
        map.remove(new Integer("1"));
        map.put(new Integer(2), "C");
        map.remove(new Integer("2"));
        map.put(new Integer(3), "D");
        map.remove(new Integer("3"));
        map.put(new Integer(4), "E");
        map.remove(new Integer("4"));
        map.get(new Integer(5));
    }

    @Test
    public void G1() {
        map.put(new Integer(17), "seventeen");
        assertEquals("seventeen", map.get(new Integer(17)));
    }

    @Test(timeout = TIMEOUT)
    public void G2() {
        map.put(new Integer(0), "A");
        map.put(new Integer(1), "B");
        map.put(new Integer(2), "C");
        map.resizeBackingTable(3);

        String[] expected = new String[]{"A", "B", "C"};
        for (int i = 0; i < map.getTable().length; i++) {
            assertEquals(expected[i], map.get(new Integer(i)));
        }
    }

    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void e_CK1() {
        map.containsKey(null);
    }

    @Test(timeout = TIMEOUT)
    public void CK1() {
        map.put(new Integer(0), "A");
        map.put(new Integer(1), "B");
        map.put(new Integer(2), "C");

        assertTrue(map.containsKey(new Integer(0)));
        assertTrue(map.containsKey(new Integer(1)));
        assertTrue(map.containsKey(new Integer(2)));
        assertFalse(map.containsKey(new Integer(-1)));
        assertFalse(map.containsKey(new Integer(3)));
    }

    @Test(timeout = TIMEOUT)
    public void CK2() {
        assertFalse(map.containsKey(new Integer(7)));
        map.put(new Integer(7), "hello world");
        assertTrue(map.containsKey(new Integer(7)));
    }

    @Test(timeout = TIMEOUT)
    public void CK3() {
        map.put(new Integer(420), "blazeit");
        assertTrue(map.containsKey(new Integer(420)));
        map.remove(new Integer(420));
        assertFalse(map.containsKey(new Integer(420)));
    }

    @Test(timeout = TIMEOUT)
    public void KS1() {
        map.put(new Integer(0), "A");
        map.put(new Integer(1), "B");
        map.put(new Integer(2), "C");
        map.remove(new Integer(1));

        assertTrue(map.keySet().contains(0));
        assertFalse(map.keySet().contains(1));
        assertTrue(map.keySet().contains(2));
    }

    @Test(timeout = TIMEOUT)
    public void V1() {
        map.put(new Integer(0), "A");
        map.put(new Integer(1), "B");
        map.put(new Integer(2), "C");
        map.remove(new Integer(1));

        assertTrue(map.values().contains("A"));
        assertFalse(map.values().contains("B"));
        assertTrue(map.values().contains("C"));
    }

    @Test(timeout = TIMEOUT)
    public void V2() {
        map.put(new Integer(0), "A");
        map.put(new Integer(1), "B");
        map.put(new Integer(6), "C");
        map.put(new Integer(4), "D");
        map.remove(new Integer(1));

        String[] expected = new String[]{"A", "D", "C"};
        for (int i = 0; i < map.values().size(); i++) {
            assertEquals(expected[i], map.values().get(i));
        }
    }

    @Test(timeout = TIMEOUT)
    public void V3() {
        map.put(new Integer(0), "A");
        map.put(new Integer(1), "B");
        map.put(new Integer(6), "A");
        map.put(new Integer(4), "i like turtles");
        map.remove(new Integer(1));

        String[] expected = new String[]{"A", "i like turtles", "A"};
        for (int i = 0; i < map.values().size(); i++) {
            assertEquals(expected[i], map.values().get(i));
        }
    }

    @Test(timeout = TIMEOUT)
    public void Cl1() {
        map.put(new Integer(5), "Dear, are you getting sober?");
        map.put(new Integer(7), "Teto/ Merami");
        map.clear();
        assertEquals(0, map.size());
        for (int i = 0; i < 13; i++) {
            assertNull(map.getTable()[i]);
        }
    }
}