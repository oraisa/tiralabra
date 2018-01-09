package oraisa.tiivistys;

import java.util.*;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class HuffmanCodeCalculatorTest {
    
    public HuffmanCodeCalculatorTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    HuffmanTreeNodeHeap heap;
    @Before
    public void setUp() {
        
        heap = new HuffmanTreeNodeHeap(20);
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void huffmanTreeNodeHeapReturnsValueAfterInsert(){
        heap.insert(new HuffmanTreeNode((byte)0, 5));
        assertEquals(5, heap.poll().getFrequency());
    }
    
    @Test
    public void huffmanTreeNodeHeapReturnsSmallerValueAfterTwoInserts(){
        heap.insert(new HuffmanTreeNode((byte)0, 10));
        heap.insert(new HuffmanTreeNode((byte)0, 4));
        assertEquals(4, heap.poll().getFrequency());
    }
    
    @Test
    public void huffmanTreeNodeHeapReturnsValuesInCorrectOrderAferManyInserts(){
        heap.insert(new HuffmanTreeNode((byte)0, 5));
        heap.insert(new HuffmanTreeNode((byte)0, 3));
        heap.insert(new HuffmanTreeNode((byte)0, 7));
        heap.insert(new HuffmanTreeNode((byte)0, 200));
        heap.insert(new HuffmanTreeNode((byte)0, 34));
        heap.insert(new HuffmanTreeNode((byte)0, 1));
        assertEquals(1, heap.poll().getFrequency());
        assertEquals(3, heap.poll().getFrequency());
        assertEquals(5, heap.poll().getFrequency());
        assertEquals(7, heap.poll().getFrequency());
        assertEquals(34, heap.poll().getFrequency());
        assertEquals(200, heap.poll().getFrequency());
    }
    
    @Test
    public void huffmanTreeNodeHeapReturnsValuesInCorrectOrderAferManyInsertsOutOfOrder(){
        heap.insert(new HuffmanTreeNode((byte)0, 5));//5
        heap.insert(new HuffmanTreeNode((byte)0, 3));//3, 5
        heap.insert(new HuffmanTreeNode((byte)0, 7));//3, 5, 7
        assertEquals(3, heap.poll().getFrequency());//5, 7
        heap.insert(new HuffmanTreeNode((byte)0, 34));//5, 7, 34
        heap.insert(new HuffmanTreeNode((byte)0, 20));//5, 7, 20, 34
        assertEquals(5, heap.poll().getFrequency());//7, 20, 34
        assertEquals(7, heap.poll().getFrequency());//20, 34
        heap.insert(new HuffmanTreeNode((byte)0, 10));//10, 20, 34
        assertEquals(10, heap.poll().getFrequency());//20, 34
        heap.insert(new HuffmanTreeNode((byte)0, 7));//7. 20, 34
        assertEquals(7, heap.poll().getFrequency());//20, 34
        assertEquals(20, heap.poll().getFrequency());//34
        assertEquals(34, heap.poll().getFrequency());
    }
}
