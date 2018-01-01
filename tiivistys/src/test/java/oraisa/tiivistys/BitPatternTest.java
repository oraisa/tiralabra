package oraisa.tiivistys;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class BitPatternTest {
    
    public BitPatternTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    BitPattern fullPattern;
    BitPattern oneBitPattern;
    BitPattern twoBitPattern;
    BitPattern withLeadingZero;
    BitPattern zeroPattern;
    BitPattern stopCode;
    @Before
    public void setUp() {
        fullPattern = new BitPattern((short)0xFFFF, 16, 0);
        oneBitPattern = new BitPattern(1, 1, 4);
        twoBitPattern = new BitPattern(2, 2, 5);
        withLeadingZero = new BitPattern(1, 2, 5);
        zeroPattern = new BitPattern(0, 1, 5);
        stopCode = BitPattern.createStopCode((short)5, 5);
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void addBitDoesntAllowAddingTo16BitPattern(){
        boolean exceptionCaught = false;
        try{
            fullPattern.addBit((byte)1);
        } catch(IllegalStateException e){
            exceptionCaught = true;
        }
        assertTrue("addBit should throw IllegalStateException.", exceptionCaught);
    }
    @Test
    public void addBitDoesntAllowAddingNegativeBit(){
        boolean exceptionCaught = false;
        try{
            oneBitPattern.addBit((byte)-1);
        } catch(IllegalArgumentException  e){
            exceptionCaught = true;
        }
        assertTrue("addBit should throw IllegalArgumentException.", exceptionCaught);
    }
    @Test
    public void addBitDoesntAllowAdding2Bit(){
        boolean exceptionCaught = false;
        try{
            oneBitPattern.addBit((byte)2);
        } catch(IllegalArgumentException e){
            exceptionCaught = true;
        }
        assertTrue("addBit should throw IllegalArgumentException.", exceptionCaught);
    }
    
    @Test
    public void addBitAddsBitToOneBitPattern(){
        BitPattern pattern = oneBitPattern.addBit((byte)1);
        assertEquals(2, pattern.getBitsInPattern());
        assertEquals(3, pattern.getPattern());
    }
    
    @Test
    public void addBitAddsBitToTwoBitPattern(){
        BitPattern pattern = twoBitPattern.addBit((byte)1);
        assertEquals(3, pattern.getBitsInPattern());
        assertEquals(5, pattern.getPattern());
    }
    
    @Test
    public void addBitDoesntChangeReplacement(){
        BitPattern pattern = oneBitPattern.addBit((byte)1);
        assertEquals(oneBitPattern.getReplacement(), pattern.getReplacement());
    }
    
    @Test
    public void oneBitPatternHasCorrectToString(){
        assertEquals("1", oneBitPattern.toString());
    }
    
    @Test
    public void twoBitPatternHasCorrectToString(){
        assertEquals("10", twoBitPattern.toString());
    }
    
    @Test
    public void fullPatternHasCorrectToString(){
        assertEquals("1111111111111111", fullPattern.toString());
    }
    
    @Test
    public void patternWithLeadingZeroHasCorrectToString(){
        assertEquals("01", withLeadingZero.toString());
    }
    
    @Test
    public void zeroPatternHasCorrectToString(){
        assertEquals("0", zeroPattern.toString());
    }
    
    @Test
    public void convertingOneBitPatternToBytesAndBackGivesTheSamePattern(){
        BitPattern convertedPattern = BitPattern.fromBytes(oneBitPattern.toBytes());
        assertEquals("Replacement: ", oneBitPattern.getReplacement(), convertedPattern.getReplacement());
        assertEquals("Pattern: ", oneBitPattern.getPattern(), convertedPattern.getPattern());
        assertEquals("Bits in pattern: ", oneBitPattern.getBitsInPattern(), convertedPattern.getBitsInPattern());
    }
    
    @Test
    public void convertingTwoBitPatternToBytesAndBackGivesTheSamePattern(){
        BitPattern convertedPattern = BitPattern.fromBytes(twoBitPattern.toBytes());
        assertEquals("Replacement: ", twoBitPattern.getReplacement(), convertedPattern.getReplacement());
        assertEquals("Pattern: ", twoBitPattern.getPattern(), convertedPattern.getPattern());
        assertEquals("Bits in pattern: ", twoBitPattern.getBitsInPattern(), convertedPattern.getBitsInPattern());
    }
    
    @Test
    public void convertingStopCodeToBytesAndBackGivesTheSamePattern(){
        BitPattern convertedPattern = BitPattern.fromBytes(stopCode.toBytes());
        assertEquals("Pattern: ", stopCode.getPattern(), convertedPattern.getPattern());
        assertEquals("Bits in pattern: ", stopCode.getBitsInPattern(), convertedPattern.getBitsInPattern());
    }
}
