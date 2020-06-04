
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

class BinarySearchTree {

    /* Class containing left and right child of current node and key value*/
    class Node {
        int key;
        Node left, right;

        public Node(int item) {
            key = item;
            left = right = null;
        }
    }

    // Root of BST
    Node root;

    // Constructor
    BinarySearchTree() {
        root = null;
    }

    // This method mainly calls insertRec()
    void insert(int key) {
        root = insertRec(root, key);
    }

    /* A recursive function to insert a new key in BST */
    Node insertRec(Node root, int key) {

        /* If the tree is empty, return a new node */
        if (root == null) {
            root = new Node(key);
            return root;
        }

        /* Otherwise, recur down the tree */
        if (key < root.key)
            root.left = insertRec(root.left, key);
        else if (key > root.key)
            root.right = insertRec(root.right, key);

        /* return the (unchanged) node pointer */
        return root;
    }

    // This method mainly calls InorderRec()
    void inorder()  {
        inorderRec(root);
    }

    // A utility function to do inorder traversal of BST
    void inorderRec(Node root) {
        if (root != null) {
            inorderRec(root.left);
            System.out.println(root.key);
            inorderRec(root.right);
        }
    }

    public int sumConnected (int data) {
        return sumConnectedHelper(null, root, data);
    }
    private int sumConnectedHelper(Node parent,  Node curr, int data) {

        int sum = 0;
        if (curr == null) {
            throw new IllegalArgumentException("");
        } else if (data < curr.key) {
            sum = sumConnectedHelper(curr, curr.left, data);
        } else if (data > curr.key) {
            sum = sumConnectedHelper(curr, curr.right, data);
        } else {
            if (parent == null) {
                return curr.left.key + curr.right.key;
            } else if (curr.left == null && curr.right == null) {
                return parent.key;
            } else if (curr.left == null && curr.right != null) {
                return  parent.key + curr.right.key;
            } else if (curr.left != null && curr.right == null) {
                return parent.key + curr.left.key;
            } else {
                return  parent.key + curr.right.key + curr.left.key;
            }
        }
        return sum;
    }
    /**
     * #1) Ancestors
     */
    public List<Integer> ancestors(int data) {
        List<Integer> list = new ArrayList<>();
        ancestorsHelper(root, list, data);
        return list;
    }

    private List<Integer> ancestorsHelper(Node curr, List<Integer> retList, int data) {
        if (curr == null) {
            throw new IllegalArgumentException("");
        }

        if (data == curr.key) {
            return null;
        }

        if (data < curr.key) {
            ancestorsHelper(curr.left, retList, data);
            retList.add(curr.key);

        } else if (data > curr.key) {
            ancestorsHelper(curr.right, retList, data);
            retList.add(curr.key);

        }
        return retList;
    }
    /**
     * #2) LargestValue
     */
    public int largestvalue() {
        if (root == null) {
            throw new java.util.NoSuchElementException("");
        }
        return largestHelper(root);
    }

    private int largestHelper(Node curr) {
        if (curr.right == null) {
            return curr.key;
        } else {
            return largestHelper(curr.right);
        }
    }

    /**
     * #3) Sum of ALL Nodes
     */
    public int sumAll() {
        return sumAlllHelper(root);
    }

    private int sumAlllHelper(Node curr) {

        if (curr == null) {
            return 0;
        }

        return (curr.key + sumAlllHelper(curr.left) + sumAlllHelper(curr.right));
    }

    /**
     * #4) Sum of EVEN Node values
     */
    public int sumEvens() {
        return sumEvensHelper(root);
    }

    private int sumEvensHelper(Node curr) {
        if (curr == null) {
            return 0;
        }
        int value = 0;

        if(curr.key % 2 == 0) {
            value = curr.key;
        }

        //if odd nodes values
//        if(curr.key %2 == 1) {
//            value = curr.key;
//        }

        return value + sumEvensHelper(curr.left) + sumEvensHelper(curr.right);
    }

    /**
     * #5) Sum of Leaf Nodes values
     */
    public int sumLeaf() {
        return sumLeafHelper(root);
    }

    private int sumLeafHelper(Node curr) {
        if (curr == null) {
            return 0;
        }

        int value = 0;

        if (curr.left == null && curr.right == null) {
            value = curr.key;
        }

        return value + sumLeafHelper(curr.left) + sumLeafHelper(curr.right);
    }

    /**
     * #6) Sum of Full (Internal) Nodes values
     */
    public int sumFull() {
        return sumFullHelper(root);
    }

    private int sumFullHelper(Node curr) {
        if (curr == null) {
            return 0;
        }

        int value = 0;
        if (curr.left != null && curr.right != null) {
            value = curr.key;
        }

        return value + sumFullHelper(curr.left) + sumFullHelper(curr.right);
    }

    /**
     * #7) Count EVEN  Nodes
     */
    public int countEvens() {
        return countEvensHelper(root);
    }

    private int countEvensHelper(Node curr){
        if (curr == null) {
            return 0;
        }

        int count = 0;

        if (curr.key %2 == 0) {
            count++;
        }

        return count + countEvensHelper(curr.left) + countEvensHelper(curr.right);
    }


    /**
     * #8) Count ALL  Nodes
     */
    public int countAll() {
        return countAllHelper(root);
    }

    private int countAllHelper(Node curr){
        if (curr == null) {
            return 0;
        }

        int count = 0;

        if (curr != null) {
            count++;
        }

        return count + countAllHelper(curr.left) + countAllHelper(curr.right);
    }


    /**
     * #9) is Root Median?
     */
    public boolean isRootMedian() {
        int leftCount = 0;
        int rightCount = 0;

        leftCount = getCount(root.left);
        rightCount = getCount(root.right);

        if (leftCount == rightCount) {
            return true;
        }
        return false;
    }

    private int getCount(Node curr) {
        if (curr == null) {
            return 0;
        }

        int count = 0;
        if (curr != null) {
            count++;
        }
        return count + getCount(curr.left) + getCount(curr.right);
    }

    /**
     * #9) leavesDescending
     */
    public List<Integer> leaevsDescending() {
        List<Integer> list = new ArrayList<>();
        leavesDescendingHelper(root, list);
        return list;
    }

    private void leavesDescendingHelper(Node curr, List<Integer> list) {
        if (curr == null) {
            return;
        }

        if (curr.left == null && curr.right == null) {
            list.add(curr.key);
        }

        leavesDescendingHelper(curr.right, list);
        leavesDescendingHelper(curr.left, list);
    }

    /**
     * #9) SumOfEvenLevels
     */
    public int sumEvenLevels() {
        return sumEvenLevelsHelper(root, true);
    }

    private int sumEvenLevelsHelper(Node curr, boolean isEven) {
        if (curr == null) {
            return 0;
        }

        int value = 0;
        if (isEven) {
            value = curr.key;
        }

        return (value + sumEvenLevelsHelper(curr.left, !isEven) + sumEvenLevelsHelper(curr.right, !isEven));
    }

    /**
     * #10) countThresh
     */
    public int countThresh(int data) {

        return countThreshHelper(root, data);
    }

    private int countThreshHelper(Node curr, int data) {
        if (curr == null) {
            return 0;
        }

        int count = 0;

        if (curr.key >= data) {
            count++;
        }

        return count + countEvensHelper(curr.left) + countEvensHelper(curr.right);
    }

    /**
     * #10) countThresh List
     */
    public List<Integer> countThreshList(int data) {
        List<Integer> list = new ArrayList<>();
        countThreshListHelper(root, data, list);
        return list;
    }

    private void countThreshListHelper(Node curr, int data, List<Integer> list) {
        if (curr == null) {
            return;
        }

        if (curr.key >= data) {
            list.add(curr.key);
        }

        countThreshListHelper(curr.left, data, list);
        countThreshListHelper(curr.right, data, list);
    }



    /**
     * #10) Print Odd Levels
     */
    public List<Integer> printOddNodes() {
        List<Integer> list = new ArrayList<>();
        printOddNodesHelper(root, false, list);
        return list;
    }

    private void printOddNodesHelper(Node curr, boolean isOdd, List<Integer> list) {
        if (curr == null) {
            return;
        }

        if (isOdd) {
            list.add(curr.key);
        }

        printOddNodesHelper(curr.left, !isOdd, list);
        printOddNodesHelper(curr.right, !isOdd, list);

    }





    // Driver Program to test above functions
    public static void main(String[] args) {
        BinarySearchTree tree = new BinarySearchTree();

        /* Let us create following BST
                50
             /     \
            30      70
          /  \    /  \
         20   40  60   80
        /
       19
        */
        tree.insert(50);
        tree.insert(30);
        tree.insert(20);
        tree.insert(40);
        tree.insert(70);
        tree.insert(60);
        tree.insert(80);
        tree.insert(19);


        System.out.println(tree.ancestors(70));
        System.out.println(tree.ancestors(80));
        System.out.println(tree.ancestors(50));

//        System.out.println(tree.evenCount());
//        System.out.println(tree.addBT());
//        System.out.println("sum of ALL nodes: " + tree.sumAll());
//        System.out.println("sum of EVEN node values: " + tree.sumEvens());
//        System.out.println("sum of LEAF node values: " + tree.sumLeaf());
//        System.out.println("sum of FULL node values: " + tree.sumFull());
//        System.out.println("Count ALL node : " + tree.countAll());
//        System.out.println("Count EVEN node : " + tree.countEvens());
//        System.out.println("is Root Median : " + tree.isRootMedian());
//        System.out.println("List of Leaves descending : " + tree.leaevsDescending());
//        System.out.println("Sum of Even Levels : " + tree.sumEvenLevels());
//        System.out.println("countThresh : " + tree.countThresh(50));
//        System.out.println("countThreshLisr : " + tree.countThreshList(50));
//
//        System.out.println("List of Odd Nodes : " + tree.printOddNodes());
//        System.out.println("countThresh List : " + tree.countThreshList(19));
//
//        System.out.println("Sum Connected: " + tree.sumConnected(19));












//        System.out.println(tree.sumConnected(70));
//        System.out.println(tree.sumConnected(50));
//        System.out.println(tree.sumConnected(60));
//        System.out.println(tree.sumConnected(30));
//        System.out.println(tree.sumConnected(100 ));





        // print inorder traversal of the BST
//        tree.inorder();
    }
}
