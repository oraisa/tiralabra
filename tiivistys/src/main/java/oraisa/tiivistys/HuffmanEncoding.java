
package oraisa.tiivistys;

import java.io.*;
import java.util.*;

/**
 * Stores a Huffman Encoding.
 */
public class HuffmanEncoding {
    
    private static final int CHARACTERS = 256;
    
    public static HuffmanEncoding fromCharacterFrequencies(Map<Byte, Long> characterFrequencies){
        return new HuffmanEncoding(HuffmanCodeCalculator.calculateHuffmanCodes(characterFrequencies));
    }
    
    public static HuffmanEncoding fromDataStream(ByteArrayInputStream stream){
        //TODO: this shouldn't read the entire stream
        byte[] bytes = new byte[stream.available()];
        stream.read(bytes, 0, bytes.length);
        BitInputStream bitStream = new BitInputStream(bytes);
        stream.reset();
        long bitsInDataStream = bitStream.bitsLeft();
        
        HuffmanTreeNode root = readNodes(bitStream);
        
        long bitsRead = bitsInDataStream - bitStream.bitsLeft();
        long bytesRead = bitsRead / 8;
        if(bitsRead % 8 != 0){
            bytesRead += 1;
        }
        stream.skip(bytesRead);
        
        return new HuffmanEncoding(root);
    }
    private static HuffmanTreeNode readNodes(BitInputStream stream){
        int nextBit = stream.readBit();
        if(nextBit == 0){ //Non leaf
            HuffmanTreeNode leftChild = readNodes(stream);
            HuffmanTreeNode rightChild = readNodes(stream);
            return new HuffmanTreeNode(leftChild, rightChild);
        } else { //Leaf
            int isStopCode = stream.readBit();
            if(isStopCode == 0){ //not stop code
                int value = 0;
                for(int i = 7; i >= 0; i--){
                    int bit = stream.readBit();
                    value += bit << i;
                }
                return new HuffmanTreeNode((byte)value, 0);
            } else { // stop code
                for(int i = 0; i < 8; i++){
                    stream.readBit();
                }
                return HuffmanTreeNode.createStopCode();
            }
        }
    }
    
    //The node for byte b is at index b - Byte.MIN_VALUE
    private HuffmanTreeNode[] leafNodes;
    private HuffmanTreeNode stopCodeLeaf;
    private HuffmanTreeNode root;
    
    HuffmanTreeNode getRootNode(){
        return root;
    }
    
    HuffmanEncoding(HuffmanTreeNode root){
        this.root = root;
        leafNodes = new HuffmanTreeNode[CHARACTERS];
        traverseHuffmanTree(root);
    }
    
    private void traverseHuffmanTree(HuffmanTreeNode node){
        if(node.getLeftChild() != null){
            traverseHuffmanTree(node.getLeftChild());
            traverseHuffmanTree(node.getRightChild());
        } else {
            if(node.isStopCode()){
                stopCodeLeaf = node;
            } else {
                leafNodes[node.getValue() - Byte.MIN_VALUE] = node;
            }
        }
    }
    
    public void writeEncodingToOutputStream(ByteArrayOutputStream stream){
        BitOutputStream array = new BitOutputStream();
        appendNodeBitsToArray(root, array);
        byte[] bytes = array.getBytes();
        for(int i = 0; i < bytes.length; i++){
            stream.write(bytes[i]);
        }
    }
    private void appendNodeBitsToArray(HuffmanTreeNode node, BitOutputStream array){
        if(node.getLeftChild() != null){
            array.writeBits(0, 1);
            appendNodeBitsToArray(node.getLeftChild(), array);
            appendNodeBitsToArray(node.getRightChild(), array);
        } else {
            array.writeBits(1, 1);
            if(node.isStopCode()){
                array.writeBits(1, 1);
                array.writeBits(0, 8);
            } else {
                array.writeBits(0, 1);
                array.writeBits(node.getValue(), 8);
            }
        }
    }
    
    public byte[] encodeUnCompressedData(byte[] bytes){
        BitOutputStream array = new BitOutputStream();
        for(byte byt: bytes){
            encodeByte(leafNodes[byt - Byte.MIN_VALUE], array);
        }
        encodeByte(stopCodeLeaf, array);
        return array.getBytes();
    }
    private void encodeByte(HuffmanTreeNode node, BitOutputStream array){
        HuffmanTreeNode parent = node.getParent();
        if(parent != null){
            if(parent.getLeftChild() == node){
                encodeByte(parent, array);
                array.writeBits(1, 1);
            } else {
                encodeByte(parent, array);
                array.writeBits(0, 1);
            }
        }
    }
    
    public byte[] decodeCompressedData(byte[] data){
        BitInputStream bitStream = new BitInputStream(data);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        while(true){
            HuffmanTreeNode currentNode = root;
            while(currentNode.getLeftChild() != null){
                int nextBit = bitStream.readBit();
                if(nextBit == 1){
                    currentNode = currentNode.getLeftChild();
                } else {
                    currentNode = currentNode.getRightChild();
                }
            }
            if(currentNode.isStopCode()){
                return stream.toByteArray();
            } else {
                stream.write(currentNode.getValue());
            }
        }
    }
}

class HuffmanTreeNode implements Comparable<HuffmanTreeNode> {
    private HuffmanTreeNode leftChild = null;
    private HuffmanTreeNode rightChild = null;
    private HuffmanTreeNode parent = null;
    private byte value;
    private long frequency;
    private boolean isStopCode = false;
    
    public HuffmanTreeNode getLeftChild(){
        return leftChild;
    }
    public HuffmanTreeNode getRightChild(){
        return rightChild;
    }
    public byte getValue(){
        return value;
    }
    public long getFrequency(){
        return frequency;
    }
    public boolean isStopCode(){
        return isStopCode;
    }
    public HuffmanTreeNode getParent(){
        return parent;
    }
    
    HuffmanTreeNode(HuffmanTreeNode leftChild, HuffmanTreeNode rightChild){
        this.leftChild = leftChild;
        this.rightChild = rightChild;
        this.frequency = leftChild.getFrequency() + rightChild.getFrequency();
        
        //This is a single-threaded application, so leaking this is not dangerous.
        leftChild.parent = this;
        rightChild.parent = this;
    }
    HuffmanTreeNode(byte value, long frequency){
        this.value = value;
        this.frequency = frequency;
    }
    public static HuffmanTreeNode createStopCode(){
        HuffmanTreeNode node = new HuffmanTreeNode((byte)0, 1);
        node.isStopCode = true;
        return node;
    }

    @Override
    public int compareTo(HuffmanTreeNode o) {
        return new Long(frequency).compareTo(o.getFrequency());
    }
}
