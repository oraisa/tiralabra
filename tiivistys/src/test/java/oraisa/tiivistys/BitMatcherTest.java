package oraisa.tiivistys;


import oraisa.tiivistys.BitMatcher;
import oraisa.tiivistys.BitPattern;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class BitMatcherTest {

    public BitMatcherTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    BitMatcher someZerosMatcher;
    BitMatcher someOnesMatcher;
    BitMatcher someZerosSomeOnesMatcher;
    BitMatcher fourZerosFourOnesMatcher;

    @Before
    public void setUp() {
        byte[] someZeros = new byte[2];
        someZeros[0] = 0;
        someZeros[1] = 0;
        someZerosMatcher = new BitMatcher(someZeros);

        byte[] someOnes = new byte[2];
        someOnes[0] = (byte)0xFF;
        someOnes[1] = (byte)0xFF;
        someOnesMatcher = new BitMatcher(someOnes);

        byte[] someZerosSomeOnes = new byte[2];
        someZerosSomeOnes[0] = 0;
        someZerosSomeOnes[1] = (byte)0xFF;
        someZerosSomeOnesMatcher = new BitMatcher(someZerosSomeOnes);

        byte[] fourZerosFourOnes = new byte[1];
        fourZerosFourOnes[0] = (byte)0x0F;
        fourZerosFourOnesMatcher = new BitMatcher(fourZerosFourOnes);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void allZeroBytesMatchOneZeroPattern(){
        assertTrue(someZerosMatcher.matchBitPattern(new BitPattern(0, 1, 0)));
    }

    @Test
    public void allZeroBytesMatchAllZeroPattern(){
        assertTrue(someZerosMatcher.matchBitPattern(new BitPattern(0, 8, 0)));
    }

    @Test
    public void allZerosNoMatchAllOnePattern(){
        assertFalse(someZerosMatcher.matchBitPattern(new BitPattern(1, 1, 0)));
    }

    @Test
    public void allOneBytesMatchOneOnePattern(){
        assertTrue(someOnesMatcher.matchBitPattern(new BitPattern(1, 1, 0)));
    }

    @Test
    public void allOneBytesMatchAllOnePattern(){
        assertTrue(someOnesMatcher.matchBitPattern(new BitPattern((byte)0xFF, 8, 0)));
    }

    @Test
    public void someZerosSomeOnesMatchesSomeZerosThenSomeOnes(){
        assertTrue(someZerosSomeOnesMatcher.matchBitPattern(new BitPattern(0, 8, 0)));
        assertTrue(someZerosSomeOnesMatcher.matchBitPattern(new BitPattern((byte)0xFF, 8, 0)));
    }
    
    @Test
    public void someZerosSomeOnesMatchesSomeZerosSomeOnes(){
        assertTrue(someZerosSomeOnesMatcher.matchBitPattern(new BitPattern(0x00FF, 16, 0)));
    }

    @Test
    public void fourZerosFourOnesMatchesFourZerosFourOnes(){
        assertTrue(fourZerosFourOnesMatcher.matchBitPattern(new BitPattern((short)0x000F, 8, 0)));
    }

    @Test
    public void fourZerosFourOnesNoMatchFourOnesFourZeros(){
        assertFalse(fourZerosFourOnesMatcher.matchBitPattern(new BitPattern((short)0x00F0, 8, 0)));
    }

    @Test
    public void fourZerosFourOnesMatchesFourZerosThenFourOnes(){
        assertTrue(fourZerosFourOnesMatcher.matchBitPattern(new BitPattern(0, 4, 0)));
        assertTrue(fourZerosFourOnesMatcher.matchBitPattern(new BitPattern((short)0x000F, 4, 0)));
    }
    
    @Test
    public void bitsLeftIsCorrectInAtStart(){
        assertEquals(16, someZerosMatcher.bitsLeft());
    }
    
    @Test
    public void bitsLeftIsCorrectAfterMatch(){
        someZerosMatcher.matchBitPattern(new BitPattern(0, 4, 0));
        assertEquals(12, someZerosMatcher.bitsLeft());
    }
    
    @Test
    public void canMatchPatternWithMoreBitsThanMatcher(){
        someZerosMatcher.matchBitPattern(new BitPattern(0, 8, 0));
        someZerosMatcher.matchBitPattern(new BitPattern(0, 4, 0));
        someZerosMatcher.matchBitPattern(new BitPattern(0, 5, 0));
    }
    
    @Test
    public void patternWithZeroBitsDoesntMatch(){
        assertFalse(someZerosMatcher.matchBitPattern(new BitPattern(0, 0, 0)));
    }

}
