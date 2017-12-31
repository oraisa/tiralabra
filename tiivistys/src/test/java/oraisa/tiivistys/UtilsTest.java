package oraisa.tiivistys;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class UtilsTest {
    
    public UtilsTest() {
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
    public void byteRightShiftWithPositiveNumber(){
        assertEquals((byte)8, Utils.byteRightShift((byte)64, 3));
    }

    @Test
    public void byteRightShiftWithNegativeNumber(){
        assertEquals((byte)(1 + 32), Utils.byteRightShift((byte)(-128 + 4), 2));
    }

    @Test
    public void shortRightShiftWithPositiveNumber(){
        assertEquals((short)(256 + 2), Utils.shortRightShift((short)(1024 + 8), 2));
    }

    @Test
    public void shortRightShiftWithNegativeNumber(){
        assertEquals((short)(4096 + 8 + 2), Utils.shortRightShift((short)(-32768 + 64 + 16), 3));
    }
}
