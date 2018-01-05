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
    
    Map<Byte, Long> exampleFrequencies;
    Map<Byte, Long> characterFrequenciesInFlippedBytes;
    HuffmanTreeNodeHeap heap;
    @Before
    public void setUp() {
        //This exaple is from https://web.stanford.edu/class/archive/cs/cs106b/cs106b.1126/handouts/220%20Huffman%20Encoding.pdf
        exampleFrequencies = new HashMap<Byte, Long>();
        exampleFrequencies.put((byte)1, 3L);//h
        exampleFrequencies.put((byte)2, 1L);//a
        exampleFrequencies.put((byte)3, 4L);//p
        exampleFrequencies.put((byte)4, 1L);//y
        exampleFrequencies.put((byte)5, 1L);//i
        exampleFrequencies.put((byte)6, 2L);//space
        //o represent the stop code
        
        
        byte[] flippedBytes = new byte[1000];
        for(int i = 0; i < 256; i++){
            int j = i * 3;

            flippedBytes[j] = (byte)i;
            flippedBytes[j + 1] = 8;
            flippedBytes[j + 2] = (byte)(255 - i);
        }
        characterFrequenciesInFlippedBytes = new HashMap<Byte, Long>();
        for(int i = 0; i < flippedBytes.length; i++){
            byte byt = flippedBytes[i];
            if(characterFrequenciesInFlippedBytes.containsKey(byt)){
                characterFrequenciesInFlippedBytes.put(byt, characterFrequenciesInFlippedBytes.get(byt) + 1);
            } else {
                characterFrequenciesInFlippedBytes.put(byt, 1L);
            }
        }
        
        heap = new HuffmanTreeNodeHeap(20);
    }
    
    @After
    public void tearDown() {
    }
    
    private void testAllCharacters(BitPattern[] huffmanCodes, int firstCharacter, int lastCharacter){
        for(int i = firstCharacter; i <= lastCharacter; i++){
            assertNotNull("Encoding for " + i + ": ", huffmanCodes[i - Byte.MIN_VALUE]);
        }
    }
    
    private void testPrefixEncoding(BitPattern[] huffmanCodes){
        for(BitPattern pattern: huffmanCodes){
            if(pattern != null && pattern.getBitsInPattern() != 0){
                BitArray patternArray = new BitArray();
                patternArray.addBitPattern(pattern);
                byte[] patternBytes = patternArray.getBytes();
                BitMatcher matcher = new BitMatcher(patternBytes);
                for(BitPattern otherPattern: huffmanCodes){
                    if(otherPattern != null && otherPattern != pattern){
                        assertFalse("Patterns " + pattern.toString() + " and " + 
                                otherPattern.toString(),
                                matcher.matchBitPattern(otherPattern));
                    }
                }
            }
        }
    }
    
    @Test
    public void huffmanCodeCalculatorHasEncodingForAllCharactersInExample(){
        BitPattern[] huffmanCodes = HuffmanCodeCalculator.calculateHuffmanCodes(exampleFrequencies);
        testAllCharacters(huffmanCodes, 1, 6);
    }
    
    @Test
    public void huffmanCodeCalculatorHasEncodingForAllCharactersInFlippedEncoding(){
        BitPattern[] huffmanCodes = HuffmanCodeCalculator.calculateHuffmanCodes(characterFrequenciesInFlippedBytes);
        testAllCharacters(huffmanCodes, Byte.MIN_VALUE, Byte.MAX_VALUE);
    }

    @Test
    public void huffmanCodeCalculatorGivesAPrefixEncodingWithExample(){
        BitPattern[] huffmanCodes = HuffmanCodeCalculator.calculateHuffmanCodes(exampleFrequencies);
        testPrefixEncoding(huffmanCodes);
    }
    
    @Test
    public void huffmanCodeCalculatorGiveAPrefixEncodingForFlippedData(){
        BitPattern[] huffmanCodes = HuffmanCodeCalculator.calculateHuffmanCodes(characterFrequenciesInFlippedBytes);
        testPrefixEncoding(huffmanCodes);
    }
    
    @Test
    public void huffmanCodeCalculatorGivesOptimalEncodingWithExample(){
        BitPattern[] huffmanCodes = HuffmanCodeCalculator.calculateHuffmanCodes(exampleFrequencies);
        int encodedLength = 0;
        for(Byte key: exampleFrequencies.keySet()){
            long freq = exampleFrequencies.get(key);
            encodedLength += freq * huffmanCodes[key - Byte.MIN_VALUE].getBitsInPattern();
        }
        encodedLength += huffmanCodes[huffmanCodes.length - 1].getBitsInPattern();
        assertEquals(34, encodedLength);
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
