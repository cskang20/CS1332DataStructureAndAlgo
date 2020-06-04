import java.util.Collection;

/**
 * Your implementation of an AVL Tree.
 *
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class AVL<T extends Comparable<? super T>> {
    // DO NOT ADD OR MODIFY INSTANCE VARIABLES.
    private AVLNode<T> root;
    private int size;

    /**
     * A no-argument constructor that should initialize an empty AVL.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public AVL() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Initializes the AVL tree with the data in the Collection. The data
     * should be added in the same order it appears in the Collection.
     *
     * @param data the data to add to the tree
     * @throws IllegalArgumentException if data or any element in data is null
     */
    public AVL(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException(
                    "Cannot Create AVL with null data.");
        }

        root = new AVLNode<T>(data.iterator().next());
        size = 1;
        for (T element : data) {
            if (element == null) {
                throw new IllegalArgumentException(
                        "Cannot add null data to AVL.");
            } else {
                add(element);
            }
        }
    }

    /**
     * Adds the data to the AVL. Start by adding it as a leaf like in a regular
     * BST and then rotate the tree as needed.
     *
     * If the data is already in the tree, then nothing should be done (the
     * duplicate shouldn't get added, and size should not be incremented).
     *
     * Remember to recalculate heights and balance factors going up the tree,
     * rebalancing if necessary.
     *
     * @throws java.lang.IllegalArgumentException if the data is null
     * @param data the data to be added
     */
    public void add(T data) {
        if (data == null) {
            throw new java.lang.IllegalArgumentException(
                    "Cannot add null data in to AVL tree.");
        }
        root = addHelper(root, data);
    }

    /**
     * this method adds node
     * @param current Moving current node
     * @param data node that are being added
     * @return return added Node
     */
    private AVLNode<T> addHelper(AVLNode<T> current, T data) {

        if (current == null) {
            size++;
            return new AVLNode<T>(data);

        } else if (data.compareTo(current.getData()) < 0) {
            current.setLeft(addHelper(current.getLeft(), data));

        } else if (data.compareTo(current.getData()) > 0) {
            current.setRight(addHelper(current.getRight(), data));
        }

        updateHBF(current);
        int balance = current.getBalanceFactor();

        //current > data (Left-Left Case)
        if (balance > 1 && data.compareTo(current.getLeft().getData()) < 0) {
            return rotateRight(current);

            //current < data (Right-Right Case)
        } else if (balance < -1 && data
                .compareTo(current.getRight().getData()) > 0) {
            return rotateLeft(current);

            //current < data (Left Right Case)
        } else if (balance > 1 && data
                .compareTo(current.getLeft().getData()) > 0) {
            current.setLeft(rotateLeft(current.getLeft()));
            return rotateRight(current);

            //current > data (Right Left Case)
        } else if (balance < -1 && data
                .compareTo(current.getRight().getData()) < 0) {
            current.setRight(rotateRight(current.getRight()));
            return rotateLeft(current);

        }
        return current;

    }

    /**
     * Removes the data from the tree. There are 3 cases to consider:
     *
     * 1: the data is a leaf. In this case, simply remove it.
     * 2: the data has one child. In this case, simply replace it with its
     * child.
     * 3: the data has 2 children. Use the predecessor to replace the data,
     * not the successor. As a reminder, rotations can occur after removing
     * the predecessor node.
     *
     * Remember to recalculate heights going up the tree, rebalancing if
     * necessary.
     *
     * @throws IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException if the data is not found
     * @param data the data to remove from the tree.
     * @return the data removed from the tree. Do not return the same data
     * that was passed in.  Return the data that was stored in the tree.
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException(
                    "Cannot remove. Given data is null.");
        }
        AVLNode<T> dummy = new AVLNode<T>(null);
        root = removeHelper(root, data, dummy);
        return dummy.getData();

    }

    /**
     * recursive helper method for remove
     * @param current moving node starting with root
     * @param data node that are being added
     * @param dummy dummy variable to store data being removed
     * @return removed data
     */
    private AVLNode<T> removeHelper(
            AVLNode<T> current, T data, AVLNode<T> dummy) {

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
            } else if (
                    current.getLeft() != null && current.getRight() == null) {
                return current.getLeft();

                // 1 Child Case (left child empty)
            } else if (
                    current.getRight() != null && current.getLeft() == null) {
                return current.getRight();

                // 2 Children Case
            } else {
                AVLNode<T> dummy2 = new AVLNode<T>(null);
                current.setLeft(removePredecessor(current.getLeft(), dummy2));
                current.setData(dummy2.getData());

            }

        }

        updateHBF(current);

        int balance = current.getBalanceFactor();

        if (balance > 1 && current.getLeft().getBalanceFactor() >= 0) {
            return rotateRight(current);

        } else if (balance > 1 && current.getLeft().getBalanceFactor() < 0) {
            current.setLeft(rotateLeft(current.getLeft()));
            return rotateRight(current);

        } else if (balance < -1 && current.getRight().getBalanceFactor() <= 0) {
            return rotateLeft(current);

        } else if (balance < -1 && current.getRight().getBalanceFactor() > 0) {
            current.setRight(rotateRight(current.getRight()));
            return rotateLeft(current);
        }

        return current;
    }



    /**
     * Helper method that find the getSuccessor
     * @param current current node moving around BST
     * @param dummy dummy variable to store Predecessor
     * @return the parent node of Predecessor
     */
    private AVLNode<T> removePredecessor(AVLNode<T> current, AVLNode<T> dummy) {
        //base case
        if (current.getRight() == null) {
            dummy.setData(current.getData());
            return current.getLeft();

        } else {
            current.setRight(removePredecessor(current.getRight(), dummy));
            updateHBF(current);

            if (current.getBalanceFactor() > 1 && current.getLeft().getBalanceFactor() >= 0) {
                return rotateRight(current);

            } else if (current.getBalanceFactor() > 1 && current.getLeft().getBalanceFactor() < 0) {
                current.setLeft(rotateLeft(current.getLeft()));
                return rotateRight(current);

            } else if (current.getBalanceFactor() < -1 && current.getRight().getBalanceFactor() <= 0) {
                return rotateLeft(current);

            } else if (current.getBalanceFactor() < -1 && current.getRight().getBalanceFactor() > 0) {
                current.setRight(rotateRight(current.getRight()));
                return rotateLeft(current);
            }
        }

        return current;
    }


    /**
     * Returns the height of the root of the tree.
     *
     * Since this is an AVL, this method does not need to traverse the tree
     * and should be O(1)
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        if (root == null) {
            return -1;

        } else {
            return root.getHeight();
        }

    }

    /**
     * this method updates Height and Balance Factor
     * @param node node that are being added
     */
    private void updateHBF(AVLNode<T> node) {
        node.setBalanceFactor(
                (easyH(node.getLeft())) - (easyH(node.getRight())));
        node.setHeight(1 + Math.max(
                easyH(node.getLeft()), easyH(node.getRight())));
    }


    /**
     * this method calculates Height of node passed in
     * @param node node that are being added
     * @return the height of a node
     */
    private int easyH(AVLNode<T> node) {
        if (node == null) {
            return -1;
        } else {
            return node.getHeight();
        }
    }

    /**
     * this method rotates the AVL tree to right
     * @param currentNode current moving nodes
     * @return returns a new root after rotation to right
     */
    private AVLNode<T> rotateRight(AVLNode<T> currentNode) {
        AVLNode<T> temp = currentNode.getLeft();
        currentNode.setLeft(temp.getRight());
        temp.setRight(currentNode);
        updateHBF(currentNode);
        updateHBF(temp);
        return temp;

    }

    /**
     * this method rotates the AVL tree to left
     * @param currentNode current moving nodes
     * @return returns a new root after rotation to left
     */
    private AVLNode<T> rotateLeft(AVLNode<T> currentNode) {

        AVLNode<T> temp = currentNode.getRight();
        currentNode.setRight(temp.getLeft());
        temp.setLeft(currentNode);
        updateHBF(currentNode);
        updateHBF(temp);
        return temp;

    }


    /**
     * Returns the data in the tree matching the parameter passed in (think
     * carefully: should you use value equality or reference equality?).
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
    private T getHelper(AVLNode<T> current, T data) {
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
     * @throws IllegalArgumentException if the data is null
     * @param data the data to search for in the tree.
     * @return whether or not the parameter is contained within the tree.
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException(
                    "Cannot add. Given data is null.");
        }

        if (size == 0) {
            return false;
        } else {
            return containsHelper(root, data);
        }

    }

    /**
     * recrusive helper method for contains method
     * @param node node to be compared with data
     * @param data data to be compared with node
     * @return whether the data is contained in avl or not
     */
    private boolean containsHelper(AVLNode<T> node, T data) {
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
     * Clears the tree.
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Returns the size of the AVL tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return number of items in the AVL tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD
        return size;
    }

    /**
     * Returns the root of the AVL tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the AVL tree
     */
    public AVLNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }
}
