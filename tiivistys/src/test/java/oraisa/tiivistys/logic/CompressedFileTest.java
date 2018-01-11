package oraisa.tiivistys.logic;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;


public class CompressedFileTest {

    
    byte[] flippedBytes;
    byte[] oneTwoThree;
    byte[] oneTwoThreeEtc;
    
    byte[] exampleData;
    ByteFrequencyCollection exampleFrequencies;

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
        
        //This exaple is from https://web.stanford.edu/class/archive/cs/cs106b/cs106b.1126/handouts/220%20Huffman%20Encoding.pdf
        exampleData = new byte[]{1, 1, 1, 2, 3, 3, 3, 3, 4, 5, 7, 6, 6};
        exampleFrequencies = new ByteFrequencyCollection();
        exampleFrequencies.put((byte)1, 3L);//h
        exampleFrequencies.put((byte)2, 1L);//a
        exampleFrequencies.put((byte)3, 4L);//p
        exampleFrequencies.put((byte)4, 1L);//y
        exampleFrequencies.put((byte)5, 1L);//i
        exampleFrequencies.put((byte)7, 1L);//o
        exampleFrequencies.put((byte)6, 2L);//space
    }

    @After
    public void tearDown() {
    }
    
    @Test
    public void encodingForExampleDataIsOptimal(){   
        HuffmanTreeNode root = CompressedFile.fromUnCompressedBytes(exampleData).
                getHuffmanCodes().getRootNode();
        assertEquals(39, traverseHuffmanTree(root, 0));
    }
    private long traverseHuffmanTree(HuffmanTreeNode node, long currentEncodingLength){
        if(node.getLeftChild() != null){
            return traverseHuffmanTree(node.getLeftChild(), currentEncodingLength + 1) + 
                    traverseHuffmanTree(node.getRightChild(), currentEncodingLength + 1);
        } else {
            if(node.isStopCode()){
                return currentEncodingLength;
            } else {
                return exampleFrequencies.get(node.getValue()) * currentEncodingLength;
            }
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
