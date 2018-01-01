package oraisa.tiivistys;

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
    
    byte[] oneTwoThree;
    byte[] oneTwoThreeEtc;

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
        
        oneTwoThree = new byte[]{1, 2, 3};
        oneTwoThreeEtc = new byte[1000];
        for(int i = 0; i < oneTwoThreeEtc.length; i++){
            oneTwoThreeEtc[i] = (byte)i;
        }
    }

    @After
    public void tearDown() {
    }

    @Test
    public void createdFromBytesWithTrivialEncoding(){
        CompressedFile file = CompressedFile.fromCompressedBytes(trivialBytes);
    }
    @Test
    public void createdFromBytesWithFlippedEncoding(){
        CompressedFile file = CompressedFile.fromCompressedBytes(flippedBytes);
    }

    @Test
    public void correctHuffmanCodesForTrivialEncoding(){
        testHuffmanCodesWithTrivialEncoding(trivialBytes);
    }

    @Test
    public void correctHuffmanCodesForTrivialEncodingWithData(){
        testHuffmanCodesWithTrivialEncoding(trivialEncodingWithData);
    }

    private void testHuffmanCodesWithTrivialEncoding(byte[] bytes){
        CompressedFile file = CompressedFile.fromCompressedBytes(bytes);
        BitPattern[] huffmanCodes = file.getHuffmanCodes();
        for(BitPattern pattern: huffmanCodes){
            assertEquals(pattern.getReplacement(), pattern.getPattern());
            assertEquals(8, pattern.getBitsInPattern());
        }
    }

    @Test
    public void correctHuffmanCodesForFlippedEncoding(){
        CompressedFile file = CompressedFile.fromCompressedBytes(flippedBytes);
        BitPattern[] huffmanCodes = file.getHuffmanCodes();
        for(BitPattern pattern: huffmanCodes){
            assertEquals((byte)255 - pattern.getReplacement(), pattern.getPattern());
            assertEquals(8, pattern.getBitsInPattern());
        }
    }

    @Test
    public void correctDataForTrivialEncoding(){
        CompressedFile file = CompressedFile.fromCompressedBytes(trivialBytes);
        byte[] data = file.getCompressedData();
        assertEquals(data.length, trivialBytes.length - 3 * 256);
        for(int i = 0; i < data.length; i++){
            assertEquals("At index " + i, 0, data[i]);
        }
    }
    @Test
    public void correctDataForTrivialEncodingWithData(){
        CompressedFile file = CompressedFile.fromCompressedBytes(trivialEncodingWithData);
        byte[] data = file.getCompressedData();
        assertEquals(data.length, trivialEncodingWithData.length - 3 * 256);
        for(int i = 0; i < data.length; i++){
            assertEquals("At index " + i, 6, data[i]);
        }
    }

    @Test
    public void correctDataForFlippedEncoding(){
        CompressedFile file = CompressedFile.fromCompressedBytes(flippedBytes);
        byte[] data = file.getCompressedData();
        assertEquals(data.length, flippedBytes.length - 3 * 256);
        for(int i = 0; i < data.length; i++){
            assertEquals("At index " + i, 0, data[i]);
        }
    }

    @Test
    public void correctPlainDataWithTrivialEncoding(){
        CompressedFile file = CompressedFile.fromCompressedBytes(trivialEncodingWithData);
        byte[] plainData = file.getUnCompressedData();
        assertEquals(plainData.length, trivialEncodingWithData.length - 3 * 256);
        for(int i = 0; i < plainData.length; i++){
            assertEquals("At index " + i, 6, plainData[i]);
        }
    }

    @Test
    public void correctPlainDataWithFlippedEncoding(){
        CompressedFile file = CompressedFile.fromCompressedBytes(flippedBytes);
        byte[] plainData = file.getUnCompressedData();
        assertEquals(plainData.length, trivialEncodingWithData.length - 3 * 256);
        for(int i = 0; i < plainData.length; i++){
            assertEquals("At index " + i, (byte)255, plainData[i]);
        }
    }
    
    @Test
    public void compressedSmallDataMatchesWithBitPatternsOfOriginalData(){
        CompressedFile file = CompressedFile.fromUnCompressedBytes(oneTwoThree);
        BitMatcher matcher = new BitMatcher(file.getCompressedData());
        assertTrue(matcher.matchBitPattern(file.getHuffmanCodes()[1 - Byte.MIN_VALUE]));
        assertTrue(matcher.matchBitPattern(file.getHuffmanCodes()[2 - Byte.MIN_VALUE]));
        assertTrue(matcher.matchBitPattern(file.getHuffmanCodes()[3 - Byte.MIN_VALUE]));
    }
    
    @Test
    public void compressedLargeDataMatchesWithBitPatternsOfOriginalData(){
        CompressedFile file = CompressedFile.fromUnCompressedBytes(oneTwoThreeEtc);
        BitMatcher matcher = new BitMatcher(file.getCompressedData());
        for(int i = 0; i < oneTwoThreeEtc.length; i++){
            assertTrue(matcher.matchBitPattern(file.getHuffmanCodes()[oneTwoThreeEtc[i] - Byte.MIN_VALUE]));
        }
    }
    
    @Test
    public void compressedOtherDataMatchesWithBitPatternsOfOriginalData(){
        CompressedFile file = CompressedFile.fromUnCompressedBytes(flippedBytes);
        BitMatcher matcher = new BitMatcher(file.getCompressedData());
        for(int i = 0; i < flippedBytes.length; i++){
            BitPattern pattern = (file.getHuffmanCodes()[flippedBytes[i] - Byte.MIN_VALUE]);
            assertTrue("Index:" + i + ", pattern: " + pattern.toString(), matcher.matchBitPattern(pattern));
        }
    }
    
    @Test
    public void compressingAndUnCompressingSmallDataReturnsTheSameData(){
        CompressedFile file = CompressedFile.fromUnCompressedBytes(oneTwoThree);
        assertArrayEquals(oneTwoThree, file.getUnCompressedData());
    }
    
    @Test
    public void compressingAndUnCompressingLargeDataReturnsTheSameData(){
        CompressedFile file = CompressedFile.fromUnCompressedBytes(oneTwoThreeEtc);
        assertArrayEquals(oneTwoThreeEtc, file.getUnCompressedData());
    }
    
    @Test
    public void compressingAndUnCompressingOtherDataReturnsTheSameData(){
        CompressedFile file = CompressedFile.fromUnCompressedBytes(flippedBytes);
        assertArrayEquals(flippedBytes, file.getUnCompressedData());
    }
    
}
