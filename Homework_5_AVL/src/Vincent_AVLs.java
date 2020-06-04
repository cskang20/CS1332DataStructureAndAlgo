import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Arrays;

/**
 * My tests
 *
 * @author Vincent Huang
 * @version 1.0
 */

public class Vincent_AVLs {
    private static final int TIMEOUT = 200;
    private AVL<Integer> avl;
    private AVLNode<Integer> node;

    private int numChildren(AVLNode<Integer> node) {
        int num = 0;
        if(node.getLeft() != null) {
            num++;
        }
        if(node.getRight() != null) {
            num++;
        }
        return num;
    }

    private void validate(AVLNode<Integer> node, int height, int balanceFactor, int numChildren, Integer data) {
        assertEquals(height, node.getHeight());
        assertEquals(balanceFactor, node.getBalanceFactor());
        assertEquals(numChildren, numChildren(node));
        assertEquals(data, node.getData());
    }

    @Before
    public void setUp() {
        avl = new AVL<Integer>();
    }

    @Test(expected = IllegalArgumentException.class)
    public void e_Cnstr1() {
        avl = new AVL<>(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void e_Cnstr2() {
        ArrayList<Integer> arr = new ArrayList<>();
        arr.add(17);
        arr.add(27);
        arr.add(null);
        arr.add(-5);
        avl = new AVL<>(arr);
    }

    @Test
    public void Cnstr1() {
        Collection<Integer> list = new ArrayList<>();
        list.add(2);
        list.add(1);
        list.add(3);
        list.add(0);
        avl = new AVL<>(list);
        /*
                      2
                     / \
                    1   3
                   /
                  0
        */
        assertEquals(4, avl.size());

        node = avl.getRoot();
        validate(node, 2, 1, 2, 2);

        node = avl.getRoot().getLeft();
        validate(node, 1, 1, 1, 1);

        node = avl.getRoot().getLeft().getLeft();
        validate(node, 0, 0, 0, 0);

        node = avl.getRoot().getRight();
        validate(node, 0, 0, 0, 3);
    }

    @Test
    public void Cnstr2() {
        Collection<Integer> set = new HashSet<>();
        set.add(15);
        set.add(34);
        set.add(420);
        set.add(198);
        set.add(9001);
        avl = new AVL<>(set);

        //sets are not ordered, no single tree is guaranteed

        assertEquals(5, avl.size());

        if(numChildren(avl.getRoot()) == 2) {
            assertTrue(avl.getRoot().getLeft().getData() < avl.getRoot().getData());
            assertTrue(avl.getRoot().getRight().getData() > avl.getRoot().getData());
        }

        if(avl.getRoot().getRight() != null) {
            if(avl.getRoot().getRight().getLeft() != null)
                assertTrue(avl.getRoot().getRight().getLeft().getData() < avl.getRoot().getRight().getData());
            if(avl.getRoot().getRight().getRight() != null)
                assertTrue(avl.getRoot().getRight().getRight().getData() > avl.getRoot().getRight().getData());
        }

        if(avl.getRoot().getLeft() != null) {
            if(avl.getRoot().getLeft().getLeft() != null)
                assertTrue(avl.getRoot().getLeft().getLeft().getData() < avl.getRoot().getLeft().getData());
            if(avl.getRoot().getLeft().getRight() != null)
                assertTrue(avl.getRoot().getLeft().getRight().getData() > avl.getRoot().getLeft().getData());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void e_A1() {
        avl.add(7);
        avl.add(null);
    }

    @Test
    public void A1() {
        avl.add(10);
        /*
                     10
        */
        assertEquals(1, avl.size());
        assertNotNull(avl.getRoot());
        node = avl.getRoot();
        validate(node, 0, 0, 0, 10);

        avl.add(20);
        /*
                     10
                       \
                        20
        */
        assertEquals(2, avl.size());

        node = avl.getRoot();
        assertEquals(1, numChildren(node));
        assertEquals((Integer) 10, node.getData());
        assertEquals(1, node.getHeight());
        assertEquals(-1, node.getBalanceFactor());

        node = avl.getRoot().getRight();
        assertEquals(0, numChildren(node));
        assertEquals((Integer) 20, node.getData());
        assertEquals(0, node.getHeight());
        assertEquals(0, node.getBalanceFactor());

        avl.add(30);
        /*
                     20
                    /  \
                   10   30
        */
        assertEquals(3, avl.size());

        assertEquals(2, numChildren(avl.getRoot()));
        assertEquals((Integer) 20, avl.getRoot().getData());
        assertEquals(1, avl.getRoot().getHeight());
        assertEquals(0, avl.getRoot().getBalanceFactor());

        assertEquals(0, numChildren(avl.getRoot().getLeft()));
        assertEquals((Integer) 10, avl.getRoot().getLeft().getData());
        assertEquals(0, avl.getRoot().getLeft().getHeight());
        assertEquals(0, avl.getRoot().getLeft().getBalanceFactor());

        assertEquals(0, numChildren(avl.getRoot().getRight()));
        assertEquals((Integer) 30, avl.getRoot().getRight().getData());
        assertEquals(0, avl.getRoot().getRight().getHeight());
        assertEquals(0, avl.getRoot().getRight().getBalanceFactor());

        avl.add(15);
        /*
                     20
                    /  \
                   10   30
                     \
                      15
        */
        assertEquals(4, avl.size());

        assertEquals(2, numChildren(avl.getRoot()));
        assertEquals((Integer) 20, avl.getRoot().getData());
        assertEquals(2, avl.getRoot().getHeight());
        assertEquals(1, avl.getRoot().getBalanceFactor());

        assertEquals(1, numChildren(avl.getRoot().getLeft()));
        assertEquals((Integer) 10, avl.getRoot().getLeft().getData());
        assertEquals(1, avl.getRoot().getLeft().getHeight());
        assertEquals(-1, avl.getRoot().getLeft().getBalanceFactor());

        assertEquals(0, numChildren(avl.getRoot().getRight()));
        assertEquals((Integer) 30, avl.getRoot().getRight().getData());
        assertEquals(0, avl.getRoot().getRight().getHeight());
        assertEquals(0, avl.getRoot().getRight().getBalanceFactor());

        assertEquals(0, numChildren(avl.getRoot().getLeft().getRight()));
        assertEquals((Integer) 15, avl.getRoot().getLeft().getRight().getData());
        assertEquals(0, avl.getRoot().getLeft().getRight().getHeight());
        assertEquals(0, avl.getRoot().getLeft().getRight().getBalanceFactor());

        avl.add(40);
        /*
                     20
                    /  \
                   10   30
                     \    \
                      15   40
        */
        assertEquals(5, avl.size());

        assertEquals(2, numChildren(avl.getRoot()));
        assertEquals((Integer) 20, avl.getRoot().getData());
        assertEquals(2, avl.getRoot().getHeight());
        assertEquals(0, avl.getRoot().getBalanceFactor());

        assertEquals(1, numChildren(avl.getRoot().getLeft()));
        assertEquals((Integer) 10, avl.getRoot().getLeft().getData());
        assertEquals(1, avl.getRoot().getLeft().getHeight());
        assertEquals(-1, avl.getRoot().getLeft().getBalanceFactor());

        assertEquals(1, numChildren(avl.getRoot().getRight()));
        assertEquals((Integer) 30, avl.getRoot().getRight().getData());
        assertEquals(1, avl.getRoot().getRight().getHeight());
        assertEquals(-1, avl.getRoot().getRight().getBalanceFactor());

        assertEquals(0, numChildren(avl.getRoot().getRight().getRight()));
        assertEquals((Integer) 40, avl.getRoot().getRight().getRight().getData());
        assertEquals(0, avl.getRoot().getRight().getRight().getHeight());
        assertEquals(0, avl.getRoot().getRight().getRight().getBalanceFactor());

        assertEquals(0, numChildren(avl.getRoot().getLeft().getRight()));
        assertEquals((Integer) 15, avl.getRoot().getLeft().getRight().getData());
        assertEquals(0, avl.getRoot().getRight().getRight().getHeight());
        assertEquals(0, avl.getRoot().getRight().getRight().getBalanceFactor());

        avl.add(35);
        /*
                      20
                    /    \
                   10     30
                     \      \
                      15     40
                            /
                          35
                      20
                    /    \
                   10     30
                     \      \
                      15     35
                               \
                                40
                      20
                    /    \
                   10     35
                     \    / \
                      15 30  40
        */
        assertEquals(6, avl.size());

        assertEquals(2, numChildren(avl.getRoot()));
        assertEquals((Integer) 20, avl.getRoot().getData());
        assertEquals(2, avl.getRoot().getHeight());
        assertEquals(0, avl.getRoot().getBalanceFactor());

        assertEquals(1, numChildren(avl.getRoot().getLeft()));
        assertEquals((Integer) 10, avl.getRoot().getLeft().getData());
        assertEquals(1, avl.getRoot().getLeft().getHeight());
        assertEquals(-1, avl.getRoot().getLeft().getBalanceFactor());

        assertEquals(0, numChildren(avl.getRoot().getLeft().getRight()));
        assertEquals((Integer) 15, avl.getRoot().getLeft().getRight().getData());
        assertEquals(0, avl.getRoot().getLeft().getRight().getHeight());
        assertEquals(0, avl.getRoot().getLeft().getRight().getBalanceFactor());

        assertEquals(2, numChildren(avl.getRoot().getRight()));
        assertEquals((Integer) 35, avl.getRoot().getRight().getData());

        assertEquals(0, numChildren(avl.getRoot().getRight().getLeft()));
        assertEquals((Integer) 30, avl.getRoot().getRight().getLeft().getData());
        assertEquals(0, avl.getRoot().getRight().getLeft().getHeight());
        assertEquals(0, avl.getRoot().getRight().getLeft().getBalanceFactor());

        assertEquals(0, numChildren(avl.getRoot().getRight().getRight()));
        assertEquals((Integer) 40, avl.getRoot().getRight().getRight().getData());
        assertEquals(0, avl.getRoot().getRight().getLeft().getHeight());
        assertEquals(0, avl.getRoot().getRight().getLeft().getBalanceFactor());
    }

    @Test(expected = IllegalArgumentException.class)
    public void e_R1() {
        avl.remove(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void e_R2() {
        avl.add(29);
        avl.remove(null);
    }
    @Test(expected = NoSuchElementException.class)
    public void e_R3() {
        avl.add(29);
        avl.remove(7);
    }

    @Test(expected = NoSuchElementException.class)
    public void e_R4() {
        avl.add(29);
        avl.add(7);
        avl.remove(7);
        avl.remove(7);
    }

    @Test
    public void R1() {
        Integer[] list = {10, 20, 30, 15, 5, -5};
        avl = new AVL<>(Arrays.asList(list));
        /*
                     10
                   /    \
                  5     20
                 /     /  \
               -5    15    30
        */

        assertEquals((Integer) (-5), avl.remove(-5));
        /*
                     10
                   /    \
                  5     20
                       /  \
                     15    30
        */

        assertEquals(5, avl.size());

        node = avl.getRoot();
        validate(node, 2, -1, 2, 10);

        node = avl.getRoot().getLeft();
        validate(node, 0, 0, 0, 5);

        node = avl.getRoot().getRight();
        validate(node, 1, 0, 2, 20);

        node = avl.getRoot().getRight().getLeft();
        validate(node, 0, 0, 0, 15);

        node = avl.getRoot().getRight().getRight();
        validate(node, 0, 0, 0, 30);

        assertEquals((Integer) 5, avl.remove(5));
        /*
                     10
                        \
                        20
                       /  \
                     15    30
                     20
                    /   \
                   10    30
                     \
                     15
        */

        assertEquals(4, avl.size());

        node = avl.getRoot();
        validate(node, 2, 1, 2, 20);

        node = avl.getRoot().getLeft();
        validate(node, 1, -1, 1, 10);

        node = avl.getRoot().getLeft().getRight();
        validate(node, 0, 0, 0, 15);

        node = avl.getRoot().getRight();
        validate(node, 0, 0, 0, 30);

        assertEquals((Integer) 30, avl.remove(30));
        /*
                     20
                    /
                   10
                     \
                     15
                     20
                    /
                   15
                  /
                10
                   15
                  /  \
                10    20
        */

        assertEquals(3, avl.size());

        node = avl.getRoot();
        validate(node, 1, 0, 2, 15);

        node = avl.getRoot().getLeft();
        validate(node, 0, 0, 0, 10);

        node = avl.getRoot().getRight();
        validate(node, 0, 0, 0, 20);

        avl.remove(15);
        /*
                   10
                     \
                      20
        */

        assertEquals(2, avl.size());

        node = avl.getRoot();
        validate(node, 1, -1, 1, 10);

        node = avl.getRoot().getRight();
        validate(node, 0, 0, 0, 20);

        avl.remove(10);
        /*
                      20
        */

        assertEquals(1, avl.size());

        node = avl.getRoot();
        validate(node, 0, 0, 0, 20);

        avl.remove(20);
        node = avl.getRoot();
        assertNull(node);
        assertEquals(0, avl.size());
    }

    @Test
    public void R2() {
        Integer[] list = {10, 20, 30, 15, 5, -5};
        avl = new AVL<>(Arrays.asList(list));
        /*
                     10
                   /    \
                  5     20
                 /     /  \
               -5    15    30
        */

        assertEquals(6, avl.size());

        node = avl.getRoot();
        validate(node, 2, 0, 2, 10);

        node = avl.getRoot().getLeft();
        validate(node, 1, 1, 1, 5);

        node = avl.getRoot().getLeft().getLeft();
        validate(node, 0, 0, 0, -5);

        node = avl.getRoot().getRight();
        validate(node, 1, 0, 2, 20);

        node = avl.getRoot().getRight().getLeft();
        validate(node, 0, 0, 0, 15);

        node = avl.getRoot().getRight().getRight();
        validate(node, 0, 0, 0, 30);

        assertEquals((Integer) 15, avl.remove(15));
        /*
                     10
                   /    \
                  5     20
                 /        \
               -5         30
        */

        assertEquals(5, avl.size());

        node = avl.getRoot();
        validate(node, 2, 0, 2, 10);

        node = avl.getRoot().getLeft();
        validate(node, 1, 1, 1, 5);

        node = avl.getRoot().getLeft().getLeft();
        validate(node, 0, 0, 0, -5);

        node = avl.getRoot().getRight();
        validate(node, 1, -1, 1, 20);

        node = avl.getRoot().getRight().getRight();
        validate(node, 0, 0, 0, 30);

        assertEquals((Integer) 10, avl.remove(10));
        /*
                     5
                   /   \
                  -5    20
                          \
                          30
        */

        assertEquals(4, avl.size());

        node = avl.getRoot();
        validate(node, 2, -1, 2, 5);

        node = avl.getRoot().getLeft();
        validate(node, 0, 0, 0, -5);

        node = avl.getRoot().getRight();
        validate(node, 1, -1, 1, 20);

        node = avl.getRoot().getRight().getRight();
        validate(node, 0, 0, 0, 30);

        assertEquals((Integer) 5, avl.remove(5));
        /*
                     -5
                        \
                        20
                          \
                          30
                        20
                       /  \
                     -5   30
        */

        assertEquals(3, avl.size());

        node = avl.getRoot();
        validate(node, 1, 0, 2, 20);

        node = avl.getRoot().getLeft();
        validate(node, 0, 0, 0, -5);

        node = avl.getRoot().getRight();
        validate(node, 0, 0, 0, 30);

        assertEquals((Integer) 20, avl.remove(20));
        /*
                        -5
                          \
                          30
        */

        assertEquals(2, avl.size());

        node = avl.getRoot();
        validate(node, 1, -1, 1, -5);

        node = avl.getRoot().getRight();
        validate(node, 0, 0, 0, 30);


    }

    @Test(expected = IllegalArgumentException.class)
    public void e_G1() {
        avl.get(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void e_G2() {
        avl.add(29);
        avl.get(null);
    }

    @Test(expected = NoSuchElementException.class)
    public void e_G3() {
        avl.get(17);
    }

    @Test(expected = NoSuchElementException.class)
    public void e_G4() {
        avl.add(29);
        avl.get(17);
    }

    @Test(expected = NoSuchElementException.class)
    public void e_G5() {
        avl.add(17);
        avl.get(17);
        avl.remove(17);
        avl.get(17);
    }

    @Test(expected = IllegalArgumentException.class)
    public void e_C1() {
        avl.contains(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void e_C2() {
        avl.add(19);
        avl.contains(null);
    }

    @Test
    public void H1() {
        assertEquals(-1, avl.height());
    }

    @Test
    public void H2() {
        avl.add(19999);
        assertEquals(0, avl.height());
        avl.remove(19999);
        assertEquals(-1, avl.height());
    }


}