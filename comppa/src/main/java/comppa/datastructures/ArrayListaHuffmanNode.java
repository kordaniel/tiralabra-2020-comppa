package comppa.datastructures;

import comppa.domain.Constants;
import comppa.domain.HuffmanNode;

public class ArrayListaHuffmanNode {

    // This class is basically a copypaste from the ArrayLista. Avoiding generics allows us to use actual
    // primitive bytes instead of byte-like objects in the original ArrayLista.

    // The tests for this class is limited to the remove()-method, since all other functionality is exactly
    // the same (implementation) as in the ArrayLista-class, which is throughly tested.

    // The default size of a newly created ArrayLista
    public static final int DEFAULT_SIZE = 8;
    // The default factor used for expanding this Array
    public static final int DEFAULT_GROW_FACTOR = 2;

    // The actual HuffmanNodes stored in this instance of ArrayLista
    private HuffmanNode[] data;
    // The current size of this instance of ArrayLista. This variable is used as the index
    // when appending new objects to the end of this ArrayLista.
    private int size;

    /**
     * Constructs a new ArrayLista with default initial size.
     */
    public ArrayListaHuffmanNode() {
        this(DEFAULT_SIZE);
    }

    /**
     * Constructs a new ArrayLista with the size specified as the argument.
     * @param initialSize The size for the new object.
     */
    public ArrayListaHuffmanNode(int initialSize) {
        this.data = new HuffmanNode[initialSize];
        this.size = 0;
    }

    /**
     * Appends a new HuffmanNode to the end of this object.
     * @param node The Huffman node to be appended to the end of this ArrayLista object.
     */
    public void add(HuffmanNode node) {
        if (this.size() == this.data.length) {
            this.expand();
        }

        this.data[this.size++] = node;
    }

    /**
     * Sets the node at index i to be the node given as argument.
     * @param i The index of the Node to be set.
     * @param node The node object to be stored at the specified index.
     */
    public void set(int i,  HuffmanNode node) {
        this.expand(i + 1); // Make sure the array length is long enough.

        this.data[i] = node;

        if (i >= this.size()) {
            this.size = ++i;
        }
    }

    /**
     * Removes the current element at the specified index. Reduces the size of this Arraylista if the removed
     * element is at the end.
     * @param i Index of the element to be removed.
     * @throws IndexOutOfBoundsException If passed an index that is negative or at
     * least equal to the size of this object.
     */
    public void remove(int i) {
        if (i < 0 || i >= this.size()) {
            throw new IndexOutOfBoundsException(
                    "Index " + i + " out of bounds for Arraylista with size of " + this.size()
            );
        }

        if (i == this.size() - 1) {
            this.size--;
        }

        this.data[i] = null;
    }

    /**
     * Returns the node at the specified index.
     * @param i The index of the node to be returned.
     * @return The node at the specified index. Can be null.
     * @throws IndexOutOfBoundsException If passed an index that is negative or at
     * least equal to the size of this object.
     */
    public HuffmanNode get(int i) {
        if (i < 0 || i >= this.size()) {
            throw new IndexOutOfBoundsException(
                    "Index " + i + " out of bounds for Arraylista with size of " + this.size()
            );
        }

        return this.data[i];
    }

    /**
     * Returns a new array object containing all the nodes stored in this ArrayLista.
     * @return HuffmanNode[] array containing all the nodes stored in this ArrayLista.
     */
    public HuffmanNode[] toArray() {
        HuffmanNode[] nodes = new HuffmanNode[this.size()];

        for (int i = 0; i < this.size(); i++) {
            nodes[i] = this.data[i];
        }

        return nodes;
    }

    /**
     * The size of this object. That is, how many nodes are currently stored in this object.
     * @return The size of this object.
     */
    public int size() {
        return this.size;
    }

    /**
     * Checks whether this object is empty or not.
     * @return boolean, true if this object is empty and false otherwise.
     */
    public boolean isEmpty() {
        return this.size() == 0;
    }

    /**
     * Helper method that calculates the correct required size for the array and if needed,
     * allocates space to the array.
     * @param requiredSize
     */
    private void expand(int requiredSize) {
        int newSize = this.data.length;

        while (newSize < requiredSize) {
            newSize = newSize * DEFAULT_GROW_FACTOR < newSize
                    ? Constants.INT_MAX_VAL
                    : newSize * DEFAULT_GROW_FACTOR;
        }

        allocate(newSize);
    }

    /**
     * Helper method that does required checks and calculations for expanding the array with DEFAULT_GROW_FACTOR.
     * @throws OutOfMemoryError If the Array is growing too large.
     */
    private void expand() {
        if (this.size() == Constants.INT_MAX_VAL) {
            throw new OutOfMemoryError("Cannot add more elements to this ArrayLista of length + " + this.size());
        }

        // Try to expand the size by multiplying with DEFAULT_GROW_FACTOR. Check for integer overflow
        // and if the newSize overflows, then expand the array to the max possible size. For most environments
        // it is required to adjust the max heap space for the Java VM to be able to use arrays this big.
        int newSize = this.data.length * DEFAULT_GROW_FACTOR;
        newSize = newSize < this.data.length ? Constants.INT_MAX_VAL : newSize;

        allocate(newSize);
    }

    /**
     * Helper method that instantiates a new HuffmanNode-array with the size passed as argument and copies all stored bytes
     * into the new array.
     * @param newSize The size of the expanded ArrayLista.
     */
    private void allocate(int newSize) {
        HuffmanNode[] newArr = new HuffmanNode[newSize];

        for (int i = 0; i < this.size(); i++) {
            newArr[i] = this.data[i];
        }

        this.data = newArr;
    }

}
