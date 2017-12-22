
import oraisa.tiivistys.CompressedFile;
import oraisa.tiivistys.BitPattern;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.*;


public class CompressedFileTest {
    
    byte[] trivialBytes;
    byte[] flippedBytes;
    byte[] trivialEncodingWithData;
    
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
        trivialBytes = new byte[1000];
        flippedBytes = new byte[1000];
        trivialEncodingWithData = new byte[1000]; 
        for(int i = 0; i < 256; i++){
            int j = i * 3;
            trivialBytes[j] = (byte)i;
            trivialBytes[j + 1] = 8;
            trivialBytes[j + 2] = (byte)i;
            trivialEncodingWithData[j] = (byte)i;
            trivialEncodingWithData[j + 1] = 8;
            trivialEncodingWithData[j + 2] = (byte)i;

            flippedBytes[j] = (byte)i;
            flippedBytes[j + 1] = 8;
            flippedBytes[j + 2] = (byte)(255 - i);
        }
        for(int i = 3 * 256; i < trivialEncodingWithData.length; i++){
            trivialEncodingWithData[i] = 6;
        }
    }

    @After
    public void tearDown() {
    }

    @Test
    public void createdFromBytesWithTrivialEncoding(){
        CompressedFile file = CompressedFile.fromBytes(trivialBytes);
    }
    @Test
    public void createdFromBytesWithFlippedEncoding(){
        CompressedFile file = CompressedFile.fromBytes(flippedBytes);
    }

    @Test
    public void correctHuffmanCodesForTrivialEncoding(){
        CompressedFile file = CompressedFile.fromBytes(trivialBytes);
        Map<Byte, BitPattern> huffmanCodes = file.getHuffmanCodes();
        for(Byte key: huffmanCodes.keySet()){
            BitPattern pattern = huffmanCodes.get(key);
            assertEquals((byte)key, pattern.getPattern());
            assertEquals(8, pattern.getBitsInPattern());
        }
    }
    
    @Test
    public void correctHuffmanCodesForTrivialEncodingWithData(){
        CompressedFile file = CompressedFile.fromBytes(trivialEncodingWithData);
        Map<Byte, BitPattern> huffmanCodes = file.getHuffmanCodes();
        for(Byte key: huffmanCodes.keySet()){
            BitPattern pattern = huffmanCodes.get(key);
            assertEquals((byte)key, pattern.getPattern());
            assertEquals(8, pattern.getBitsInPattern());
        }
    }

    @Test
    public void correctHuffmanCodesForFlippedEncoding(){
        CompressedFile file = CompressedFile.fromBytes(flippedBytes);
        Map<Byte, BitPattern> huffmanCodes = file.getHuffmanCodes();
        for(Byte key: huffmanCodes.keySet()){
            BitPattern pattern = huffmanCodes.get(key);
            assertEquals((byte)255 - key, pattern.getPattern());
            assertEquals(8, pattern.getBitsInPattern());
        }
    }

    @Test
    public void correctDataForTrivialEncoding(){
        CompressedFile file = CompressedFile.fromBytes(trivialBytes);
        byte[] data = file.getData();
        assertEquals(data.length, trivialBytes.length - 3 * 256);
        for(int i = 0; i < data.length; i++){
            assertEquals("At index " + i, 0, data[i]);
        }
    }
    @Test
    public void correctDataForTrivialEncodingWithData(){
        CompressedFile file = CompressedFile.fromBytes(trivialEncodingWithData);
        byte[] data = file.getData();
        assertEquals(data.length, trivialBytes.length - 3 * 256);
        for(int i = 0; i < data.length; i++){
            assertEquals("At index " + i, 6, data[i]);
        }
    }

    @Test
    public void correctDataForFlippedEncoding(){
        CompressedFile file = CompressedFile.fromBytes(flippedBytes);
        byte[] data = file.getData();
        assertEquals(data.length, flippedBytes.length - 3 * 256);
        for(int i = 0; i < data.length; i++){
            assertEquals("At index " + i, 0, data[i]);
        }
    }
}
