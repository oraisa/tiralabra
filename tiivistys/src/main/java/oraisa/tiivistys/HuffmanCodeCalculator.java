
package oraisa.tiivistys;

import java.util.*;

/**
 * Contains the algorithm to calculate the Huffman encoding for a given 
 * distribution of bytes.
 */
public class HuffmanCodeCalculator {
    private HuffmanCodeCalculator(){}
    
    /**
     * Calculate the Huffman codes for a given distribution of bytes. One of the
     * codes is a BitPattern with the stop code variable set to true. This 
     * pattern should be used to mark the of a file encoded with the returned
     * encoding.
     * @param characterFrequencies A Map with a byte as the key and the frequency
     *                             of that byte as value.
     * @return An array of BitPatterns representing the Huffman codes of each 
     * byte. The BitPattern for byte b is at index b - Byte.MIN_VALUE. The 
     * BitPattern for the stop code is at the end of the array. Bytes that 
     * don't have a frequency in characterFrequencies have a null pattern.
     * @see BitPattern
     */
    public static BitPattern[] calculateHuffmanCodes(Map<Byte, Long> characterFrequencies){
        int characters = 256;
        BitPattern[] huffmanCodes = new BitPattern[characters + 1];
        
        PriorityQueue<HuffmanTreeNode> nodes = new PriorityQueue<HuffmanTreeNode>();
        for(Byte character: characterFrequencies.keySet()){
            nodes.offer(new HuffmanTreeNode(character, characterFrequencies.get(character)));
        }
        nodes.offer(HuffmanTreeNode.createStopCode());
        
        while(nodes.size() > 1){
            HuffmanTreeNode minimumNode = nodes.poll();
            HuffmanTreeNode secondMinimumNode = nodes.poll();
            HuffmanTreeNode newNode = new HuffmanTreeNode(minimumNode, secondMinimumNode);
            nodes.offer(newNode);
        }
        
        traverseHuffmanTree(huffmanCodes, nodes.peek(), new BitPattern(0, 0, 0));
        
        return huffmanCodes;
    }
    
    private static void traverseHuffmanTree(BitPattern[] huffmanCodes, HuffmanTreeNode node, BitPattern currentPattern){
        if(node.getLeftChild() != null && node.getRightChild() != null){
            traverseHuffmanTree(huffmanCodes, node.getLeftChild(), currentPattern.addBit((byte)0));
            traverseHuffmanTree(huffmanCodes, node.getRightChild(), currentPattern.addBit((byte)1));
        } else {
            if(node.isStopCode()){
                huffmanCodes[huffmanCodes.length - 1] = BitPattern.createStopCode(
                        currentPattern.getPattern(), currentPattern.getBitsInPattern());
            } else {
                huffmanCodes[node.getValue() - Byte.MIN_VALUE] = new BitPattern(currentPattern.getPattern(), 
                        currentPattern.getBitsInPattern(), node.getValue());
            }
        }
    }
}

class HuffmanTreeNode implements Comparable<HuffmanTreeNode> {
    private HuffmanTreeNode leftChild = null;
    private HuffmanTreeNode rightChild = null;
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
    
    HuffmanTreeNode(HuffmanTreeNode leftChild, HuffmanTreeNode rightChild){
        this.leftChild = leftChild;
        this.rightChild = rightChild;
        this.frequency = leftChild.getFrequency() + rightChild.getFrequency();
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