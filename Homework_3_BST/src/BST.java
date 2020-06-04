import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Queue;
import java.util.LinkedList;

/**
 * Your implementation of a binary search tree.
 *
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */

public class BST<T extends Comparable<? super T>> {
    // DO NOT ADD OR MODIFY INSTANCE VARIABLES.
    private BSTNode<T> root;
    private int size;

    /**
     * A no-argument constructor that should initialize an empty BST.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public BST() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Initializes the BST with the data in the Collection. The data
     * should be added in the same order it is in the Collection.
     *
     * Hint: Not all Collections are indexable like Lists, so a regular for loop
     * will not work here. However, all Collections are Iterable, so what type
     * of loop would work?
     *
     * @param data the data to add to the tree
     * @throws IllegalArgumentException if data or any element in data is null
     */
    public BST(Collection<T> data) {
        if (data == null || data.isEmpty()) {
            throw new IllegalArgumentException("Cannot initialize "
                    + "data into BTS. Given Collection is empty or null");
        }

        root = new BSTNode<T>(data.iterator().next());
        size = 1;
        for (T element : data) {
            if (element == null) {
                throw new IllegalArgumentException(
                        "Null in Collection. Cannot add to BST");
            } else {
                add(element);
            }
        }
    }

    /**
     * Add the data as a leaf in the BST. Should traverse the tree to find the
     * appropriate location. If the data is already in the tree, then nothing
     * should be done (the duplicate shouldn't get added, and size should not be
     * incremented).
     * 
     * Should have a running time of O(log n) for a balanced tree, and a worst
     * case of O(n).
     *
     * @throws IllegalArgumentException if the data is null
     * @param data the data to be added
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException(
                    "Cannot add. Given data is null.");
        }

        root = addHelper(root, data);
    }

    /**
     * recursive helper method for add method
     * @param data Node that will be added to BST
     * @param current stars from root and moves around based on comparison
     * @return node that we want to add to BST
     * with added Node
     */
    private BSTNode<T> addHelper(BSTNode<T> current, T data) {

        if (current == null) {
            size++;
            return new BSTNode<T>(data);

        } else if (data.compareTo(current.getData()) < 0) {
            current.setLeft(addHelper(current.getLeft(), data));

        } else if (data.compareTo(current.getData()) > 0) {
            current.setRight(addHelper(current.getRight(), data));
        }
        return current;
    }

    /**
     * Removes the data from the tree. There are 3 cases to consider:
     *
     * 1: the data is a leaf (no children). In this case, simply remove it.
     * 2: the data has one child. In this case, simply replace it with its
     * child.
     * 3: the data has 2 children. Use the successor to replace the data.
     * You MUST use recursion to find and remove the successor (you will
     * likely need an additional helper method to handle this case efficiently).
     *
     * Should have a running time of O(log n) for a balanced tree, and a worst
     * case of O(n).
     *
     * @throws IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException if the data is not found
     * @param data the data to remove from the tree.
     * @return the data removed from the tree. Do not return the same data
     * that was passed in. Return the data that was stored in the tree.
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException(
                    "Cannot remove. Given data is null.");
        }
        BSTNode<T> dummy = new BSTNode<T>(null);
        root = removeHelper(root, data, dummy);
        return dummy.getData();

    }

    /**
     * recursive helper method for remove
     * @param current moving node starting with root
     * @param data data being passed in
     * @param dummy variable to be removed
     * @return removed data
     */
    private BSTNode<T> removeHelper(
            BSTNode<T> current, T data, BSTNode<T> dummy) {
        //Data is not found. Keep searching through the tree.
        if (current == null) {
            throw new java.util.NoSuchElementException(
                    "No matching data found. Cannot remove.");

        } else if (data.compareTo(current.getData()) < 0) {
            current.setLeft(removeHelper(current.getLeft(), data, dummy));

        } else if (data.compareTo(current.getData()) > 0) {
            current.setRight(removeHelper(current.getRight(), data, dummy));

        } else {

            dummy.setData(current.getData());
            size--;
            //Data is found.
            //0 Child Case
            if (current.getLeft() == null && current.getRight() == null) {
                return null;

                // 1 Child Case (right child empty)
            } else if (current.getLeft() != null
                    && current.getRight() == null) {
                return current.getLeft();

                // 1 Child Case (left child empty)
            } else if (current.getRight() != null
                    && current.getLeft() == null) {
                return current.getRight();

                // 2 Children Case
            } else {
                BSTNode<T> dummy2 = new BSTNode<T>(null);
                current.setRight(removeSuccessor(current.getRight(), dummy2));
                current.setData(dummy2.getData());

            }
        }
        return current;
    }



    /**
     * Helper method that find the getSuccessor
     * @param current current node moving around BST
     * @param dummy variable to be removed
     * @return the parent node of successor
     */

    private BSTNode<T> removeSuccessor(
            BSTNode<T> current, BSTNode<T> dummy) {
        //base case
        if (current.getLeft() == null) {
            dummy.setData(current.getData());
            return current.getRight();

        } else {
            current.setLeft(removeSuccessor(current.getLeft(), dummy));
        }
        return current;
    }

    /**
     * Helper method that find the getSuccessor
     * @return the parent node of successor
     */
    public List<T> listLeavesDescending() {
        List<T> list = new ArrayList<T>();
        listLeavesDescending(root, list);
        return list;
    }
    /**
     * Helper method that find the getSuccessor
     * @param current current node moving around BST
     * @param list of leaves descending
     */
    private void listLeavesDescending(BSTNode<T> current, List<T> list) {
        if (current == null) {
            return;
        }

        if (current.getLeft() == null && current.getRight() == null) {
            list.add(current.getData());
        }

        listLeavesDescending(current.getRight(), list);
        listLeavesDescending(current.getLeft(), list);
    }




    /**
     * Returns the data in the tree matching the parameter passed in (think
     * carefully: should you use value equality or reference equality?).
     *
     * Should have a running time of O(log n) for a balanced tree, and a worst
     * case of O(n).
     *
     * @throws IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException if the data is not found
     * @param data the data to search for in the tree.
     * @return the data in the tree equal to the parameter. Do not return the
     * same data that was passed in.  Return the data that was stored in the
     * tree.
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException(
                    "Cannot Get data. Given data is null.");
        }
        return getHelper(root, data);

    }


    /**
     * @param current current node that moves around to check if match with data
     * @param data data that are check with current
     * @return the data found if exists
     */
    private T getHelper(BSTNode<T> current, T data) {
        if (current == null) {
            throw new java.util.NoSuchElementException(
                    "Data not found. Cannot retrieve data.");

        } else if (current.getData().compareTo(data) == 0) {
            return current.getData();

        } else if (current.getData().compareTo(data) > 0) {
            return getHelper(current.getLeft(), data);

        } else if (current.getData().compareTo(data) < 0) {
            return getHelper(current.getRight(), data);
        }
        return current.getData();
    }

    /**
     * Returns whether or not data equivalent to the given parameter is
     * contained within the tree. The same type of equality should be used as
     * in the get method.
     *
     * Should have a running time of O(log n) for a balanced tree, and a worst
     * case of O(n).
     *
     * @throws IllegalArgumentException if the data is null
     * @param data the data to search for in the tree.
     * @return whether or not the parameter is contained within the tree.
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException(
                    "Given data is null. Cannot add to BST");
        }
        if (size == 0) {
            return false;
        } else {
            return containsHelper(root, data);
        }
    }

    /**
     * recursive helper method for contains method
     * @param node starts at root and moves around based on comparison to data
     * @param data given data that we want to verify if exists in BST
     * @return true if given data is in the tree false otherwise
     */
    private boolean containsHelper(BSTNode<T> node, T data) {
        if (node == null) {
            return false;
        } else if (node.getData().compareTo(data) == 0) {
            return true;
        } else if (data.compareTo(node.getData()) < 0) {
            return containsHelper(node.getLeft(), data);
        } else if (data.compareTo(node.getData()) > 0) {
            return containsHelper(node.getRight(), data);
        }
        return false;
    }

    /**
     * Should run in O(n).
     *
     * @return a preorder traversal of the tree
     */
    public List<T> preorder() {
        List<T> set = new ArrayList<T>();
        return preorderHelper(root, set);
    }

    /**
     * revursive helper method for preorder method
     * @param current starting root
     * @param set list to be added
     * @return list in preorder
     */
    private List<T> preorderHelper(BSTNode<T> current, List<T> set) {
        if (current == null) {
            return set;
        }
        set.add(current.getData());
        preorderHelper(current.getLeft(), set);
        preorderHelper(current.getRight(), set);
        return set;
    }

    /**
     * Should run in O(n).
     *
     * @return an inorder traversal of the tree
     */
    public List<T> inorder() {
        List<T> set = new ArrayList<T>();
        return inorderHelper(root, set);
    }

    /**
     * recursive helper method for inorder method
     * @param current starting root of BST
     * @param set set to be added
     * @return list in inorder
     */
    private List<T> inorderHelper(BSTNode<T> current, List<T> set) {
        if (current == null) {
            return set;
        }
        inorderHelper(current.getLeft(), set);
        set.add(current.getData());
        inorderHelper(current.getRight(), set);
        return set;
    }

    /**
     * Should run in O(n).
     *
     * @return a postorder traversal of the tree
     */
    public List<T> postorder() {
        List<T> set = new ArrayList<T>();
        return postorderHelper(root, set);
    }

    /**
     * recursive helper method for postorder method
     * @param current starting root
     * @param set list to be added
     * @return list in postorder
     */
    private List<T> postorderHelper(BSTNode<T> current, List<T> set) {
        if (current == null) {
            return set;
        }
        postorderHelper(current.getLeft(), set);
        postorderHelper(current.getRight(), set);
        set.add(current.getData());
        return set;

    }

    /**
     * Generate a level-order traversal of the tree.
     *
     * To do this, add the root node to a queue. Then, while the queue isn't
     * empty, remove one node, add its data to the list being returned, and add
     * its left and right child nodes to the queue. If what you just removed is
     * {@code null}, ignore it and continue with the rest of the nodes.
     *
     * Should run in O(n). This does not need to be done recursively.
     *
     * @return a level order traversal of the tree
     */
    public List<T> levelorder() {
        return levelorderHelper(root);
    }

    /**
     * is a helper method for level order
     * @param current is where we start recursion
     * @return set in level order
     */
    private List<T> levelorderHelper(BSTNode<T> current) {
        Queue<BSTNode<T>> dummyQueue = new LinkedList<BSTNode<T>>();
        List<T> newList = new ArrayList<T>();
        if (size == 0) {
            return newList;
        }
        dummyQueue.add(current);
        newList.add(current.getData());
        while (!dummyQueue.isEmpty()) {
            BSTNode<T> next = dummyQueue.remove();
            if (next.getLeft() != null) {
                dummyQueue.add(next.getLeft());
                newList.add(next.getLeft().getData());
            }
            if (next.getRight() != null) {
                dummyQueue.add(next.getRight());
                newList.add(next.getRight().getData());
            }
        }
        return newList;
    }

    /**
     * Clears the tree.
     *
     * Should run in O(1).
     */
    public void clear() {
        root = null;
        size = 0;

    }

    /**
     * Calculate and return the height of the root of the tree. A node's
     * height is defined as {@code max(left.height, right.height) + 1}. A leaf
     * node has a height of 0 and a null child should be -1.
     *
     * Should be calculated in O(n).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {

        return heightHelper(root);
    }

    /**
     * recusrive helper method for height method
     * @param current that moves around the BST
     * @return the height of BST
     */
    private int heightHelper(BSTNode<T> current) {
        if (size == 0) {
            return -1;
        }
        if (current.getLeft() == null && current.getRight() == null) {
            return 0;
        }
        if (current.getLeft() != null && current.getRight() == null) {
            return heightHelper(current.getLeft()) + 1;
        } else if (current.getLeft() == null && current.getRight() != null) {
            return heightHelper(current.getRight()) + 1;
        } else {
            return Math.max(heightHelper(current.getLeft()) + 1,
                    heightHelper(current.getRight()) + 1);
        }
    }

    /**
     * Returns the size of the BST.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the number of elements in the tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }

    /**
     * Returns the root of the BST.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public BSTNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }
}
