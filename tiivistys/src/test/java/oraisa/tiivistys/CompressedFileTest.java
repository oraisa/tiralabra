package oraisa.tiivistys;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.*;


public class CompressedFileTest {

    
    byte[] flippedBytes;
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
        
        flippedBytes = new byte[2000];
        for(int i = 0; i < 256; i++){
            int j = i * 4;
            flippedBytes[j] = (byte)i;
            flippedBytes[j + 1] = 0;
            flippedBytes[j + 2] = (byte)(255 - i);
            flippedBytes[j + 3] = 8;
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
