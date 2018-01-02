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
        
        trivialBytes = new byte[2001];
        flippedBytes = new byte[2000];
        trivialEncodingWithData = new byte[2001];
        for(int i = 0; i < 256; i++){
            int j = i * 4;
            
            trivialBytes[j] = (byte)i;
            trivialBytes[j + 1] = 0;
            trivialBytes[j + 2] = (byte)i;
            trivialBytes[j + 3] = 16;
            trivialEncodingWithData[j] = (byte)i;
            trivialEncodingWithData[j + 1] = 0;
            trivialEncodingWithData[j + 2] = (byte)i;
            trivialEncodingWithData[j + 3] = 16;
            

            flippedBytes[j] = (byte)i;
            flippedBytes[j + 1] = 0;
            flippedBytes[j + 2] = (byte)(255 - i);
            flippedBytes[j + 3] = 8;
        }
        
        trivialBytes[256 * 4] = 1;
        trivialBytes[256 * 4 + 1] = 1;
        trivialBytes[256 * 4 + 2] = 16;
        trivialEncodingWithData[256 * 4] = 1;
        trivialEncodingWithData[256 * 4 + 1] = 1;
        trivialEncodingWithData[256 * 4 + 2] = 16;
        for(int i = 4 * 256 + 3; i < trivialEncodingWithData.length; i++){
            if(i % 2 != 0){
                trivialEncodingWithData[i] = 0;
            } else {
                trivialEncodingWithData[i] = 6;
            }
        }
        trivialBytes[trivialBytes.length - 2] = 1;
        trivialBytes[trivialBytes.length - 1] = 1;
        trivialEncodingWithData[trivialBytes.length - 2] = 1;
        trivialEncodingWithData[trivialBytes.length - 1] = 1;
        
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
        for(int i = 0; i < huffmanCodes.length - 1; i++){
            BitPattern pattern = huffmanCodes[i];
            assertEquals(pattern.getReplacement(), (byte)pattern.getPattern());
            assertEquals(16, pattern.getBitsInPattern());
        }
    }
    
    @Test
    public void correctStopCodeWithTrivialEncoding(){
        CompressedFile file = CompressedFile.fromCompressedBytes(trivialBytes);
        BitPattern[] huffmanCodes = file.getHuffmanCodes();
        BitPattern stopCode = huffmanCodes[huffmanCodes.length - 1];
        assertEquals("Pattern: ", (1 << 8) + 1, stopCode.getPattern());
        assertEquals("BitsInPattern: ", 16, stopCode.getBitsInPattern());
    }

    @Test
    public void correctDataForTrivialEncoding(){
        CompressedFile file = CompressedFile.fromCompressedBytes(trivialBytes);
        byte[] data = file.getCompressedData();
        assertEquals(data.length, trivialBytes.length - 4 * 256 - 3);
        //Ignore the last two bytes because they contain the stop code.
        for(int i = 0; i < data.length - 2; i++){
            assertEquals("At index " + i, 0, data[i]);
        }
    }
    @Test
    public void correctDataForTrivialEncodingWithData(){
        CompressedFile file = CompressedFile.fromCompressedBytes(trivialEncodingWithData);
        byte[] data = file.getCompressedData();
        assertEquals(data.length, trivialEncodingWithData.length - 4 * 256 - 3);
        //Ignore the last two bytes because they contain the stop code.
        for(int i = 0; i < data.length - 2; i++){
            if(i % 2 == 0){
                assertEquals("At index " + i, 0, data[i]);
            } else {
                assertEquals("At index " + i, 6, data[i]);
            }
        }
    }

    @Test
    public void correctPlainDataWithTrivialEncoding(){
        CompressedFile file = CompressedFile.fromCompressedBytes(trivialEncodingWithData);
        byte[] plainData = file.getUnCompressedData();
        assertEquals((trivialEncodingWithData.length - 4 * 256 - 3) / 2 - 1, plainData.length);
        for(int i = 0; i < plainData.length; i++){
            assertEquals("At index " + i, 6, plainData[i]);
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
    public void fileCreatedFromCompressedDataReturnsSameCompressedDataWithTrivialBytes(){
        CompressedFile file = CompressedFile.fromCompressedBytes(trivialBytes);
        assertArrayEquals(trivialBytes, file.getCompressedDataWithHeader());
    }
    
    @Test
    public void fileCreatedFromCompressedDataReturnsSameCompressedDataWithTrivialBytesWithData(){
        CompressedFile file = CompressedFile.fromCompressedBytes(trivialEncodingWithData);
        assertArrayEquals(trivialEncodingWithData, file.getCompressedDataWithHeader());
    }
    
    private void testCompressedDataWithHeaderPreservesCompressedData(byte[] data){
        CompressedFile file = CompressedFile.fromUnCompressedBytes(data);
        byte[] compressedData = file.getCompressedData();
        byte[] compressedDataWithHeader = file.getCompressedDataWithHeader();
        byte[] compressedDataWithNoHeader = new byte[compressedDataWithHeader.length - 4 * 256 - 3];
        System.arraycopy(compressedDataWithHeader, 4 * 256 + 3, compressedDataWithNoHeader, 0, 
                compressedDataWithNoHeader.length);
        assertArrayEquals(compressedData, compressedDataWithNoHeader);
    }
    
    private void testCompressedDataWithHeaderHasRightLength(byte[] data){
        CompressedFile file = CompressedFile.fromUnCompressedBytes(data);
        byte[] compressedData = file.getCompressedData();
        byte[] compressedDataWithHeader = file.getCompressedDataWithHeader();
        assertEquals(compressedData.length + 256 * 4 + 3, compressedDataWithHeader.length);
    }
    
    @Test
    public void smallDataCompressedToBytesHasRightLength(){
        testCompressedDataWithHeaderHasRightLength(oneTwoThree);
    }
    
    @Test
    public void getCompressedDataWithHeaderPreservesCompressedDataWithSmallData(){
        testCompressedDataWithHeaderPreservesCompressedData(oneTwoThree);
    }
    
    @Test
    public void largeDataCompressedToBytesHasRightLength(){
        testCompressedDataWithHeaderHasRightLength(oneTwoThreeEtc);
    }
    
    @Test
    public void getCompressedDataWithHeaderPreservesCompressedDataWithLargeData(){
        testCompressedDataWithHeaderPreservesCompressedData(oneTwoThreeEtc);
    }
    
    @Test
    public void otherDataCompressedToBytesHasRightLength(){
        testCompressedDataWithHeaderHasRightLength(flippedBytes);
    }
    
    @Test
    public void getCompressedDataWithHeaderPreservesCompressedDataWithOtherData(){
        testCompressedDataWithHeaderPreservesCompressedData(flippedBytes);
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
    
    @Test
    public void compressingAndUnCompressingSmallDataToBytesReturnTheSameData(){
        CompressedFile compressed = CompressedFile.fromUnCompressedBytes(oneTwoThree);
        byte[] compressedData = compressed.getCompressedDataWithHeader();
        CompressedFile fromCompressed = CompressedFile.fromCompressedBytes(compressedData);
        assertArrayEquals(oneTwoThree, fromCompressed.getUnCompressedData());
    }
    
    @Test
    public void compressingAndUnCompressingLargeDataToBytesReturnsTheSameData(){
        CompressedFile compressed = CompressedFile.fromUnCompressedBytes(oneTwoThreeEtc);
        byte[] compressedData = compressed.getCompressedDataWithHeader();
        CompressedFile fromCompressed = CompressedFile.fromCompressedBytes(compressedData);
        assertArrayEquals(oneTwoThreeEtc, fromCompressed.getUnCompressedData());
    }
    
    @Test
    public void compressingAndUnCompressingOtherDataToBytesReturnsTheSameData(){
        CompressedFile compressed = CompressedFile.fromUnCompressedBytes(flippedBytes);
        byte[] compressedData = compressed.getCompressedDataWithHeader();
        CompressedFile fromCompressed = CompressedFile.fromCompressedBytes(compressedData);
        assertArrayEquals(flippedBytes, fromCompressed.getUnCompressedData());
    }
    
}
