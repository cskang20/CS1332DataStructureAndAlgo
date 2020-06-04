/**
 * Your implementation of a linked stack. It should NOT be circular.
 *
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class LinkedStack<T> {

    // Do not add new instance variables.
    private LinkedNode<T> head;
    private int size;

    /**
     * Adds the given data onto the stack. The given element becomes the
     * top-most element of the stack.
     *
     * This method should be implemented in O(1) time.
     *
     * @param data the data to add
     * @throws IllegalArgumentException if data is null
     */
    public void push(T data) {
        if (data == null) {
            throw new IllegalArgumentException(
                    "Cannot insert null data into data structure");
        }

        LinkedNode<T> newNode = new LinkedNode<T>(data);

        if (size == 0) {
            head = newNode;

        } else {

            newNode.setNext(head);
            head = newNode;
        }
        size++;

    }

    /**
     * Removes and returns the top-most element on the stack.
     *
     * This method should be implemented in O(1) time.
     *
     * @return the data from the top of the stack
     * @throws java.util.NoSuchElementException if the stack is empty
     */
    public T pop() {
        if (size == 0) {
            throw new java.util.NoSuchElementException(
                    "No data to remove since data structure is empty");
        }

        T returnData = null;

        if (head.getNext() == null) {
            returnData = head.getData();
            head = null;
            size--;
            return returnData;

        } else {
            returnData = head.getData();
            head = head.getNext();
            size--;
            return returnData;
        }

    }


    /**
     * Retrieves the next element to be popped without removing it.
     *
     * This method should be implemented in O(1) time.
     *
     * @return the next data or null if the stack is empty
     */
    public T peek() {

        T returnData = null;

        if (size == 0) {
            return null;

        } else {
            returnData = head.getData();
            return returnData;
        }
    }

    /**
     * Return the size of the stack.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return number of items in the stack
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }

    /**
     * Returns the head node of the stack.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the head node
     */
    public LinkedNode<T> getHead() {
        // DO NOT MODIFY THIS METHOD!
        return head;
    }

}
