/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oraisa.tiivistys;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class BitInputStreamTest {
    
    public BitInputStreamTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void correctBitReadFromBitInputStreamWithOneByte(){
        BitInputStream stream = new BitInputStream(new byte[]{2 + 64 + 8});
        assertEquals(0, stream.readBit());
        assertEquals(1, stream.readBit());
        assertEquals(0, stream.readBit());
        assertEquals(0, stream.readBit());
        assertEquals(1, stream.readBit());
        assertEquals(0, stream.readBit());
        assertEquals(1, stream.readBit());
        assertEquals(0, stream.readBit());
    }

    @Test
    public void correctBitReadFromBitInputStreamWithTwoBytes(){
        BitInputStream stream = new BitInputStream(new byte[]{2 + 64 + 8, (byte)(4 + 2 + 32 + 128)});
        assertEquals(0, stream.readBit());
        assertEquals(1, stream.readBit());
        assertEquals(0, stream.readBit());
        assertEquals(0, stream.readBit());
        assertEquals(1, stream.readBit());
        assertEquals(0, stream.readBit());
        assertEquals(1, stream.readBit());
        assertEquals(0, stream.readBit());
        
        assertEquals(1, stream.readBit());
        assertEquals(0, stream.readBit());
        assertEquals(1, stream.readBit());
        assertEquals(0, stream.readBit());
        assertEquals(0, stream.readBit());
        assertEquals(1, stream.readBit());
        assertEquals(1, stream.readBit());
        assertEquals(0, stream.readBit());
    }
}
