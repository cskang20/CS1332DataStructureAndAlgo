import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * A set of basic tests to test your AVL implementation.
 *
 * These tests are NOT exhaustive. Write your own tests to ensure code coverage.
 *
 * @author CS 1332 TAs
 * @version 1.0
 */
public class AVLStudentTests {
    private static final int TIMEOUT = 2000;
    private AVL<Integer> avlTree;

    @Before
    public void setup() {
        avlTree = new AVL<>();
    }

    @Test
    public void testAddRightRotation() {
        /*
                    5                   4
                   /                   / \
                  4         ->        3   5
                 /
                3
         */
        avlTree.add(5);
        avlTree.add(4);
        avlTree.add(3);


        assertEquals(3, avlTree.size());

        AVLNode<Integer> root = avlTree.getRoot();
        assertEquals((Integer) 4, root.getData());
        assertEquals(1, root.getHeight());
        assertEquals(0, root.getBalanceFactor());
        assertEquals((Integer) 3, root.getLeft().getData());
        assertEquals(0, root.getLeft().getHeight());
        assertEquals(0, root.getLeft().getBalanceFactor());
        assertEquals((Integer) 5, root.getRight().getData());
        assertEquals(0, root.getRight().getHeight());
        assertEquals(0, root.getRight().getBalanceFactor());
    }


    @Test
    public void testAddRightLeftRotationRoot() {
        /*
                3               4
                 \             / \
                  5     ->    3   5
                 /
                4
         */
        avlTree.add(3);
        avlTree.add(5);
        avlTree.add(4);
        assertEquals(3, avlTree.size());
        AVLNode<Integer> root = avlTree.getRoot();
        assertEquals((Integer) 4, root.getData());
        assertEquals(1, root.getHeight());
        assertEquals(0, root.getBalanceFactor());
        assertEquals((Integer) 3, root.getLeft().getData());
        assertEquals(0, root.getLeft().getHeight());
        assertEquals(0, root.getLeft().getBalanceFactor());
        assertEquals((Integer) 5, root.getRight().getData());
        assertEquals(0, root.getRight().getHeight());
        assertEquals(0, root.getRight().getBalanceFactor());
    }



    @Test
    public void testRemove() {
        /*
                    646                     646
                   /   \                   /   \
                 477   856      ->       386   856
                 / \                       \
               386 526                     526
         */
        Integer toBeRemoved = new Integer(477);
        avlTree.add(646);
        avlTree.add(toBeRemoved);
        avlTree.add(856);
        avlTree.add(386);
        avlTree.add(526);

        assertSame(toBeRemoved, avlTree.remove(new Integer(477)));

        assertEquals(4, avlTree.size());

        AVLNode<Integer> root = avlTree.getRoot();
        assertEquals((Integer) 646, root.getData());
        assertEquals(2, root.getHeight());
        assertEquals(1, root.getBalanceFactor());
        assertEquals((Integer) 386, root.getLeft().getData());
        assertEquals(1, root.getLeft().getHeight());
        assertEquals(-1, root.getLeft().getBalanceFactor());
        assertEquals((Integer) 856, root.getRight().getData());
        assertEquals(0, root.getRight().getHeight());
        assertEquals(0, root.getRight().getBalanceFactor());
        assertEquals((Integer) 526, root.getLeft().getRight().getData());
        assertEquals(0, root.getLeft().getRight().getHeight());
        assertEquals(0, root.getLeft().getRight().getBalanceFactor());
    }
//    @Test
//    public void testRemove() {
//        /*
//                    646                     646
//                   /   \                   /   \
//                 477   856      ->       386   856
//                 / \                       \
//               386 526                     526
//         */
//        Integer toBeRemoved = new Integer(477);
//        avlTree.add(646);
//        avlTree.add(toBeRemoved);
//        avlTree.add(856);
//        avlTree.add(386);
//        avlTree.add(526);
//
//        assertSame(toBeRemoved, avlTree.remove(new Integer(477)));
//
//        assertEquals(4, avlTree.size());
//
//        AVLNode<Integer> root = avlTree.getRoot();
//        assertEquals((Integer) 646, root.getData());
//        assertEquals(2, root.getHeight());
//        assertEquals(1, root.getBalanceFactor());
//        assertEquals((Integer) 386, root.getLeft().getData());
//        assertEquals(1, root.getLeft().getHeight());
//        assertEquals(-1, root.getLeft().getBalanceFactor());
//        assertEquals((Integer) 856, root.getRight().getData());
//        assertEquals(0, root.getRight().getHeight());
//        assertEquals(0, root.getRight().getBalanceFactor());
//        assertEquals((Integer) 526, root.getLeft().getRight().getData());
//        assertEquals(0, root.getLeft().getRight().getHeight());
//        assertEquals(0, root.getLeft().getRight().getBalanceFactor());
//    }

    @Test
    public void testGet() {
        /*
                        477
                       /   \
                     386   526
                              \
                              646
         */
        Integer maximum = new Integer(646);
        avlTree.add(477);
        avlTree.add(526);
        avlTree.add(386);
        avlTree.add(maximum);

        assertSame(maximum, avlTree.get(new Integer(646)));
    }

    @Test
    public void testContains() {
        /*
                        477
                       /   \
                     386   526
                              \
                              646
         */
        avlTree.add(new Integer(477));
        avlTree.add(new Integer(526));
        avlTree.add(new Integer(386));
        avlTree.add(new Integer(646));

        assertEquals(true, avlTree.contains(new Integer(477)));
        assertEquals(true, avlTree.contains(new Integer(386)));
        assertEquals(true, avlTree.contains(new Integer(646)));
        assertEquals(false, avlTree.contains(new Integer(387)));
        assertEquals(false, avlTree.contains(new Integer(700)));
        assertEquals(false, avlTree.contains(new Integer(500)));
    }


    @Test
    public void testHeight() {
        /*
                    646
                   /   \
                 386   856
                 / \
               526 477
         */
        avlTree.add(646);
        avlTree.add(386);
        avlTree.add(856);
        avlTree.add(526);
        avlTree.add(477);

        assertEquals(2, avlTree.height());
    }

    @Test
    public void testConstructorAndClear() {
        /*
                     7
                    / \
                   1   24
        */

        List<Integer> toAdd = new ArrayList<>();
        toAdd.add(7);
        toAdd.add(24);
        toAdd.add(1);
        avlTree = new AVL<>(toAdd);

        assertEquals((Integer) 7, avlTree.getRoot().getData());
        assertEquals(1, avlTree.getRoot().getHeight());
        assertEquals(0, avlTree.getRoot().getBalanceFactor());
        assertEquals((Integer) 1, avlTree.getRoot().getLeft().getData());
        assertEquals(0, avlTree.getRoot().getLeft().getHeight());
        assertEquals(0, avlTree.getRoot().getLeft().getBalanceFactor());
        assertEquals((Integer) 24, avlTree.getRoot().getRight().getData());
        assertEquals(0, avlTree.getRoot().getRight().getHeight());
        assertEquals(0, avlTree.getRoot().getRight().getBalanceFactor());

        avlTree.clear();
        assertEquals(null, avlTree.getRoot());
        assertEquals(0, avlTree.size());
    }
}