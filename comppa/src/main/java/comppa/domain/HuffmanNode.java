package comppa.domain;

/**
 * Class that represents a node or leaf in an Huffman Trie (Prefix search tree).
 * In this application we model the Trie so that an edge to the left represents
 * the value of 0 and the right edge represents the value of 1.
 * @author danielko
 */
public class HuffmanNode implements Comparable<HuffmanNode> {

    private byte nodeByte;
    private int  frequency;
    private HuffmanNode left, right;

    public HuffmanNode(byte nodeByte) {
        this(nodeByte, 0, null, null);
    }

    public HuffmanNode(byte nodeByte, int frequency) {
        this(nodeByte, frequency, null, null);
    }

    public HuffmanNode(int frequency, HuffmanNode left, HuffmanNode right) {
        this((byte) 0, frequency, left, right);
    }

    public HuffmanNode(byte nodeByte, int frequency,
                       HuffmanNode left, HuffmanNode right) {
        if (frequency < 0) {
            throw new IllegalArgumentException("Parameter frequency can not be negative.");
        }

        this.nodeByte = nodeByte;
        this.frequency = frequency;
        this.left  = left;
        this.right = right;
    }

    /**
     * Simple getter that returns the frequency of the byte represented by this node.
     * @return The frequency for the byte this node represents.
     */
    public int getFrequency() {
        return this.frequency;
    }

    /**
     * Simple getter that returns the byte value of this node.
     * @return The byte this node represents.
     */
    public byte getNodeByte() {
        // @TODO: throw exception if this method is called for a non-leaf node?
        // This will currently return 0 if this node is not a leaf node, which is "wrong".
        return this.nodeByte;
    }

    /**
     * Returns the left child of this node.
     * @return The left child if this node has a left child, null otherwise.
     */
    public HuffmanNode getLeft() {
        return this.left;
    }

    /**
     * Returns the right child of this node.
     * @return The right child if this node has a right child, null otherwise.
     */
    public HuffmanNode getRight() {
        return this.right;
    }

    /**
     * Check whether this node is a leaf-node or a node that has children. Leaf nodes have meaningful byte values
     * and no children, where nodes that have children have no meaningful byte-values, but serve as building blocks
     * for the Trie.
     * @return
     */
    public boolean isLeaf() {
        boolean isLeaf = this.left == null && this.right == null;
        if (!isLeaf && (this.left == null || this.right == null)) {
            // @TODO: DELETE THIS CHECK, only used while developing the software
            throw new RuntimeException("Found a node with only one null child, ERRRRRRRRRRRRRRRROR!!!");
        }
        return isLeaf;
    }

    /**
     * This method implements the Comparable interface. Compares this object with the one passed as an
     * argument and returns an negative integer if the weight (frequency) of this node is less than the
     * one that we are comparing it to. Similarly returns 0 or positive depending on the result of the
     * comparison. That is the natural order of the nodes are so that the smallest one goes to the top.
     * @param other Node to compare this one against.
     * @return negative int if this has a lower weight, 0 if they are equal and positive int if this one has a bigger weight.
     */
    @Override
    public int compareTo(HuffmanNode other) {
        return this.frequency - other.frequency;
    }

    /**
     * Textual representation of this node. Returns the value of the byte, the frequency and yes or no
     * for both children depending on whether this node has children.
     * @return The textual representation of this node.
     */
    @Override
    public String toString() {
        return "Byte       : " + this.nodeByte
           + "\nFrequency  : " + this.frequency
           + "\nLeft Child : " + (this.left  != null ? "Yes" : "No")
           + "\nRight Child: " + (this.right != null ? "Yes" : "No");
    }

    /**
     * Prints this node and all it's children in the Trie formatted in a nice way.
     * Useful for debugging the application.
     * @return String containing the Trie with this Node as the root.
     */
    public String toStringAsTrieWithChildren() {
        StringBuilder sb = new StringBuilder();
        this.buildTrieString(sb, "", "");
        return sb.toString();
    }

    /**
     * Helper method for building the String representing the structure of the Trie.
     * @param sb StringBuilder object containing the String that represents the structure of the Trie
     * @param prefix The prefix for the string of this node.
     * @param childrenPrefix The prefix for the children of this node.
     */
    private void buildTrieString(StringBuilder sb, String prefix, String childrenPrefix) {
        sb.append(prefix);
        sb.append(this.nodeByte);
        sb.append("\n");

        if (this.isLeaf()) {
            return;
        }

        this.left.buildTrieString(sb,  childrenPrefix + "├── ", childrenPrefix + "|   ");
        this.right.buildTrieString(sb, childrenPrefix + "└── ", childrenPrefix + "    ");
    }

}
