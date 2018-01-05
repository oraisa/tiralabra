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
    @Before
    public void setUp() {
        //This exaple is from http://math.mit.edu/~goemans/18310S15/huffman-notes.pdf
        //The encoding length for 2 is 5 instead of the 4 in the example because
        //the example doesn't include a stop code character.
        exampleFrequencies = new HashMap<Byte, Long>();
        exampleFrequencies.put((byte)1, 40L);
        exampleFrequencies.put((byte)2, 5L);
        exampleFrequencies.put((byte)3, 18L);
        exampleFrequencies.put((byte)4, 7L);
        exampleFrequencies.put((byte)5, 20L);
        exampleFrequencies.put((byte)6, 10L);
        
        
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
}
