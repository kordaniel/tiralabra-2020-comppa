package comppa.datastructures;

import comppa.domain.HuffmanNode;

import java.util.ArrayList;

/**
 * This class implements an priority queue. It is hardcoded to be a minimum Priority queue
 * for HuffManNodes.
 */
public class PriorityQueue {

    private final static int ROOT_NODE_INDEX = 1;

    private ArrayList<HuffmanNode> data;

    public PriorityQueue() {
        this.data = new ArrayList<>();
        this.data.add(null); // start indexing from 1
    }

    /**
     * Adds a new element to this priority queue.
     * @param node The element to be added.
     */
    public void add(HuffmanNode node) {
        // root node is at index 1
        this.data.add(node);
        if (this.size() < 2) {
            return;
        }
        this.raiseNodeUpToCorrectPos(this.size());
    }

    /**
     * Removes and returns the smallest item currently in this priority queue.
     * @return The smallest element currently in this priority queue. null if the queue is empty.
     */
    public HuffmanNode poll() {
        HuffmanNode polledNode = this.peek();
        if (polledNode == null) {
            return polledNode;
        }

        this.data.set(ROOT_NODE_INDEX, this.data.get(this.size()));
        this.data.remove(this.size());

        this.travelNodeDownToCorrectPos(ROOT_NODE_INDEX);

        return polledNode;
    }

    /**
     * Returns the smallest element this priority queue currently holds.
     * @return The smallest element in this priority queue. null if the queue is empty.
     */
    public HuffmanNode peek() {
        if (this.isEmpty()) {
            return null;
        }

        return this.data.get(ROOT_NODE_INDEX);
    }

    /**
     * Checks whether this priority queue is empty.
     * @return true, if the queue is empty, false otherwise.
     */
    public boolean isEmpty() {
        return this.size() == 0;
    }

    /**
     * The size of this Priority queue.
     * @return Number of elements stored in this priority queue.
     */
    public int size() {
        return this.data.size() - 1;
    }

    @Override
    public String toString() {
        return this.data.toString();
    }

    /**
     * Helper method that takes the index of one node, and moves it up the heap until
     * the heapProperty holds.
     * @param childIndex Index for the Node to be checked and possibly moved up the heap.
     */
    private void raiseNodeUpToCorrectPos(int childIndex) {
        int parentIndex = childIndex / 2;

        while (parentIndex > 0
                && !checkHeapProperty(this.data.get(parentIndex),
                                      this.data.get(childIndex))) {
            this.swap(parentIndex, childIndex);
            childIndex = parentIndex;
            parentIndex /= 2;
        }
    }

    /**
     * Helper (recursive) method that takes the index of one node, and moves it down the heap until
     * the heapProperty holds.
     * @param parentIndex Index for the Node to be checked and possibly moved down the heap.
     */
    private void travelNodeDownToCorrectPos(int parentIndex) {
        int leftChildIndex = 2 * parentIndex;
        int rightChildIndex = 2 * parentIndex + 1;
        int smallerChildIndex = -1;

        if (leftChildIndex >= this.data.size()) {
            return;
        }

        if (rightChildIndex < this.data.size()) {
            smallerChildIndex = this.getIndexForSmaller(leftChildIndex, rightChildIndex);
        } else {
            smallerChildIndex = leftChildIndex;
        }

        if (checkHeapProperty(this.data.get(parentIndex), this.data.get(smallerChildIndex))) {
            return;
        }

        swap(parentIndex, smallerChildIndex);
        travelNodeDownToCorrectPos(smallerChildIndex);
    }

    /**
     * Helper method to check if the Binary Heap Property is holding. For a minimum queue
     * this means that the value of the parent node is less than or equal to the values of it's children.
     * @param i1
     * @param i2
     * @return
     */
    private boolean checkHeapProperty(HuffmanNode parent, HuffmanNode child) {
        return parent.compareTo(child) <= 0;
    }

    /**
     * Helper method to get the index for the node with smaller or equal to value.
     * @param i1 First node to compare.
     * @param i2 Second node to compare.
     * @return The index for the node that is smaller, and if both nodes are equal the index i1.
     */
    private int getIndexForSmaller(int i1, int i2) {
        return this.checkHeapProperty(this.data.get(i1), this.data.get(i2)) ? i1 : i2;
    }

    /**
     * Helper method that swaps the elements at positions i1 and i2.
     * @param i1 The index for the first element to be swapped.
     * @param i2 The index for the second element to be swapped.
     */
    private void swap(int i1, int i2) {
        HuffmanNode temp = this.data.get(i1);
        this.data.set(i1, this.data.get(i2));
        this.data.set(i2, temp);
    }
}
