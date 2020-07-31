package comppa.domain;

/**
 *
 * @author danielko
 */
public class HuffmanNode implements Comparable<HuffmanNode> {

    private byte nodeByte;
    private int  frequency;
    private HuffmanNode left, right;

    public HuffmanNode(byte nodeByte, int frequency) {
        this(nodeByte, frequency, null, null);
    }

    public HuffmanNode(int frequency, HuffmanNode left, HuffmanNode right) {
        this.frequency = frequency;
        this.left = left;
        this.right = right;
    }

    public HuffmanNode(byte nodeByte, int frequency,
                       HuffmanNode left, HuffmanNode right) {
        this.nodeByte = nodeByte;
        this.frequency = frequency;
        this.left  = left;
        this.right = right;
    }
    public int getFrequency() {
        return this.frequency;
    }

    public byte getNodeByte() {
        return this.nodeByte;
    }
    
    public HuffmanNode getLeft() {
        return this.left;
    }
    
    public HuffmanNode getRight() {
        return this.right;
    }

    public boolean isLeaf() {
        return this.left == null && this.right == null;
    }
    
    @Override
    public int compareTo(HuffmanNode other) {
        return this.frequency - other.frequency;
    }

    @Override
    public String toString() {
        return "Byte       : " + new String(new byte[] {this.nodeByte})
           + "\nFrequency  : " + this.frequency
           + "\nLeft Child : " + (this.left  != null ? "Yes" : "No")
           + "\nRight Child: " + (this.right != null ? "Yes" : "No");
    }
}
