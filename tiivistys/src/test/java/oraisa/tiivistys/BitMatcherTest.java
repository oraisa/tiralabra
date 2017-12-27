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
        assertEquals(true, someZerosMatcher.matchBitPattern(new BitPattern(0, 1, 0)));
    }

    @Test
    public void allZeroBytesMatchAllZeroPattern(){
        assertEquals(true, someZerosMatcher.matchBitPattern(new BitPattern(0, 8, 0)));
    }

    @Test
    public void allZerosNoMatchAllOnePattern(){
        assertEquals(false, someZerosMatcher.matchBitPattern(new BitPattern(1, 1, 0)));
    }

    @Test
    public void allOneBytesMatchOneOnePattern(){
        assertEquals(true, someOnesMatcher.matchBitPattern(new BitPattern(1, 1, 0)));
    }

    @Test
    public void allOneBytesMatchAllOnePattern(){
        assertEquals(true, someOnesMatcher.matchBitPattern(new BitPattern((byte)0xFF, 8, 0)));
    }

    @Test
    public void someZerosSomeOnesMatchesSomeZerosThenSomeOnes(){
        assertEquals(true, someZerosSomeOnesMatcher.matchBitPattern(new BitPattern(0, 8, 0)));
        assertEquals(true, someZerosSomeOnesMatcher.matchBitPattern(new BitPattern((byte)0xFF, 8, 0)));
    }

    @Test
    public void fourZerosFourOnesMatchesFourZerosFourOnes(){
        assertEquals(true, fourZerosFourOnesMatcher.matchBitPattern(new BitPattern((byte)0x0F, 8, 0)));
    }

    @Test
    public void fourZerosFourOnesNoMatchFourOnesFourZeros(){
        assertEquals(false, fourZerosFourOnesMatcher.matchBitPattern(new BitPattern((byte)0xF0, 8, 0)));
    }

    @Test
    public void fourZerosFourOnesMatchesFourZerosThenFourOnes(){
        assertEquals(true, fourZerosFourOnesMatcher.matchBitPattern(new BitPattern(0, 4, 0)));
        assertEquals(true, fourZerosFourOnesMatcher.matchBitPattern(new BitPattern((byte)0x0F, 4, 0)));
    }

}
