
package oraisa.tiivistys;

import java.util.Map;

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
        
        //PriorityQueue<HuffmanTreeNode> nodes = new PriorityQueue<HuffmanTreeNode>();
        HuffmanTreeNodeHeap nodes = new HuffmanTreeNodeHeap(huffmanCodes.length);
        for(Byte character: characterFrequencies.keySet()){
            nodes.insert(new HuffmanTreeNode(character, characterFrequencies.get(character)));
        }
        nodes.insert(HuffmanTreeNode.createStopCode());
        
        while(nodes.size() > 1){
            HuffmanTreeNode minimumNode = nodes.poll();
            HuffmanTreeNode secondMinimumNode = nodes.poll();
            HuffmanTreeNode newNode = new HuffmanTreeNode(minimumNode, secondMinimumNode);
            nodes.insert(newNode);
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

class HuffmanTreeNodeHeap {
    
    private HuffmanTreeNode[] heapArray;
    private int heapSize;
    
    private int parentIndex(int i){
        return i / 2;
    }
    private int leftChildIndex(int i){
        return 2 * i;
    }
    private int rightChildIndex(int i){
        return 2 * i + 1;
    }
    
    public HuffmanTreeNodeHeap(int maxSize){
        heapArray = new HuffmanTreeNode[maxSize];
        heapSize = 0;
    }
    
    private void heapify(int index){
        int rightIndex = rightChildIndex(index);
        int leftIndex = leftChildIndex(index);
        if(rightIndex < heapSize){
            int minimumChildIndex;
            if(heapArray[leftIndex].compareTo(heapArray[rightIndex]) < 0){
                minimumChildIndex = leftIndex;
            } else {
                minimumChildIndex = rightIndex;
            }
            if(heapArray[index].compareTo(heapArray[minimumChildIndex]) > 0){
                swap(index, minimumChildIndex);
                heapify(minimumChildIndex);
            }
        } else if(leftIndex == heapSize - 1 && heapArray[index].compareTo(heapArray[leftIndex]) > 0){
            swap(index, leftIndex);
        }
    }
    
    private void swap(int i, int j){
        HuffmanTreeNode temp = heapArray[i];
        heapArray[i] = heapArray[j];
        heapArray[j] = temp;
    }
    
    public HuffmanTreeNode peek(){
        return heapArray[0];
    }
    
    public HuffmanTreeNode poll(){
        HuffmanTreeNode minNode = heapArray[0];
        heapArray[0] = heapArray[heapSize - 1];
        heapArray[heapSize - 1] = null;
        heapSize -= 1;
        heapify(0);
        return minNode;
    }
    
    public void insert(HuffmanTreeNode node){
        heapSize += 1;
        int i = heapSize - 1;
        while(i > 1 && heapArray[parentIndex(i)].compareTo(node) > 0){
            heapArray[i] = heapArray[parentIndex(i)];
            i = parentIndex(i);
        }
        heapArray[i] = node;
    }
    
    public int size(){
        return heapSize;
    }
}