package comppa.datastructures;

import comppa.domain.HuffmanNode;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

public class PriorityQueueTest {

    private java.util.PriorityQueue minPQueue;
    private PriorityQueue minQueue;

    private static Random random;
    private static int huffmanNodesSize;
    private static int huffmanNodesRandomSize;
    private static HuffmanNode[] huffmanNodes;       // sorted
    private static HuffmanNode[] huffmanNodesRandom; // randomized

    @BeforeClass
    public static void initializeClass() {
        int seed = 1337;
        random = new Random(seed);

        huffmanNodesSize = 256;
        huffmanNodesRandomSize = huffmanNodesSize * 2;

        huffmanNodes = new HuffmanNode[huffmanNodesSize];
        huffmanNodesRandom = new HuffmanNode[huffmanNodesRandomSize];

        int i = 0;
        for (; i < huffmanNodesSize; i++) {
            HuffmanNode node = new HuffmanNode((byte) (i - 128), i);
            huffmanNodes[i] = node;
            huffmanNodesRandom[i] = node;
        }

        for (; i < huffmanNodesRandomSize; i++) {
            huffmanNodesRandom[i] = new HuffmanNode((byte) (random.nextInt(256) - 128), random.nextInt(huffmanNodesSize));
        }

        // Shuffle array randomly
        for (int j = huffmanNodesRandom.length; j > 1; j--) {
            int k = random.nextInt(j);
            HuffmanNode temp = huffmanNodesRandom[j - 1];
            huffmanNodesRandom[j - 1] = huffmanNodesRandom[k];
            huffmanNodesRandom[k] = temp;
        }
    }

    @Before
    public void setUp() {
        this.minPQueue = new java.util.PriorityQueue();
        this.minQueue = new PriorityQueue();
    }

    @Test
    public void queueIsCreatedEmpty() {
        assertTrue(minQueue.isEmpty());
        assertEquals(0, minQueue.size());
    }

    @Test
    public void peekReturnsNullWhenEmpty() {
        assertNull(minQueue.peek());
    }

    @Test
    public void pollReturnsNullWhenEmpty() {
        assertNull(minQueue.poll());
    }

    @Test
    public void NodeCanBeAddedAndSizeGrows() {
        minQueue.add(huffmanNodes[0]);
        assertFalse(minQueue.isEmpty());
        assertEquals(1, minQueue.size());
    }

    @Test
    public void nodeCanBeAddedAndPeeked() {
        minQueue.add(huffmanNodes[10]);
        assertEquals(huffmanNodes[10], minQueue.peek());
        assertEquals(1, minQueue.size());
    }

    @Test
    public void nodeCanBeAddedAndPolledFromQueue() {
        minQueue.add(huffmanNodes[7]);
        assertEquals(huffmanNodes[7], minQueue.poll());
        assertTrue(minQueue.isEmpty());
    }

    @Test
    public void severalNodesFromMinToMaxCanBeAddedAndQueueWorks() {
        for (int i = 0; i < huffmanNodesSize; i++) {
            minQueue.add(huffmanNodes[i]);
            minPQueue.add(huffmanNodes[i]);

            assertEquals(minPQueue.size(), minQueue.size());
            assertEquals(minPQueue.peek(), minQueue.peek());
        }

        while (!minPQueue.isEmpty() || !minQueue.isEmpty()) {
            assertEquals(minPQueue.poll(), minQueue.poll());
        }
    }

    @Test
    public void severalNodesFromMaxToMinCanBeAddedAndQueueWorks() {
        for (int i = huffmanNodesSize - 1; i >= 0; i--) {
            minQueue.add(huffmanNodes[i]);
            minPQueue.add(huffmanNodes[i]);

            assertEquals(minPQueue.peek(), minQueue.peek());
        }

        while (!minPQueue.isEmpty() || !minQueue.isEmpty()) {
            assertEquals(minPQueue.poll(), minQueue.poll());
        }
    }

    @Test
    public void severalRandomNodesCanBeAddedAndQueueWorks() {
        for (int i = 0; i < huffmanNodesRandom.length; i++) {
            minQueue.add(huffmanNodesRandom[i]);
            minPQueue.add(huffmanNodesRandom[i]);

            assertEquals(minPQueue.peek(), minQueue.peek());
        }

        while (!minPQueue.isEmpty() || !minQueue.isEmpty()) {
            assertEquals(minPQueue.poll(), minQueue.poll());
        }
    }
}
