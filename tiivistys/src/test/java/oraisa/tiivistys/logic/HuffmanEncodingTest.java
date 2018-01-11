
package oraisa.tiivistys.logic;

import java.io.*;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class HuffmanEncodingTest {
    
    public HuffmanEncodingTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    HuffmanEncoding easyEncoding;
    byte[] easyEncodingInBytes;
    
    ByteFrequencyCollection exampleFrequencies;
    HuffmanEncoding exampleEncoding;
    @Before
    public void setUp() {
        HuffmanTreeNode leaf1 = new HuffmanTreeNode((byte)1, 0);
        HuffmanTreeNode leaf2 = new HuffmanTreeNode((byte)2, 0);
        HuffmanTreeNode leaf3 = new HuffmanTreeNode((byte)3, 0);
        HuffmanTreeNode leaf4 = HuffmanTreeNode.createStopCode();
        HuffmanTreeNode branch1 = new HuffmanTreeNode(leaf1, leaf2);
        HuffmanTreeNode branch2 = new HuffmanTreeNode(leaf3, leaf4);
        HuffmanTreeNode root = new HuffmanTreeNode(branch1, branch2);
        easyEncoding = new HuffmanEncoding(root);
        BitOutputStream array = new BitOutputStream();
        array.writeBits((short)0, 1);//root
        array.writeBits((short)0, 1);//branch 1
        array.writeBits((short)2, 2);//leaf, not stop
        array.writeBits((short)1, 8);//1
        array.writeBits((short)2, 2);//leaf, not stop
        array.writeBits((short)2, 8);//2
        array.writeBits((short)0, 1);//branch 2
        array.writeBits((short)2, 2);//leaf, not stop
        array.writeBits((short)3, 8);//3
        array.writeBits((short)3, 2);//leaf, stop
        array.writeBits((short)0, 8);//0
        easyEncodingInBytes = array.getBytes();
        
        //This exaple is from https://web.stanford.edu/class/archive/cs/cs106b/cs106b.1126/handouts/220%20Huffman%20Encoding.pdf
        exampleFrequencies = new ByteFrequencyCollection();
        exampleFrequencies.put((byte)1, 3L);//h
        exampleFrequencies.put((byte)2, 1L);//a
        exampleFrequencies.put((byte)3, 4L);//p
        exampleFrequencies.put((byte)4, 1L);//y
        exampleFrequencies.put((byte)5, 1L);//i
        exampleFrequencies.put((byte)7, 1L);//o
        exampleFrequencies.put((byte)6, 2L);//space
        exampleEncoding = HuffmanEncoding.fromCharacterFrequencies(exampleFrequencies);
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void shortDataEncodedCorrectlyWithEasyEncoding(){
        byte[] plainData = new byte[]{2, 3, 1, 3};
        BitOutputStream array = new BitOutputStream();
        array.writeBits((short)2, 2);//2
        array.writeBits((short)1, 2);//3
        array.writeBits((short)3, 2);//1
        array.writeBits((short)1, 2);//3
        array.writeBits((short)0, 2);//stop
        byte[] shouldBeEncodedData = array.getBytes();
        byte[] encodedData = easyEncoding.encodeUnCompressedData(plainData);
        assertArrayEquals(shouldBeEncodedData, encodedData);
    }
    
    @Test
    public void shortDataDecodedCorrectlyWithEasyEncoding(){
        byte[] shouldBeDecodedData = new byte[]{3, 3, 1, 2};
        BitOutputStream array = new BitOutputStream();
        array.writeBits((short)1, 2);//3
        array.writeBits((short)1, 2);//3
        array.writeBits((short)3, 2);//1
        array.writeBits((short)2, 2);//2
        array.writeBits((short)0, 2);//stop
        byte[] encodedData = array.getBytes();
        byte[] decodedData = easyEncoding.decodeCompressedData(encodedData);
        assertArrayEquals(shouldBeDecodedData, decodedData);
    }
    
    @Test
    public void dataIsEncodedAndDecodedCorrectlyWithExampleEncoding(){
        byte[] plainData = new byte[1000];
        for(int i = 0; i < plainData.length; i++){
            plainData[i] = 7;
            if(i % 3 == 0){
                plainData[i] = 1;
            }
            if(i % 4 == 0){
                plainData[i] = 2;
            }
            if(i % 7 == 3){
                plainData[i] = 3;
            }
            if(i % 12 == 0){
                plainData[i] = 4;
            }
            if(i % 33 == 17){
                plainData[i] = 5;
            }
            if(i % 277 == 1){
                plainData[i] = 6;
            }
        }
        byte[] encodedData = exampleEncoding.encodeUnCompressedData(plainData);
        byte[] decodedData = exampleEncoding.decodeCompressedData(encodedData);
        assertArrayEquals(plainData, decodedData);
    }
    
    @Test
    public void exampleEncodingIsOptimal(){
        HuffmanTreeNode root = exampleEncoding.getRootNode();
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
    public void easyEncodingIsCorrectlyWrittenToBytes(){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        easyEncoding.writeEncodingToOutputStream(stream);
        assertArrayEquals(easyEncodingInBytes, stream.toByteArray());
    }
    
    @Test
    public void easyEncodingIsCorrectlyReadFromBytes(){
        ByteArrayInputStream stream = new ByteArrayInputStream(easyEncodingInBytes);
        HuffmanEncoding encoding = HuffmanEncoding.fromDataStream(stream);
        HuffmanTreeNode root = encoding.getRootNode();
        assertEquals(1, root.getLeftChild().getLeftChild().getValue());
        assertEquals(2, root.getLeftChild().getRightChild().getValue());
        assertEquals(3, root.getRightChild().getLeftChild().getValue());
        assertTrue(root.getRightChild().getRightChild().isStopCode());
        assertEquals(0, stream.available());
    }
}
