import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.List;


import static org.junit.Assert.*;

/**
 * My tests.
 * Boilerplate taken from official TA tests.
 *
 * @version 1.0
 * @author Vincent Huang
 */

public class Vincent_BinarySearchTrees {
    private BST<Integer> bst;
//    public static final int TIMEOUT = 200;

    private int numChildren(BSTNode node) {
        int num = 0;
        if(node.getLeft() != null) {
            num++;
        }
        if(node.getRight() != null) {
            num++;
        }
        return num;
    }

    @Before
    public void setup() {
        bst = new BST<Integer>();
    }

    @Test
    public void general() {
        assertEquals(0, bst.size());
        assertEquals(null, bst.getRoot());
    }

    @Test(expected = IllegalArgumentException.class)
    public void e_BC1() {
        bst = new BST<>(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void e_BC2() {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(5);
        list.add(3);
        list.add(null);
        list.add(8);
        bst = new BST<>(list);
    }

    @Test
    public void BC1() {
        Collection<Integer> list = new ArrayList<>();
        list.add(2);
        list.add(1);
        list.add(3);
        list.add(0);
        bst = new BST<>(list);
        /*
                      2
                     / \
                    1   3
                   /
                  0
        */
        assertEquals(4, bst.size());

        assertEquals((Integer) 2, bst.getRoot().getData());
        assertEquals(2, numChildren(bst.getRoot()));

        assertEquals((Integer) 1, bst.getRoot().getLeft().getData());
        assertEquals(1, numChildren(bst.getRoot().getLeft()));

        assertEquals((Integer) 0, bst.getRoot().getLeft().getLeft().getData());
        assertEquals(0, numChildren(bst.getRoot().getLeft().getLeft()));

        assertEquals((Integer) 3, bst.getRoot().getRight().getData());
        assertEquals(0, numChildren(bst.getRoot().getRight()));
    }

    @Test
    public void BC2() {
        Collection<Integer> set = new HashSet<>();
        set.add(15);
        set.add(34);
        set.add(420);
        set.add(198);
        set.add(9001);
        bst = new BST<>(set);

        //sets are not ordered, no single tree is guaranteed

        assertEquals(5, bst.size());

        if(numChildren(bst.getRoot()) == 2) {
            assertTrue(bst.getRoot().getLeft().getData() < bst.getRoot().getData());
            assertTrue(bst.getRoot().getRight().getData() > bst.getRoot().getData());
        }

        if(bst.getRoot().getRight() != null) {
            if(bst.getRoot().getRight().getLeft() != null)
                assertTrue(bst.getRoot().getRight().getLeft().getData() < bst.getRoot().getRight().getData());
            if(bst.getRoot().getRight().getRight() != null)
                assertTrue(bst.getRoot().getRight().getRight().getData() > bst.getRoot().getRight().getData());
        }

        if(bst.getRoot().getLeft() != null) {
            if(bst.getRoot().getLeft().getLeft() != null)
                assertTrue(bst.getRoot().getLeft().getLeft().getData() < bst.getRoot().getLeft().getData());
            if(bst.getRoot().getLeft().getRight() != null)
                assertTrue(bst.getRoot().getLeft().getRight().getData() > bst.getRoot().getLeft().getData());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void e_A1() {
        bst.add(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void e_A2() {
        bst.add(5);
        bst.add(19);
        bst.add(null);
        bst.add(17);
    }

    @Test
    public void A1() {
        bst.add(10);
        /*
                     10
        */
        assertEquals(1, bst.size());
        assertNotNull(bst.getRoot());
        assertEquals(0, numChildren(bst.getRoot()));
        assertEquals((Integer) 10, bst.getRoot().getData());

        bst.add(20);
        /*
                     10
                       \
                        20
        */
        assertEquals(2, bst.size());

        assertEquals(1, numChildren(bst.getRoot()));
        assertEquals((Integer) 10, bst.getRoot().getData());

        assertEquals(0, numChildren(bst.getRoot().getRight()));
        assertEquals((Integer) 20, bst.getRoot().getRight().getData());

        bst.add(30);
        /*
                     10
                       \
                        20
                          \
                           30
        */
        assertEquals(3, bst.size());

        assertEquals(1, numChildren(bst.getRoot()));
        assertEquals((Integer) 10, bst.getRoot().getData());

        assertEquals(1, numChildren(bst.getRoot().getRight()));
        assertEquals((Integer) 20, bst.getRoot().getRight().getData());

        assertEquals(0, numChildren(bst.getRoot().getRight().getRight()));
        assertEquals((Integer) 30, bst.getRoot().getRight().getRight().getData());

        bst.add(15);
        /*
                     10
                       \
                        20
                       /  \
                     15    30
        */
        assertEquals(4, bst.size());

        assertEquals(1, numChildren(bst.getRoot()));
        assertEquals((Integer) 10, bst.getRoot().getData());

        assertEquals(2, numChildren(bst.getRoot().getRight()));
        assertEquals((Integer) 20, bst.getRoot().getRight().getData());

        assertEquals(0, numChildren(bst.getRoot().getRight().getRight()));
        assertEquals((Integer) 30, bst.getRoot().getRight().getRight().getData());

        assertEquals(0, numChildren(bst.getRoot().getRight().getLeft()));
        assertEquals((Integer) 15, bst.getRoot().getRight().getLeft().getData());

        bst.add(5);
        /*
                     10
                   /    \
                  5     20
                       /  \
                     15    30
        */
        assertEquals(5, bst.size());

        assertEquals(2, numChildren(bst.getRoot()));
        assertEquals((Integer) 10, bst.getRoot().getData());

        assertEquals(0, numChildren(bst.getRoot().getLeft()));
        assertEquals((Integer) 5, bst.getRoot().getLeft().getData());

        assertEquals(2, numChildren(bst.getRoot().getRight()));
        assertEquals((Integer) 20, bst.getRoot().getRight().getData());

        assertEquals(0, numChildren(bst.getRoot().getRight().getRight()));
        assertEquals((Integer) 30, bst.getRoot().getRight().getRight().getData());

        assertEquals(0, numChildren(bst.getRoot().getRight().getLeft()));
        assertEquals((Integer) 15, bst.getRoot().getRight().getLeft().getData());

        bst.add(10);
        /*
                     10
                   /    \
                  5     20
                       /  \
                     15    30
        */
        assertEquals(5, bst.size());

        assertEquals(2, numChildren(bst.getRoot()));
        assertEquals((Integer) 10, bst.getRoot().getData());

        assertEquals(0, numChildren(bst.getRoot().getLeft()));
        assertEquals((Integer) 5, bst.getRoot().getLeft().getData());

        assertEquals(2, numChildren(bst.getRoot().getRight()));
        assertEquals((Integer) 20, bst.getRoot().getRight().getData());

        assertEquals(0, numChildren(bst.getRoot().getRight().getRight()));
        assertEquals((Integer) 30, bst.getRoot().getRight().getRight().getData());

        assertEquals(0, numChildren(bst.getRoot().getRight().getLeft()));
        assertEquals((Integer) 15, bst.getRoot().getRight().getLeft().getData());

        bst.add(-5);
        /*
                     10
                   /    \
                  5     20
                 /     /  \
               -5    15    30
        */
        assertEquals(6, bst.size());

        assertEquals(2, numChildren(bst.getRoot()));
        assertEquals((Integer) 10, bst.getRoot().getData());

        assertEquals(1, numChildren(bst.getRoot().getLeft()));
        assertEquals((Integer) 5, bst.getRoot().getLeft().getData());

        assertEquals(0, numChildren(bst.getRoot().getLeft().getLeft()));
        assertEquals((Integer) (-5), bst.getRoot().getLeft().getLeft().getData());

        assertEquals(2, numChildren(bst.getRoot().getRight()));
        assertEquals((Integer) 20, bst.getRoot().getRight().getData());

        assertEquals(0, numChildren(bst.getRoot().getRight().getRight()));
        assertEquals((Integer) 30, bst.getRoot().getRight().getRight().getData());

        assertEquals(0, numChildren(bst.getRoot().getRight().getLeft()));
        assertEquals((Integer) 15, bst.getRoot().getRight().getLeft().getData());

        bst.add(-5);
        /*
                     10
                   /    \
                  5     20
                 /     /  \
               -5    15    30
        */
        assertEquals(6, bst.size());

        assertEquals(2, numChildren(bst.getRoot()));
        assertEquals((Integer) 10, bst.getRoot().getData());

        assertEquals(1, numChildren(bst.getRoot().getLeft()));
        assertEquals((Integer) 5, bst.getRoot().getLeft().getData());

        assertEquals(0, numChildren(bst.getRoot().getLeft().getLeft()));
        assertEquals((Integer) (-5), bst.getRoot().getLeft().getLeft().getData());

        assertEquals(2, numChildren(bst.getRoot().getRight()));
        assertEquals((Integer) 20, bst.getRoot().getRight().getData());

        assertEquals(0, numChildren(bst.getRoot().getRight().getRight()));
        assertEquals((Integer) 30, bst.getRoot().getRight().getRight().getData());

        assertEquals(0, numChildren(bst.getRoot().getRight().getLeft()));
        assertEquals((Integer) 15, bst.getRoot().getRight().getLeft().getData());

        bst.add(20);
        /*
                     10
                   /    \
                  5     20
                 /     /  \
               -5    15    30
        */
        assertEquals(6, bst.size());

        assertEquals(2, numChildren(bst.getRoot()));
        assertEquals((Integer) 10, bst.getRoot().getData());

        assertEquals(1, numChildren(bst.getRoot().getLeft()));
        assertEquals((Integer) 5, bst.getRoot().getLeft().getData());

        assertEquals(0, numChildren(bst.getRoot().getLeft().getLeft()));
        assertEquals((Integer) (-5), bst.getRoot().getLeft().getLeft().getData());

        assertEquals(2, numChildren(bst.getRoot().getRight()));
        assertEquals((Integer) 20, bst.getRoot().getRight().getData());

        assertEquals(0, numChildren(bst.getRoot().getRight().getRight()));
        assertEquals((Integer) 30, bst.getRoot().getRight().getRight().getData());

        assertEquals(0, numChildren(bst.getRoot().getRight().getLeft()));
        assertEquals((Integer) 15, bst.getRoot().getRight().getLeft().getData());
    }

    @Test(expected = IllegalArgumentException.class)
    public void e_R1() {
        bst.remove(null);
    }

    @Test(expected = NoSuchElementException.class)
    public void e_R2() {
        bst.remove(5);
    }

    @Test(expected = NoSuchElementException.class)
    public void e_R3() {
        bst.add(5);
        bst.add(5);
        bst.remove(5);
        bst.remove(5);
    }

    @Test(expected = NoSuchElementException.class)
    public void e_R4() {
        Integer[] list = {10, 20, 30, 15, 5, -5};
        bst = new BST<>(Arrays.asList(list));
        /*
                     10
                   /    \
                  5     20
                 /     /  \
               -5    15    30
        */
        bst.remove(17);
    }

    @Test
    public void R1() {
        Integer[] list = {10, 20, 30, 15, 5, -5};
        bst = new BST<>(Arrays.asList(list));
        /*
                     10
                   /    \
                  5     20
                 /     /  \
               -5    15    30
        */

        bst.remove(-5);
        /*
                     10
                   /    \
                  5     20
                       /  \
                     15    30
        */

        assertEquals(5, bst.size());

        assertEquals(2, numChildren(bst.getRoot()));
        assertEquals((Integer) 10, bst.getRoot().getData());

        assertEquals(0, numChildren(bst.getRoot().getLeft()));
        assertEquals((Integer) 5, bst.getRoot().getLeft().getData());

        assertEquals(2, numChildren(bst.getRoot().getRight()));
        assertEquals((Integer) 20, bst.getRoot().getRight().getData());

        assertEquals(0, numChildren(bst.getRoot().getRight().getRight()));
        assertEquals((Integer) 30, bst.getRoot().getRight().getRight().getData());

        assertEquals(0, numChildren(bst.getRoot().getRight().getLeft()));
        assertEquals((Integer) 15, bst.getRoot().getRight().getLeft().getData());

        bst.remove(5);
        /*
                     10
                       \
                        20
                       /  \
                     15    30
        */
        assertEquals(4, bst.size());

        assertEquals(1, numChildren(bst.getRoot()));
        assertEquals((Integer) 10, bst.getRoot().getData());

        assertEquals(2, numChildren(bst.getRoot().getRight()));
        assertEquals((Integer) 20, bst.getRoot().getRight().getData());

        assertEquals(0, numChildren(bst.getRoot().getRight().getRight()));
        assertEquals((Integer) 30, bst.getRoot().getRight().getRight().getData());

        assertEquals(0, numChildren(bst.getRoot().getRight().getLeft()));
        assertEquals((Integer) 15, bst.getRoot().getRight().getLeft().getData());

        bst.remove(15);
        /*
                     10
                       \
                        20
                          \
                           30
        */
        assertEquals(3, bst.size());

        assertEquals(1, numChildren(bst.getRoot()));
        assertEquals((Integer) 10, bst.getRoot().getData());

        assertEquals(1, numChildren(bst.getRoot().getRight()));
        assertEquals((Integer) 20, bst.getRoot().getRight().getData());

        assertEquals(0, numChildren(bst.getRoot().getRight().getRight()));
        assertEquals((Integer) 30, bst.getRoot().getRight().getRight().getData());

        bst.remove(30);
        /*
                     10
                       \
                        20
        */
        assertEquals(2, bst.size());

        assertEquals(1, numChildren(bst.getRoot()));
        assertEquals((Integer) 10, bst.getRoot().getData());

        assertEquals(0, numChildren(bst.getRoot().getRight()));
        assertEquals((Integer) 20, bst.getRoot().getRight().getData());

        bst.remove(20);
        /*
                     10
        */
        assertEquals(1, bst.size());
        assertNotNull(bst.getRoot());
        assertEquals(0, numChildren(bst.getRoot()));
        assertEquals((Integer) 10, bst.getRoot().getData());

        bst.remove(10);
        assertEquals(0, bst.size());
        assertNull(bst.getRoot());
    }

    @Test
    public void R2() {
        Integer[] list = {10, 20, 30, 15, 5, -5};
        bst = new BST<>(Arrays.asList(list));
        /*
                     10
                   /    \
                  5     20
                 /     /  \
               -5    15    30
        */
        System.out.println(bst.levelorder());

        bst.remove(10);
        /*
                     15
                   /    \
                  5     20
                 /        \
               -5          30
        */
        System.out.println(bst.levelorder());
        assertEquals(5, bst.size());

        assertEquals(2, numChildren(bst.getRoot()));
        assertEquals((Integer) 15, bst.getRoot().getData());

        assertEquals(1, numChildren(bst.getRoot().getLeft()));
        assertEquals((Integer) 5, bst.getRoot().getLeft().getData());

        assertEquals(1, numChildren(bst.getRoot().getRight()));
        assertEquals((Integer) 20, bst.getRoot().getRight().getData());

        assertEquals(0, numChildren(bst.getRoot().getRight().getRight()));
        assertEquals((Integer) 30, bst.getRoot().getRight().getRight().getData());

        assertEquals(0, numChildren(bst.getRoot().getLeft().getLeft()));
        assertEquals((Integer) (-5), bst.getRoot().getLeft().getLeft().getData());

        bst.remove(15);
        /*
                     20
                   /    \
                  5     30
                 /
               -5
        */
        assertEquals( 4, bst.size());

        assertEquals(2, numChildren(bst.getRoot()));
        assertEquals((Integer) 20, bst.getRoot().getData());

        assertEquals(1, numChildren(bst.getRoot().getLeft()));
        assertEquals((Integer) 5, bst.getRoot().getLeft().getData());

        assertEquals(0, numChildren(bst.getRoot().getRight()));
        assertEquals((Integer) 30, bst.getRoot().getRight().getData());

        assertEquals(0, numChildren(bst.getRoot().getLeft().getLeft()));
        assertEquals((Integer) (-5), bst.getRoot().getLeft().getLeft().getData());

        bst.remove(20);
        /*
                     30
                   /
                  5
                 /
               -5
        */
        assertEquals( 3, bst.size());

        assertEquals(1, numChildren(bst.getRoot()));
        assertEquals((Integer) 30, bst.getRoot().getData());

        assertEquals(1, numChildren(bst.getRoot().getLeft()));
        assertEquals((Integer) 5, bst.getRoot().getLeft().getData());

        assertEquals(0, numChildren(bst.getRoot().getLeft().getLeft()));
        assertEquals((Integer) (-5), bst.getRoot().getLeft().getLeft().getData());

        bst.remove(30);
        /*
                     5
                    /
                  -5
        */
        assertEquals( 2, bst.size());

        assertEquals(1, numChildren(bst.getRoot()));
        assertEquals((Integer) 5, bst.getRoot().getData());

        assertEquals(0, numChildren(bst.getRoot().getLeft()));
        assertEquals((Integer) (-5), bst.getRoot().getLeft().getData());

        bst.remove(5);
        /*
                     -5
        */
        assertEquals( 1, bst.size());

        assertEquals(0, numChildren(bst.getRoot()));
        assertEquals((Integer) (-5), bst.getRoot().getData());


        bst.remove(-5);

        assertEquals(0, bst.size());
        assertNull(bst.getRoot());
    }

    @Test
    public void R3() {
        Integer[] list = {10, 20, 30, 15, 5, -5, 27, 25, 12};
        bst = new BST<>(Arrays.asList(list));
        /*
                     10
                   /    \
                  5     20
                 /     /  \
               -5    15    30
                    /     /
                  12     27
                        /
                       25
        */

        assertEquals(9, bst.size());

        assertEquals(2, numChildren(bst.getRoot()));
        assertEquals((Integer) 10, bst.getRoot().getData());

        assertEquals(1, numChildren(bst.getRoot().getLeft()));
        assertEquals((Integer) 5, bst.getRoot().getLeft().getData());

        assertEquals(0, numChildren(bst.getRoot().getLeft().getLeft()));
        assertEquals((Integer) (-5), bst.getRoot().getLeft().getLeft().getData());

        assertEquals(2, numChildren(bst.getRoot().getRight()));
        assertEquals((Integer) 20, bst.getRoot().getRight().getData());

        assertEquals(1, numChildren(bst.getRoot().getRight().getLeft()));
        assertEquals((Integer) 15, bst.getRoot().getRight().getLeft().getData());

        assertEquals(0, numChildren(bst.getRoot().getRight().getLeft().getLeft()));
        assertEquals((Integer) 12, bst.getRoot().getRight().getLeft().getLeft().getData());

        assertEquals(1, numChildren(bst.getRoot().getRight().getRight()));
        assertEquals((Integer) 30, bst.getRoot().getRight().getRight().getData());

        assertEquals(1, numChildren(bst.getRoot().getRight().getRight().getLeft()));
        assertEquals((Integer) 27, bst.getRoot().getRight().getRight().getLeft().getData());

        assertEquals(0, numChildren(bst.getRoot().getRight().getRight().getLeft().getLeft()));
        assertEquals((Integer) 25, bst.getRoot().getRight().getRight().getLeft().getLeft().getData());

        bst.remove(20);
        /*
                     10
                   /    \
                  5     25
                 /     /  \
               -5    15    30
                    /     /
                  12     27
        */

        assertEquals(8, bst.size());

        assertEquals(2, numChildren(bst.getRoot()));
        assertEquals((Integer) 10, bst.getRoot().getData());

        assertEquals(1, numChildren(bst.getRoot().getLeft()));
        assertEquals((Integer) 5, bst.getRoot().getLeft().getData());

        assertEquals(0, numChildren(bst.getRoot().getLeft().getLeft()));
        assertEquals((Integer) (-5), bst.getRoot().getLeft().getLeft().getData());

        assertEquals(2, numChildren(bst.getRoot().getRight()));
        assertEquals((Integer) 25, bst.getRoot().getRight().getData());

        assertEquals(1, numChildren(bst.getRoot().getRight().getLeft()));
        assertEquals((Integer) 15, bst.getRoot().getRight().getLeft().getData());

        assertEquals(0, numChildren(bst.getRoot().getRight().getLeft().getLeft()));
        assertEquals((Integer) 12, bst.getRoot().getRight().getLeft().getLeft().getData());

        assertEquals(1, numChildren(bst.getRoot().getRight().getRight()));
        assertEquals((Integer) 30, bst.getRoot().getRight().getRight().getData());

        assertEquals(0, numChildren(bst.getRoot().getRight().getRight().getLeft()));
        assertEquals((Integer) 27, bst.getRoot().getRight().getRight().getLeft().getData());

        bst.remove(10);
        /*
                     12
                   /    \
                  5     25
                 /     /  \
               -5    15    30
                          /
                         27
        */

        assertEquals(7, bst.size());

        assertEquals(2, numChildren(bst.getRoot()));
        assertEquals((Integer) 12, bst.getRoot().getData());

        assertEquals(1, numChildren(bst.getRoot().getLeft()));
        assertEquals((Integer) 5, bst.getRoot().getLeft().getData());

        assertEquals(0, numChildren(bst.getRoot().getLeft().getLeft()));
        assertEquals((Integer) (-5), bst.getRoot().getLeft().getLeft().getData());

        assertEquals(2, numChildren(bst.getRoot().getRight()));
        assertEquals((Integer) 25, bst.getRoot().getRight().getData());

        assertEquals(0, numChildren(bst.getRoot().getRight().getLeft()));
        assertEquals((Integer) 15, bst.getRoot().getRight().getLeft().getData());

        assertEquals(1, numChildren(bst.getRoot().getRight().getRight()));
        assertEquals((Integer) 30, bst.getRoot().getRight().getRight().getData());

        assertEquals(0, numChildren(bst.getRoot().getRight().getRight().getLeft()));
        assertEquals((Integer) 27, bst.getRoot().getRight().getRight().getLeft().getData());
    }

    @Test
    public void R4() {
        Integer[] list = {10, 5, -5, 7};
        bst = new BST<>(Arrays.asList(list));

        /*
                    10
                   /
                  5
                 / \
               -5   7
        */

        assertEquals(4, bst.size());

        assertEquals(1, numChildren(bst.getRoot()));
        assertEquals((Integer) 10, bst.getRoot().getData());

        assertEquals(2, numChildren(bst.getRoot().getLeft()));
        assertEquals((Integer) 5, bst.getRoot().getLeft().getData());

        assertEquals(0, numChildren(bst.getRoot().getLeft().getLeft()));
        assertEquals((Integer) (-5), bst.getRoot().getLeft().getLeft().getData());

        assertEquals(0, numChildren(bst.getRoot().getLeft().getRight()));
        assertEquals((Integer) 7, bst.getRoot().getLeft().getRight().getData());

        bst.remove(10);
        /*
                    5
                   / \
                 -5   7
        */
        assertEquals(3, bst.size());

        assertEquals(2, numChildren(bst.getRoot()));
        assertEquals((Integer) 5, bst.getRoot().getData());

        assertEquals(0, numChildren(bst.getRoot().getLeft()));
        assertEquals((Integer) (-5), bst.getRoot().getLeft().getData());

        assertEquals(0, numChildren(bst.getRoot().getRight()));
        assertEquals((Integer) 7, bst.getRoot().getRight().getData());
    }

    @Test(expected = IllegalArgumentException.class)
    public void e_G1() {
        bst.get(null);
    }

    @Test(expected = NoSuchElementException.class)
    public void e_G2() {
        bst.get(0);
    }

    @Test(expected = NoSuchElementException.class)
    public void e_G3() {
        Integer[] list = {10, 20, 30, 15, 5, -5};
        bst = new BST<>(Arrays.asList(list));
        /*
                     10
                   /    \
                  5     20
                 /     /  \
               -5    15    30
        */
        bst.get(17);
    }

    @Test
    public void G1() {
        Integer[] list = {10, 5, -5, 25, 15, 12, 30, 27};
        bst = new BST<>(Arrays.asList(list));
        /*
                     10
                   /    \
                  5     25
                 /     /  \
               -5    15    30
                    /     /
                  12     27
        */
        assertEquals((Integer) 10, bst.get(10));
        Integer ten = new Integer(10);
        assertFalse(ten == bst.get(ten));

        assertEquals((Integer) (-5), bst.get(-5));
        Integer nFive = new Integer(-5);
        assertFalse(nFive == bst.get(nFive));

        assertEquals((Integer) 30, bst.get(30));
        Integer thirty = new Integer(30);
        assertFalse(thirty == bst.get(thirty));

        assertEquals((Integer) 15, bst.get(15));
        Integer fifteen = new Integer(15);
        assertFalse(fifteen == bst.get(fifteen));
    }

    @Test(expected = IllegalArgumentException.class)
    public void e_C1() {
        bst.contains(null);
    }

    @Test
    public void C1() {
        Integer[] list = {10, 5, -5, 25, 15, 12, 30, 27};
        bst = new BST<>(Arrays.asList(list));
        /*
                     10
                   /    \
                  5     25
                 /     /  \
               -5    15    30
                    /     /
                  12     27
        */
        for(int i = 0; i < list.length; i++) {
            assertTrue(bst.contains(list[i]));
        }
        assertFalse(bst.contains(-9));
        assertFalse(bst.contains(108));
        assertFalse(bst.contains(0));
        assertFalse(bst.contains(9001));
        assertFalse(bst.contains(-6));
    }

    @Test
    public void PrO1() {
        Integer[] list = {10, 5, -5, 25, 15, 12, 30, 27};
        bst = new BST<>(Arrays.asList(list));
        /*
                     10
                   /    \
                  5     25
                 /     /  \
               -5    15    30
                    /     /
                  12     27
        */
        List<Integer> result = bst.preorder();
        Integer[] expected = {10, 5, -5, 25, 15, 12, 30, 27};
        for(int i = 0; i < list.length; i++) {
            assertEquals(expected[i], result.get(i));
        }
    }

    @Test
    public void PrO2() {
        Integer[] list = {8, 4, 2, 1, 3, 6, 5, 7, 12, 10, 9, 11, 14, 13, 15};
        bst = new BST<>(Arrays.asList(list));
        /*
                       8
                   /       \
                 4          12
               /   \       /   \
             2      6     10   14
            / \    / \   / \   / \
           1   3  5  7  9 11  13 15
        */
        List<Integer> result = bst.preorder();
        Integer[] expected = {8, 4, 2, 1, 3, 6, 5, 7, 12, 10, 9, 11, 14, 13, 15};
        for(int i = 0; i < list.length; i++) {
            assertEquals(expected[i], result.get(i));
        }
    }

    @Test
    public void PrO3() {
        assertTrue(bst.preorder().isEmpty());
    }

    @Test
    public void PrO4() {
        bst.add(123456789);
        assertEquals((Integer)123456789, bst.preorder().get(0));
        assertEquals(1, bst.preorder().size());
    }

    @Test
    public void PoO1() {
        Integer[] list = {10, 5, -5, 25, 15, 12, 30, 27};
        bst = new BST<>(Arrays.asList(list));
        /*
                     10
                   /    \
                  5     25
                 /     /  \
               -5    15    30
                    /     /
                  12     27
        */
        List<Integer> result = bst.postorder();
        Integer[] expected = {-5, 5, 12, 15, 27, 30, 25, 10};
        for(int i = 0; i < list.length; i++) {
            assertEquals(expected[i], result.get(i));
        }
    }

    @Test
    public void PoO2() {
        Integer[] list = {8, 4, 2, 1, 3, 6, 5, 7, 12, 10, 9, 11, 14, 13, 15};
        bst = new BST<>(Arrays.asList(list));
        /*
                       8
                   /       \
                 4          12
               /   \       /   \
             2      6     10   14
            / \    / \   / \   / \
           1   3  5  7  9 11  13 15
        */
        List<Integer> result = bst.postorder();
        Integer[] expected = {1, 3, 2, 5, 7, 6, 4, 9, 11, 10, 13, 15, 14, 12, 8};
        for(int i = 0; i < list.length; i++) {
            assertEquals(expected[i], result.get(i));
        }
    }

    @Test
    public void PoO3() {
        assertTrue(bst.postorder().isEmpty());
    }

    @Test
    public void PoO4() {
        bst.add(123456789);
        assertEquals((Integer) 123456789, bst.postorder().get(0));
        assertEquals(1, bst.postorder().size());
    }

    @Test
    public void IO1() {
        Integer[] list = {10, 5, -5, 25, 15, 12, 30, 27};
        bst = new BST<>(Arrays.asList(list));
        /*
                     10
                   /    \
                  5     25
                 /     /  \
               -5    15    30
                    /     /
                  12     27
        */
        List<Integer> result = bst.inorder();
        Integer[] expected = {-5, 5, 10, 12, 15, 25, 27, 30};
        for(int i = 0; i < list.length; i++) {
            assertEquals(expected[i], result.get(i));
        }
    }

    @Test
    public void IO2() {
        Integer[] list = {8, 4, 2, 1, 3, 6, 5, 7, 12, 10, 9, 11, 14, 13, 15};
        bst = new BST<>(Arrays.asList(list));
        /*
                       8
                   /       \
                 4          12
               /   \       /   \
             2      6     10   14
            / \    / \   / \   / \
           1   3  5  7  9 11  13 15
        */
        List<Integer> result = bst.inorder();
        Integer[] expected = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
        for(int i = 0; i < list.length; i++) {
            assertEquals(expected[i], result.get(i));
        }
    }

    @Test
    public void IO3() {
        assertTrue(bst.inorder().isEmpty());
    }

    @Test
    public void IO4() {
        bst.add(123456789);
        assertEquals((Integer) 123456789, bst.inorder().get(0));
        assertEquals(1, bst.inorder().size());
    }

    @Test
    public void LO1() {
        Integer[] list = {10, 5, -5, 25, 15, 12, 30, 27};
        bst = new BST<>(Arrays.asList(list));
        /*
                     10
                   /    \
                  5     25
                 /     /  \
               -5    15    30
                    /     /
                  12     27
        */
        List<Integer> result = bst.levelorder();
        Integer[] expected = {10, 5, 25, -5, 15, 30, 12, 27};
        for(int i = 0; i < list.length; i++) {
            assertEquals(expected[i], result.get(i));
        }
    }

    @Test
    public void LO2() {
        Integer[] list = {8, 4, 2, 1, 3, 6, 5, 7, 12, 10, 9, 11, 14, 13, 15};
        bst = new BST<>(Arrays.asList(list));
        /*
                       8
                   /       \
                 4          12
               /   \       /   \
             2      6     10   14
            / \    / \   / \   / \
           1   3  5  7  9 11  13 15
        */
        List<Integer> result = bst.levelorder();
        Integer[] expected = {8, 4, 12, 2, 6, 10, 14, 1, 3, 5, 7, 9, 11, 13, 15};
        for(int i = 0; i < list.length; i++) {
            assertEquals(expected[i], result.get(i));
        }
    }

    @Test
    public void LO3() {
        assertTrue(bst.levelorder().isEmpty());
    }

    @Test
    public void LO4() {
        bst.add(123456789);
        assertEquals((Integer) 123456789, bst.levelorder().get(0));
        assertEquals(1, bst.levelorder().size());
    }

    @Test
    public void C() {
        Integer[] list = {8, 4, 2, 1, 3, 6, 5, 7, 12, 10, 9, 11, 14, 13, 15};
        bst = new BST<>(Arrays.asList(list));
        /*
                       8
                   /       \
                 4          12
               /   \       /   \
             2      6     10   14
            / \    / \   / \   / \
           1   3  5  7  9 11  13 15
        */
        bst.clear();
        assertEquals(0, bst.size());
        assertNull(bst.getRoot());
    }

    @Test
    public void H1() {
        Integer[] list = {10, 5, -5, 25, 15, 12, 30, 27};
        bst = new BST<>(Arrays.asList(list));
        /*
                     10
                   /    \
                  5     25
                 /     /  \
               -5    15    30
                    /     /
                  12     27
        */
        assertEquals(3, bst.height());
    }

    @Test
    public void H2() {
        assertEquals(-1, bst.height());
    }

    @Test
    public void H3() {
        bst.add(123456789);
        assertEquals(0, bst.height());
    }

    @Test
    public void H4() {
        bst.add(1);
        bst.add(2);
        assertEquals(1, bst.height());
    }
}