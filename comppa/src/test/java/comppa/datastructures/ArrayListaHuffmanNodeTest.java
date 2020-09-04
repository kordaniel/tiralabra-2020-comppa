package comppa.datastructures;

import comppa.domain.HuffmanNode;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ArrayListaHuffmanNodeTest {

    private ArrayListaHuffmanNode aLista;

    @Before
    public void setUp() {
        aLista = new ArrayListaHuffmanNode();
    }

    @Test
    public void removeTheOnlyElementMakesTheListEmpty() {
        aLista.add(new HuffmanNode((byte) 0, 9));
        aLista.remove(0);
        assertTrue(aLista.isEmpty());
    }

    @Test
    public void removingTheLastElementReducesTheListSize() {
        for (int i = 0; i < 5; i++) {
            aLista.add(new HuffmanNode((byte) i, i));
        }

        for (int i = 4; i >= 0; i--) {
            assertEquals(i + 1, aLista.size());
            aLista.remove(i);
            assertEquals(i, aLista.size());
        }

        assertTrue(aLista.isEmpty());
    }

    @Test
    public void removingFromMiddleWorks() {
        int size = 20;

        for (int i = 0; i < size; i++) {
            aLista.add(new HuffmanNode((byte) i, i));
        }

        for (int i = 1; i < size; i += 3) {
            aLista.remove(i);

            if (i != 19) {
                assertNull(aLista.get(i));
                assertEquals(size, aLista.size());
            } else {
                assertEquals(size - 1, aLista.size());
            }
        }
    }
}
