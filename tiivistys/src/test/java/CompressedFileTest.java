
import oraisa.tiivistys.CompressedFile;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;


public class CompressedFileTest {
    
    public CompressedFileTest() {
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
    public void createdFromBytesWithTrivialEncoding(){
        byte[] bytes = new byte[1000];
        for(int i = 0; i < 256; i++){
            int j = i * 3;
            bytes[j] = (byte)i;
            bytes[j + 1] = 8;
            bytes[j + 2] = (byte)i;
        }
        CompressedFile file = CompressedFile.fromBytes(bytes);
    }
    @Test
    public void createdFromBytesWithFlippedEncoding(){
        byte[] bytes = new byte[1000];
        for(int i = 0; i < 256; i++){
            int j = i * 3;
            bytes[j] = (byte)i;
            bytes[j + 1] = 8;
            bytes[j + 2] = (byte)(255 - i);
        }
        CompressedFile file = CompressedFile.fromBytes(bytes);
    }
}
