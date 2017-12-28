package oraisa.tiivistys;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class BitArrayTest {
    
    public BitArrayTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    BitArray array;
    BitArray arrayWithInitialData;
    @Before
    public void setUp() {
        array = new BitArray();
        byte[] initialData = new byte[2];
        initialData[0] = 4;
        arrayWithInitialData = new BitArray(initialData);
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void addingAWholeByteWorks(){
        byte[] shouldBeArray = new byte[1];
        shouldBeArray[0] = 1;
        array.addBits((byte)1, (byte)8);
        assertArrayEquals(shouldBeArray, array.getBytes());
    }

    @Test
    public void addingTwoWholeBytesWorks(){
        byte[] shouldBeArray = new byte[2];
        shouldBeArray[0] = 1;
        shouldBeArray[1] = 4;
        array.addBits((byte)1, (byte)8);
        array.addBits((byte)4, (byte)8);
        assertArrayEquals(shouldBeArray, array.getBytes());
    }

    @Test
    public void addingAHalfByteWorks(){
        byte[] shouldBeArray = new byte[1];
        shouldBeArray[0] = 32;
        array.addBits((byte)2, (byte)4);
        assertArrayEquals(shouldBeArray, array.getBytes());
    }

    @Test
    public void addingTwoHalfBytesWorks(){
        byte[] shouldBeArray = new byte[1];
        shouldBeArray[0] = 32 + 4;
        array.addBits((byte)2, (byte)4);
        array.addBits((byte)4, (byte)4);
        assertArrayEquals(shouldBeArray, array.getBytes());
    }

    @Test
    public void addingAcrossByteBoundaryWorks(){
        byte[] shouldBeArray = new byte[2];
        shouldBeArray[0] = 1 + 8 + 16;
        shouldBeArray[1] = 64;
        array.addBits((byte)(2 + 4), (byte)6);
        array.addBits((byte)(16 + 4), (byte)6);
        assertArrayEquals(shouldBeArray, array.getBytes());
    }
    
    @Test 
    public void addingToArrayWithInitialDataWorks(){
        byte[] shouldBeArray = new byte[3];
        shouldBeArray[0] = 4;
        shouldBeArray[1] = 0;
        shouldBeArray[2] = 1;
        arrayWithInitialData.addBits((byte)1, (byte)8);
        assertArrayEquals(shouldBeArray, arrayWithInitialData.getBytes());
    }
}
