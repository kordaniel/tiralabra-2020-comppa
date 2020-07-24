package comppa;

/**
 *
 * @author danielko
 */
public class Huffman {

    public static void printCode(Node root, String s) {
        if (root.left == null && root.right == null) {
            if (Character.isLetter(root.c)) {
                while (s.length() < 15) {
                    s = "0"+s;
                }
                System.out.println(root.c + ": " + s);
            }
            return;
        }
        
        if (s.length() != 0 && s.length() % 4 == 0) {
            printCode(root.left,  s + " 0");
            printCode(root.right, s + " 1");
        } else {
            printCode(root.left,  s + "0");
            printCode(root.right, s + "1");
        }

    }
}
