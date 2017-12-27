
import oraisa.tiivistys.HuffmanCodeCalculator;
import oraisa.tiivistys.BitPattern;
import oraisa.tiivistys.BitMatcher;
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
    byte[] exampleOptimalEncodingLengths;
    @Before
    public void setUp() {
        //This exaple is from http://math.mit.edu/~goemans/18310S15/huffman-notes.pdf
        exampleFrequencies = new HashMap<Byte, Long>();
        exampleFrequencies.put((byte)1, 40L);
        exampleFrequencies.put((byte)2, 5L);
        exampleFrequencies.put((byte)3, 18L);
        exampleFrequencies.put((byte)4, 7L);
        exampleFrequencies.put((byte)5, 20L);
        exampleFrequencies.put((byte)6, 10L);
        exampleOptimalEncodingLengths = new byte[7];
        exampleOptimalEncodingLengths[1] = 1;
        exampleOptimalEncodingLengths[2] = 4;
        exampleOptimalEncodingLengths[3] = 3;
        exampleOptimalEncodingLengths[4] = 4;
        exampleOptimalEncodingLengths[5] = 3;
        exampleOptimalEncodingLengths[6] = 3;
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void huffmanCodeCalculatorGivesAPrefixEncodingWithExample(){
        BitPattern[] huffmanCodes = HuffmanCodeCalculator.calculateHuffmanCodes(exampleFrequencies);
        for(BitPattern pattern: huffmanCodes){
            if(pattern != null && pattern.getBitsInPattern() != 0){
                byte[] patternByte = new byte[1];
                patternByte[0] = (byte)(pattern.getPattern() << (8 - pattern.getBitsInPattern()));
                BitMatcher matcher = new BitMatcher(patternByte);
                for(BitPattern otherPattern: huffmanCodes){
                    if(otherPattern != null && otherPattern != pattern){
                        assertEquals("Patterns " + pattern.toString() + " and " + 
                                otherPattern.toString(),
                                false, matcher.matchBitPattern(otherPattern));
                    }
                }
            }
        }
    }
    
    public void huffmanCodeCalculatorHasOptimalCodeLengthsWithExample(){
        BitPattern[] huffmanCodes = HuffmanCodeCalculator.calculateHuffmanCodes(exampleFrequencies);
        for(int i = 1; i < exampleOptimalEncodingLengths.length; i++){
            assertEquals("Encoding for character " + i + ": ", exampleOptimalEncodingLengths[i], 
                    huffmanCodes[i - Byte.MIN_VALUE].getBitsInPattern());
        }
    }
}
