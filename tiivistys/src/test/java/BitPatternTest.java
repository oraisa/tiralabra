
import oraisa.tiivistys.BitPattern;
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
    @Before
    public void setUp() {
        fullPattern = new BitPattern((byte)0xFF, 8, 0);
        oneBitPattern = new BitPattern(1, 1, 4);
        twoBitPattern = new BitPattern(2, 2, 5);
        withLeadingZero = new BitPattern(1, 2, 5);
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void addBitDoesntAllowAddingTo8BitPattern(){
        boolean exceptionCaught = false;
        try{
            fullPattern.addBit((byte)1);
        } catch(IllegalStateException e){
            exceptionCaught = true;
        }
        assertEquals("addBit should throw IllegalStateException.", true, exceptionCaught);
    }
    @Test
    public void addBitDoesntAllowAddingNegativeBit(){
        boolean exceptionCaught = false;
        try{
            oneBitPattern.addBit((byte)-1);
        } catch(IllegalArgumentException  e){
            exceptionCaught = true;
        }
        assertEquals("addBit should throw IllegalArgumentException.", true, exceptionCaught);
    }
    @Test
    public void addBitDoesntAllowAdding2Bit(){
        boolean exceptionCaught = false;
        try{
            oneBitPattern.addBit((byte)2);
        } catch(IllegalArgumentException e){
            exceptionCaught = true;
        }
        assertEquals("addBit should throw IllegalArgumentException.", true, exceptionCaught);
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
        assertEquals("11111111", fullPattern.toString());
    }
    
    @Test
    public void patternWithLeadingZeroHasCorrectToString(){
        assertEquals("01", withLeadingZero.toString());
    }
}
