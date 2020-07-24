package comppa;

/**
 *
 * @author danielko
 */
public class Node implements Comparable<Node> {
    int frequency;
    char c;
    Node left;
    Node right;


    @Override
    public String toString() {
        return "Char: " + c + "\n Freq: " + frequency;
    }

    @Override
    public int compareTo(Node other) {
        return this.frequency - other.frequency;
    }
}
