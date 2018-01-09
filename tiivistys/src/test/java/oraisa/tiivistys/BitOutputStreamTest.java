package oraisa.tiivistys;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class BitOutputStreamTest {
    
    public BitOutputStreamTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    BitOutputStream array;
    @Before
    public void setUp() {
        array = new BitOutputStream();
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void addingAWholeByteWorks(){
        byte[] shouldBeArray = new byte[1];
        shouldBeArray[0] = 1;
        array.writeBits(1, 8);
        assertArrayEquals(shouldBeArray, array.getBytes());
    }

    @Test
    public void addingTwoWholeBytesWorks(){
        byte[] shouldBeArray = new byte[2];
        shouldBeArray[0] = 1;
        shouldBeArray[1] = 4;
        array.writeBits(1, 8);
        array.writeBits(4, 8);
        assertArrayEquals(shouldBeArray, array.getBytes());
    }

    @Test
    public void addingAHalfByteWorks(){
        byte[] shouldBeArray = new byte[1];
        shouldBeArray[0] = 32;
        array.writeBits(2, 4);
        assertArrayEquals(shouldBeArray, array.getBytes());
    }

    @Test
    public void addingTwoHalfBytesWorks(){
        byte[] shouldBeArray = new byte[1];
        shouldBeArray[0] = 32 + 4;
        array.writeBits(2, 4);
        array.writeBits(4, 4);
        assertArrayEquals(shouldBeArray, array.getBytes());
    }

    @Test
    public void addingAcrossByteBoundaryWorks(){
        byte[] shouldBeArray = new byte[2];
        shouldBeArray[0] = 1 + 8 + 16;
        shouldBeArray[1] = 64;
        array.writeBits((2 + 4), 6);
        array.writeBits((16 + 4), 6);
        assertArrayEquals(shouldBeArray, array.getBytes());
    }
}
