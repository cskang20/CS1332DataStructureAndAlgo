
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Tests Yo! Because you know, you need to test yo
 * stuff. Let me know if I messed up somewhere
 *
 * @author Milan Patel
 */
public class Milan_AVLTests {

    private static final int TIMEOUT = 1000;
    private AVL<Integer> avlTree;

    private boolean isLeafNode(AVLNode curr) {
        return !hasLeft(curr) && !hasRight(curr);
    }

    private boolean hasLeft(AVLNode curr) {
        return null != curr && null != curr.getLeft();
    }

    private boolean hasRight(AVLNode curr) {
        return null != curr && null != curr.getRight();
    }

    private boolean hasBoth(AVLNode curr) {
        return hasLeft(curr) && hasRight(curr);
    }

    @Before
    public void setup() {
        avlTree = new AVL<>();
    }

    @Test(expected = IllegalArgumentException.class)
    public void E1() {
        avlTree = new AVL<Integer>(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void Ei1() {
        List<Integer> testL = new ArrayList<>(Arrays.asList(1, 2, 3, 4));
        testL.set(3, null);
        avlTree = new AVL<Integer>(testL);
    }

    @Test(expected = IllegalArgumentException.class)
    public void EiE1() {
        avlTree.add(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void EiEi1() {
        avlTree.remove(null);
    }

    @Test(expected = NoSuchElementException.class)
    public void EiEiO1() {
        avlTree.remove(1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void EiEiO2() {
        avlTree.get(null);
    }

    @Test(expected = NoSuchElementException.class)
    public void EiEiO3() {
        avlTree.get(99);
    }

    @Test(expected = IllegalArgumentException.class)
    public void EiEiO4() {
        avlTree.contains(null);
    }

    @Test()
    public void EiEiO5maybe() {
        assertEquals(-1, avlTree.height());
    }

    /**
     * Tests very basic add and does validation
     * across node positioning and height/BF.
     *
     * Passing this test does not guarantee you
     * more than the knowledge that you can add
     * stuff to your tree and perform right rotations.
     */
    @Test(timeout = TIMEOUT)
    public void P1() {

        avlTree.add(7);
        /*
             7
         */

        AVLNode curr = avlTree.getRoot();
        assertEquals(new Integer(7), curr.getData());
        assertEquals(0, curr.getHeight());
        assertEquals(0, curr.getBalanceFactor());

        LinkedList<Integer> stack = new LinkedList<>();
        IntStream.range(0, 7)
                .boxed().forEach(stack::push);

        avlTree.add(stack.pop());
        /*
                7
               /
              6
         */
        assertEquals(new Integer(6), curr.getLeft().getData());
        assertEquals(2, avlTree.size());
        avlTree.add(6);
        assertEquals(2, avlTree.size());
        assertNull(curr.getRight());
        assertEquals(new Integer(6), curr.getLeft().getData());
        assertEquals(1, curr.getHeight());
        assertEquals(1, curr.getBalanceFactor());

        avlTree.add(stack.pop());
        /*
                7
               /  [R]
              6   -->    6
             /          / \
            5          5   7
         */

        assertNotEquals(curr, avlTree.getRoot());
        curr = avlTree.getRoot();

        assertEquals(new Integer(5), curr.getLeft().getData());
        assertEquals(new Integer(7), curr.getRight().getData());
        assertEquals(new Integer(6), curr.getData());
        assertEquals(3, avlTree.size());
        avlTree.add(5);
        assertEquals(3, avlTree.size());
        assertTrue(isLeafNode(curr.getLeft()));
        assertTrue(isLeafNode(curr.getRight()));
        assertEquals(1, curr.getHeight());
        assertEquals(0, curr.getBalanceFactor());

        avlTree.add(stack.pop());
        /*
               6
              / \
             5   7
            /
           4
         */
        assertSame(curr, avlTree.getRoot());
        assertFalse(isLeafNode(curr.getLeft()));
        assertTrue(isLeafNode(curr.getRight()));
        assertTrue(hasLeft(curr.getLeft()));
        assertFalse(hasRight(curr.getLeft()));
        assertEquals(4, avlTree.size());
        assertEquals(new Integer(4),
                curr.getLeft().getLeft().getData());

        assertEquals(2, curr.getHeight());
        assertEquals(1, curr.getBalanceFactor());


        AVLNode prev = curr.getLeft();
        avlTree.add(stack.pop());
        /*
                 6              6
                / \           /  \
               5   7  [R]    4    7
              /       -->   / \
             4             3   5
            /
           3
         */

        assertNotSame(prev, curr.getLeft());
        assertEquals(new Integer(6), curr.getData());
        assertEquals(new Integer(4), curr.getLeft().getData());
        assertEquals(new Integer(5),
                curr.getLeft().getRight().getData());
        assertEquals(1, curr.getBalanceFactor());
        assertEquals(2, curr.getHeight());
        prev = curr.getLeft();

        avlTree.add(stack.pop());
        /*
                  6
                /  \            4
               4    7  [R]    /  \
              / \      -->   3    6
             3   5          /    /  \
            /              2    5    7
           2
         */
        assertNotSame(curr, avlTree.getRoot());
        curr = avlTree.getRoot();
        assertSame(prev, curr);
        assertEquals(0, curr.getBalanceFactor());
        assertEquals(new Integer(3), curr.getLeft().getData());
        assertEquals(new Integer(6), curr.getRight().getData());
        assertEquals(new Integer(5),
                curr.getRight().getLeft().getData());
        assertEquals(new Integer(7),
                curr.getRight().getRight().getData());
        assertFalse(hasRight(curr.getLeft()));
        assertTrue(hasRight(curr.getRight()));
        assertTrue(hasLeft(curr.getRight()));
        prev = curr.getLeft();
        assertEquals(2, curr.getHeight());
        assertEquals(0, curr.getBalanceFactor());

        avlTree.add(stack.pop());
        /*
                  4
                /  \                  4
               3    6      [R]     /    \
              /    /  \     -->   2      6
             2    5    7         / \    / \
            /                   1   3  5   7
           1
         */
        assertSame(curr, avlTree.getRoot());
        assertNotSame(prev, curr.getLeft());
        assertEquals(2, curr.getHeight());
        assertTrue(hasBoth(curr));
        assertTrue(hasBoth(curr.getRight()));
        assertTrue(hasBoth(curr.getLeft()));
        assertEquals(new Integer(2), curr.getLeft().getData());
        assertEquals(new Integer(1),
                curr.getLeft().getLeft().getData());
        prev = curr.getLeft();
        assertEquals(2, curr.getHeight());
        assertEquals(0, curr.getBalanceFactor());

        avlTree.add(stack.pop());
        /*
                   4
                /    \
               2      6
              / \    / \
             1   3  5   7
            /
           0
         */
        assertEquals(8, avlTree.size());
        assertSame(curr, avlTree.getRoot());
        assertSame(prev, curr.getLeft());
        assertEquals(new Integer(0),
                curr.getLeft().getLeft().getLeft().getData());

        assertEquals(3, curr.getHeight());
        assertEquals(1, curr.getBalanceFactor());
    }

    /**
     * Tests left-right rotation. Very similar
     * to the student tests just different add order
     * and rotation order.
     */
    @Test(timeout = TIMEOUT)
    public void P2() {
        /*
                5
               /            4
              3      ->    / \
               \          3   5
                4
         */
        avlTree.add(5);
        avlTree.add(3);
        avlTree.add(4);

        assertEquals(3, avlTree.size());
        AVLNode root = avlTree.getRoot();
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

    /**
     * Runs very basic test on adding to the tree
     * and validating that nodes are added in the correct place.
     * It tests left rotation ONLY.
     *
     * Passing this test does not guarantee you
     * more than the knowledge that you can add
     * stuff to your tree and perform left rotations.
     */
    @Test(timeout = TIMEOUT)
    public void P3() {
        LinkedList<Integer> queue = IntStream.range(0, 8)
                .boxed()
                .collect(Collectors.toCollection(LinkedList::new));

        AVLNode root = avlTree.getRoot();
        assertEquals(0, avlTree.size());
        assertNull(root);
        /*
            0
         */
        avlTree.add(queue.removeFirst());
        assertEquals(1, avlTree.size());
        assertNotSame(root, avlTree.getRoot());
        root = avlTree.getRoot();

        avlTree.add(queue.removeFirst());
        /*
            0
             \
              1
         */
        assertEquals(2, avlTree.size());
        assertTrue(hasRight(root));
        assertFalse(hasLeft(root));
        assertEquals(-1, root.getBalanceFactor());
        assertEquals(0, root.getRight().getBalanceFactor());
        assertEquals(1, root.getHeight());
        assertTrue(isLeafNode(root.getRight()));

        avlTree.add(queue.removeFirst());
        /*
            0         0     [L]     1
             \  -->    \    -->    / \
              1         1         0   2
                         \
                          2
         */

        assertEquals(3, avlTree.size());
        assertNotSame(root, avlTree.getRoot());
        root = avlTree.getRoot();
        assertEquals(1, root.getHeight());
        assertEquals(0, root.getBalanceFactor());
        assertTrue(hasBoth(root));
        assertEquals(2, root.getRight().getData());
        assertEquals(0, root.getLeft().getData());
        assertEquals(1, root.getData());
        assertEquals(0, root.getRight().getBalanceFactor());
        assertEquals(0, root.getLeft().getBalanceFactor());
        assertEquals(0, root.getLeft().getHeight());
        assertEquals(0, root.getRight().getHeight());

        avlTree.add(queue.removeFirst());
        /*
              1         1
             / \  ->   / \
            0   2     0   2
                           \
                            3
         */

        assertEquals(4, avlTree.size());
        assertEquals(-1, root.getBalanceFactor());
        assertEquals(0, root.getLeft().getBalanceFactor());
        assertEquals(-1, root.getRight().getBalanceFactor());
        assertEquals(2, root.getHeight());
        assertEquals(3, root.getRight().getRight().getData());

        avlTree.add(queue.removeFirst());
        /*
              1          1               1
             / \        / \            /  \
            0   2  ->  0   2    [L]   0    3
                 \          \   -->       / \
                  3          3           2  4
                              \
                               4
         */
        assertSame(root, avlTree.getRoot());
        assertEquals(5, avlTree.size());
        avlTree.add(4);
        assertEquals(5, avlTree.size());
        assertEquals(2, root.getHeight());
        assertEquals(-1, root.getBalanceFactor());
        assertTrue(hasBoth(root.getRight()));

        AVLNode curr = root.getRight();

        assertEquals(0, curr.getBalanceFactor());
        assertEquals(1, curr.getHeight());
        assertEquals(3, curr.getData());
        assertEquals(2, curr.getLeft().getData());
        assertEquals(4, curr.getRight().getData());

        avlTree.add(queue.removeFirst());
        /*
               1            1                 3
             /  \         /  \      [L]   /      \
            0    3   ->  0    3     -->  1        4
                / \          / \        / \        \
               2  4         2   4      0   2        5
                                 \
                                  5
         */
        assertNotSame(root, avlTree.getRoot());
        root = avlTree.getRoot();
        assertEquals(6, avlTree.size());
        assertNotSame(curr, root.getRight());
        curr = root.getRight();
        assertEquals(0, root.getBalanceFactor());
        assertEquals(2, root.getHeight());
        assertEquals(-1, curr.getBalanceFactor());
        assertEquals(0, curr.getRight().getBalanceFactor());
        assertEquals(5, curr.getRight().getData());

        assertEquals(1, root.getLeft().getData());
        assertTrue(hasBoth(root.getLeft()));
        assertEquals(0, root.getLeft().getBalanceFactor());
        assertEquals(1, root.getLeft().getHeight());
        assertEquals(0, root.getLeft().getLeft().getData());
        assertEquals(2, root.getLeft().getRight().getData());

        avlTree.add(queue.removeFirst());
        /*
                   3                 3                    3
               /      \           /    \               /    \
              1        4    ->   1      4      [L]    1      5
             / \        \       / \      \     -->   / \    / \
            0   2        5     0   2      5         0   2  4   6
                                           \
                                            6
         */
        assertSame(root, avlTree.getRoot());
        assertEquals(7, avlTree.size());
        assertTrue(hasBoth(root.getLeft()));
        assertTrue(hasBoth(root.getRight()));
        assertEquals(1, root.getLeft().getData());
        assertEquals(3, root.getData());
        assertEquals(5, root.getRight().getData());
        assertEquals(6, root.getRight().getRight().getData());
        assertEquals(4, root.getRight().getLeft().getData());
        assertEquals(0, root.getRight().getBalanceFactor());
        assertEquals(1, root.getRight().getHeight());

        avlTree.add(queue.removeFirst());
        /*
                  3                3
               /    \           /     \
              1      5   ->    1       5
             / \    / \       / \     / \
            0   2  4   6     0   2   4   6
                                          \
                                           7
         */
        assertSame(root, avlTree.getRoot());
        assertEquals(-1, root.getBalanceFactor());
        assertEquals(3, root.getHeight());
        curr = root.getRight();
        assertEquals(-1, curr.getBalanceFactor());
        assertEquals(2, curr.getHeight());
        curr = curr.getRight();

        assertEquals(-1, curr.getBalanceFactor());
        assertEquals(1, curr.getHeight());
        assertEquals(6, curr.getData());
        assertFalse(hasLeft(curr));
        assertTrue(hasRight(curr));

        curr = curr.getRight();
        assertEquals(7, curr.getData());
    }

    /**
     * Tests removing a leaf from the tree and
     * updating the balance factor and height.
     */
    @Test
    public void R1() {
        IntStream.range(0, 8)
                .boxed()
                .sorted(Collections.reverseOrder())
                .forEach(avlTree::add);

        AVLNode root = avlTree.getRoot();
        assertEquals(8, avlTree.size());
        /*
                   4
                /    \
               2      6
              / \    / \
             1   3  5   7
            /
           0
       */
        assertEquals(1, root.getRight().getHeight());
        assertEquals(0, root.getRight().getBalanceFactor());
        assertEquals(new Integer(7), avlTree.remove(7));
        /*
                   4
                /    \
               2      6
              / \    /
             1   3  5
            /
           0
       */
        assertEquals(1, root.getRight().getBalanceFactor());
        assertEquals(1, root.getRight().getHeight());
        assertEquals(6, root.getRight().getData());
        assertEquals(1, root.getRight().getBalanceFactor());
        assertEquals(5, root.getRight().getLeft().getData());
        assertFalse(hasRight(root.getRight()));
    }

    /**
     * Tests removing a node higher up in the tree
     * than the leaf and making sure the predecessor
     * is used as the replacement.
     */
    @Test(timeout = TIMEOUT)
    public void R2() {
        IntStream.range(0, 8)
                .boxed()
                .sorted(Collections.reverseOrder())
                .forEach(avlTree::add);

        AVLNode root = avlTree.getRoot();
        assertEquals(8, avlTree.size());
        assertEquals(1, root.getBalanceFactor());
        /*
                   4                 4
                /    \   [DEL 2]   /   \
               2      6    -->    1      6
              / \    / \         / \    / \
             1   3  5   7       0   3   5   7
            /
           0
       */
        assertEquals(3, root.getHeight());
        assertEquals(1, root.getBalanceFactor());
        assertEquals(new Integer(2), avlTree.remove(2));
        assertEquals(7, avlTree.size());
        assertEquals(1, root.getLeft().getData());
        assertEquals(3, root.getLeft().getRight().getData());
        assertEquals(0, root.getLeft().getLeft().getData());

        assertEquals(2, root.getHeight());
        assertEquals(1, root.getLeft().getHeight());
        assertEquals(0, root.getLeft().getBalanceFactor());
        assertEquals(0, root.getBalanceFactor());
    }


    /**
     * Tests removing a non-leaf node, replacement
     * with the predecessor, and proper balancing
     * using right rotation
     */
    @Test
    public void R3() {
        IntStream.range(0, 8)
                .boxed()
                .sorted(Collections.reverseOrder())
                .forEach(avlTree::add);

        AVLNode root = avlTree.getRoot();
        assertEquals(8, avlTree.size());

        /*
                   4                3
                /    \   [DEL 4]   /  \
               2      6    -->    2    6
              / \    / \         /    / \
             1   3  5   7       1    5   7
            /                  /
           0                  0
                 3
              /     \
             1       6
            / \     / \
           0   2   5   7
       */
        assertEquals(3, root.getHeight());
        assertEquals(1, root.getBalanceFactor());

        assertEquals(new Integer(4), avlTree.remove(4));

        assertEquals(3, root.getData());
        assertEquals(1, root.getLeft().getData());
        assertEquals(0, root.getLeft().getLeft().getData());
        assertEquals(2, root.getLeft().getRight().getData());
        assertEquals(0, root.getBalanceFactor());
        assertEquals(0, root.getLeft().getBalanceFactor());

    }

    /**
     * Tests removing a non leaf node, replacement
     * with the predecessor, and proper balancing
     * using left rotation
     */
    @Test
    public void R4() {
        IntStream.range(0, 8)
                .boxed()
                .sorted(Collections.reverseOrder())
                .forEach(avlTree::add);

        avlTree.add(8);
        AVLNode root = avlTree.getRoot();
        assertEquals(9, avlTree.size());
        assertEquals(3, root.getHeight());
        assertEquals(0, root.getBalanceFactor());
        assertEquals(4, root.getData());
        assertEquals(new Integer(5), avlTree.remove(5));
        /*
                   4                  4
                /    \    [DEL 5]  /     \
               2      6    -->    2       6
              / \    / \         / \       \
             1   3  5   7       1   3       7
            /            \     /             \
           0              8   0               8
                 4
              /     \
             2       7
            / \     / \
           1   3   6   8
          /
         0
       */
        assertEquals(8, avlTree.size());
        assertEquals(1, root.getBalanceFactor());
        assertEquals(3, root.getHeight());
        assertEquals(7, root.getRight().getData());
        assertTrue(hasBoth(root.getRight()));
        assertEquals(8, root.getRight().getRight().getData());
        assertEquals(6, root.getRight().getLeft().getData());
        assertEquals(0, root.getRight().getBalanceFactor());
        assertTrue(isLeafNode(root.getRight().getRight()));
        assertEquals(0, root.getRight().getRight().getBalanceFactor());
        assertTrue(isLeafNode(root.getRight().getLeft()));
        assertEquals(0, root.getRight().getLeft().getBalanceFactor());
    }

    /**
     * Tests:
     * <ol>
     *     <li>Removing nodes and returning appropriate value</li>
     *     <li>Properly re-balancing after removing using Right-Left
     *     rotation</li>
     * </ol>
     */
    @Test
    public void R6() {

        Arrays.asList(70, 60, 50, 40, 30, 20, 10, 0)
                .stream().forEach(avlTree::add);

        AVLNode root = avlTree.getRoot();
        assertEquals(8, avlTree.size());
        assertEquals(40, root.getData());
        avlTree.add(29);
        /*
                    40                    40
                /       \              /     \
               20        60    -->    20      60
              / \       / \         /   \    /  \
             10   30  50   70      10   30  50   70
            /                     /     /
           0                     0     29
       */
        assertEquals(9, avlTree.size());
        assertEquals(29,
                root.getLeft().getRight().getLeft().getData());

        assertEquals(0, root.getLeft().getBalanceFactor());

        assertEquals(new Integer(0), avlTree.remove(0));
        assertEquals(-1, root.getLeft().getBalanceFactor());
        assertEquals(new Integer(10), avlTree.remove(10));

        /*
                    40                      40
                /       \      [R]      /       \
               20        60    -->    20        60
                \       / \            \       /  \
          -->    30  50   70            29    50   70
                 /                       \
                29                       30
                       40
            [L]    /        \
            -->   29         60
                 /   \       / \
                20   30     50   70
       */
        assertEquals(7, avlTree.size());
        assertEquals(29, root.getLeft().getData());
        assertEquals(30, root.getLeft().getRight().getData());
        assertEquals(20, root.getLeft().getLeft().getData());
        assertEquals(0, root.getLeft().getBalanceFactor());
        assertTrue(isLeafNode(root.getLeft().getLeft()));
        assertTrue(isLeafNode(root.getLeft().getRight()));
    }

    /**
     * Tests:
     * <ol>
     *     <li>Removing a node from the tree and replacement
     *          with the predecessor</li>
     *     <li> Proper Right-Left balancing afterwards</li>
     * </ol>
     */
    @Test
    public void R7() {

        Arrays.asList(70, 60, 50, 40, 30, 20, 10, 0)
                .stream().forEach(avlTree::add);

        AVLNode root = avlTree.getRoot();
        assertEquals(8, avlTree.size());
        avlTree.add(29);
        /*
                    40
                 /     \
                20      60
              /   \    /  \
             10   30  50   70
            /     /
           0     29
       */
        assertEquals(new Integer(0), avlTree.remove(0));
        /*
                    40
                 /     \
                20      60
              /   \    /  \
             10   30  50   70
                  /
                 29
       */
        assertEquals(new Integer(20), avlTree.remove(20));
        /*
                    40                      40
                /       \      [R]      /       \
               10        60    -->    10        60
                \       / \            \       /  \
          -->    30  50   70            29    50   70
                 /                       \
                29                       30
                       40
            [L]    /        \
            -->   29         60
                 /   \       / \
                10   30     50   70
       */
        assertEquals(29, root.getLeft().getData());
        assertEquals(30, root.getLeft().getRight().getData());
        assertEquals(10, root.getLeft().getLeft().getData());
        assertEquals(0, root.getLeft().getBalanceFactor());
        assertTrue(isLeafNode(root.getLeft().getRight()));
        assertTrue(isLeafNode(root.getLeft().getLeft()));
    }


    /**
     * Tests:
     * <ol>
     *     <li>Removing a node and returning the proper value</li>
     *     <li>Properly re-balancing using Left-Right rotation</li>
     * </ol>
     */
    @Test(timeout = TIMEOUT)
    public void R8() {

        Arrays.asList(70, 60, 50, 40, 30, 20, 10)
                .stream().forEach(avlTree::add);

        AVLNode root = avlTree.getRoot();
        assertEquals(7, avlTree.size());
        assertEquals(40, root.getData());
        assertEquals(20, root.getLeft().getData());
        assertEquals(60, root.getRight().getData());
        assertEquals(10, root.getLeft().getLeft().getData());
        assertEquals(30, root.getLeft().getRight().getData());
        /*
                    40
                 /     \
                20      60
              /   \    /  \
             10   30  50   70
                    ||
                    \/
        */
        avlTree.add(11);
        /*
                     40
                 /       \
                 20       60
              /     \    /  \
             10     30  50   70
              \
               11
       */
        assertEquals(1, root.getBalanceFactor());
        assertEquals(3, root.getHeight());
        assertEquals(new Integer(30), avlTree.remove(30));
        /*
                    40                      40
                /       \      [L]      /       \
               20        60    -->    20        60
               /         / \          /         /  \
          --> 10      50   70       11        50   70
               \                    /
                11                 10
                       40
            [R]    /        \
            -->   11         60
                 /   \       / \
                10   20     50   70
       */
        assertSame(root, avlTree.getRoot());
        assertEquals(11, root.getLeft().getData());
        assertTrue(hasBoth(root.getLeft()));
        assertEquals(10, root.getLeft().getLeft().getData());
        assertEquals(20, root.getLeft().getRight().getData());
    }

    /**
     * Helper to validate tree for next test since I am lazy.
     * @param curr Curr node
     * @param toPopulate List to populate
     */
    private void recursiveValidate(AVLNode curr, List toPopulate) {
        if (null == curr) {
            return;
        }
        recursiveValidate(curr.getLeft(), toPopulate);
        toPopulate.add(curr.getData());
        recursiveValidate(curr.getRight(), toPopulate);
    }

    /**
     * Tests:
     * <ol>
     *     <li>Properly creating a balanced BST</li>
     *     <li>Removing leaf nodes to create a degenerate tree</li>
     *     <li>Both LR and RL rotations</li>
     *     <li>Predecessor replacement</li>
     *     <li>Remove all nodes in tree in worst possible way</li>
     * </ol>
     */
    @Test
    public void R9() {
        List<Integer> dataList = Arrays.asList(70, 65, 60, 55, 50, 45,
                40, 35, 30, 25, 20, 15, 10, 5, 0);

        dataList.stream().forEach(avlTree::add);
        AVLNode root = avlTree.getRoot();
        List<Integer> inOrderResult = new LinkedList<>();
        recursiveValidate(root, inOrderResult);
        int currSize = avlTree.size();
        // This is a cheap way to check that every node is where it is
        // supposed to be. I'm lazy sorry :-P
        Collections.reverse(dataList);
        assertEquals(dataList, inOrderResult);
        /*
                        35
                  /            \
                 15              55
              /     \          /    \
             5       25      45       65
            / \      / \    /  \     /  \
           0   10  20  30  40   50  60   70
       */
        assertEquals(new Integer(0), avlTree.remove(0));
        assertEquals(--currSize, avlTree.size());
        /*
                        35
                  /            \
                 15              55
              /     \          /    \
             5       25      45       65
              \      / \    /  \     /  \
               10  20  30  40   50  60   70
       */
        assertEquals(3, root.getHeight());
        assertEquals(0, root.getBalanceFactor());
        assertEquals(0, root.getLeft().getBalanceFactor());
        assertEquals(2, root.getLeft().getHeight());
        assertNull(root.getLeft().getLeft().getLeft());

        assertEquals(new Integer(10), avlTree.remove(10));
        assertEquals(--currSize, avlTree.size());
        /*
                        35
                  /            \
                 15              55
              /     \          /    \
             5       25      45       65
                     / \    /  \     /  \
                   20  30  40   50  60   70
       */
        assertSame(root, avlTree.getRoot());
        assertEquals(3, root.getHeight());
        assertEquals(0, root.getBalanceFactor());
        assertEquals(-1, root.getLeft().getBalanceFactor());
        assertEquals(5, root.getLeft().getLeft().getData());

        assertEquals(new Integer(70), avlTree.remove(70));
        assertEquals(--currSize, avlTree.size());
        /*
                        35
                  /            \
                 15              55
              /     \          /    \
             5       25      45       65
                     / \    /  \     /
                   20  30  40   50  60
       */
        assertSame(root, avlTree.getRoot());
        assertEquals(3, root.getHeight());
        assertEquals(0, root.getBalanceFactor());
        assertNull(root.getRight().getRight().getRight());
        assertEquals(1, root.getRight().getRight().getBalanceFactor());

        assertEquals(new Integer(60), avlTree.remove(60));
        assertEquals(--currSize, avlTree.size());
        /*
                        35
                  /            \
                 15              55
              /     \          /    \
             5       25      45       65
                     / \    /  \
                   20  30  40   50
       */
        assertSame(root, avlTree.getRoot());
        assertEquals(3, root.getHeight());
        assertEquals(0, root.getBalanceFactor());
        assertEquals(65, root.getRight().getRight().getData());
        assertEquals(1, root.getRight().getBalanceFactor());

        assertTrue(avlTree.contains(30));
        assertEquals(new Integer(30), avlTree.remove(30));
        assertFalse(avlTree.contains(30));
        assertEquals(--currSize, avlTree.size());
        /*
                        35
                  /            \
                 15              55
              /     \          /    \
             5       25      45       65
                     /      /  \
                   20      40   50
       */
        assertEquals(root, avlTree.getRoot());
        assertEquals(3, root.getHeight());
        assertEquals(0, root.getBalanceFactor());
        assertEquals(-1, root.getLeft().getBalanceFactor());
        assertEquals(2, root.getLeft().getHeight());
        assertEquals(1, root.getLeft().getRight().getBalanceFactor());
        assertEquals(1, root.getLeft().getRight().getHeight());

        assertEquals(new Integer(40), avlTree.remove(40));
        assertEquals(--currSize, avlTree.size());
        /*
                        35
                  /            \
                 15              55
              /     \          /    \
             5       25      45       65
                     /         \
                   20           50
       */

        AVLNode oldLeft = root.getLeft();
        assertEquals(new Integer(5), avlTree.remove(5));
        assertEquals(--currSize, avlTree.size());
        /*
                        35
                  /            \
                 15              55
                    \          /    \
                     25      45       65
                     /         \
                   20           50
                        ||  [R]
                        \/
                        35
                  /            \
                 15              55
                    \          /    \
                     20      45       65
                      \        \
                        25     50
                        ||  [L]
                        \/
                        35
                  /            \
                 20              55
                /  \           /    \
               15   25       45      65
                               \
                               50
       */
        assertNotSame(oldLeft, root.getLeft());
        assertEquals(3, root.getHeight());
        assertEquals(-1, root.getBalanceFactor());
        assertEquals(20, root.getLeft().getData());
        assertEquals(15, root.getLeft().getLeft().getData());
        assertEquals(25, root.getLeft().getRight().getData());
        assertEquals(0, root.getLeft().getBalanceFactor());

        AVLNode oldRight = root.getRight();
        assertEquals(new Integer(65), avlTree.remove(65));
        assertEquals(--currSize, avlTree.size());
        /*
                        35
                  /            \
                 20              55
                /  \           /
               15   25       45
                               \
                               50
                        || [L]
                        \/
                        35
                  /            \
                 20              55
                /  \           /
               15   25       50
                             /
                            45
                        || [R]
                        \/
                        35
                  /            \
                 20              50
                /  \           /   \
               15   25       45     55
       */
        assertNotSame(oldRight, root.getRight());
        assertEquals(2, root.getHeight());
        assertEquals(0, root.getBalanceFactor());
        assertEquals(50, root.getRight().getData());
        assertEquals(55, root.getRight().getRight().getData());
        assertEquals(45, root.getRight().getLeft().getData());
        assertEquals(0, root.getRight().getBalanceFactor());

        assertEquals(new Integer(35), avlTree.remove(35));
        assertEquals(--currSize, avlTree.size());
        /*
                        25
                  /            \
                 20              50
                /              /   \
               15            45     55
       */
        assertEquals(2, root.getHeight());
        assertEquals(25, root.getData());
        assertEquals(0, root.getBalanceFactor());
        assertFalse(hasRight(root.getLeft()));
        assertEquals(20, root.getLeft().getData());
        assertEquals(15, root.getLeft().getLeft().getData());

        assertEquals(new Integer(25), avlTree.remove(25));
        assertEquals(--currSize, avlTree.size());
        /*
                        20
                  /            \
                 15              50
                               /   \
                             45     55
       */
        assertEquals(-1, root.getBalanceFactor());
        assertEquals(20, root.getData());
        assertEquals(15, root.getLeft().getData());
        assertTrue(isLeafNode(root.getLeft()));

        assertEquals(new Integer(20), avlTree.remove(20));
        assertEquals(--currSize, avlTree.size());
        /*
                        15
                          \
                           50
                          /   \
                        45     55
                        || [L]
                        \/
                        50
                       /   \
                     15     55
                      \
                       45
       */
        assertNotSame(root, avlTree.getRoot());
        root = avlTree.getRoot();
        assertEquals(2, root.getHeight());
        assertFalse(hasLeft(root.getLeft()));
        assertEquals(15, root.getLeft().getData());
        assertEquals(-1, root.getLeft().getBalanceFactor());
        assertEquals(55, root.getRight().getData());
        assertTrue(isLeafNode(root.getRight()));

        assertEquals(new Integer(50), avlTree.remove(50));
        assertEquals(--currSize, avlTree.size());
        /*
                        45
                       /   \
                     15     55
       */
        assertEquals(45, root.getData());
        assertTrue(hasBoth(root));
        assertEquals(15, root.getLeft().getData());
        assertEquals(55, root.getRight().getData());
        assertEquals(1, root.getHeight());
        assertEquals(0, root.getBalanceFactor());

        assertEquals(new Integer(45), avlTree.remove(45));
        assertEquals(--currSize, avlTree.size());
        /*
                        15
                           \
                           55
       */

        assertEquals(15, root.getData());
        assertEquals(-1, root.getBalanceFactor());
        assertNull(root.getLeft());
        assertEquals(55, root.getRight().getData());

        assertEquals(new Integer(15), avlTree.remove(15));
        assertEquals(--currSize, avlTree.size());
        /*
             55
       */

        assertNotSame(root, avlTree.getRoot());
        root = avlTree.getRoot();
        assertEquals(55, root.getData());
        assertEquals(0, root.getHeight());
        assertEquals(0, root.getBalanceFactor());
        assertTrue(isLeafNode(root));

        assertEquals(new Integer(55), avlTree.remove(55));
        assertEquals(--currSize, avlTree.size());
        assertNotSame(root, avlTree.getRoot());
        assertNull(avlTree.getRoot());
        assertEquals(-1, avlTree.height());
    }
}