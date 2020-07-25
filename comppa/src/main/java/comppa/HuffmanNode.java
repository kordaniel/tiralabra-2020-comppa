package comppa;

/**
 *
 * @author danielko
 */
public class HuffmanNode implements Comparable<HuffmanNode> {
    
    private char character;
    private int  frequency;
    HuffmanNode left, right;

    public HuffmanNode(char character, int frequency) {
        this(character, frequency, null, null);
    }
    
    public HuffmanNode(char character, int frequency,
            HuffmanNode left, HuffmanNode right) {
        this.character = character;
        this.frequency = frequency;
        this.left = left;
        this.right = right;
    }

    public int getFrequency() {
        return this.frequency;
    }
    
    public char getCharacter() {
        return this.character;
    }
    
    public HuffmanNode getLeft() {
        return this.left;
    }
    
    public HuffmanNode getRight() {
        return this.right;
    }
    
    @Override
    public int compareTo(HuffmanNode other) {
        return this.frequency - other.frequency;
    }
    
}
