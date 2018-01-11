
package oraisa.tiivistys.logic;

import oraisa.tiivistys.measuring.ActiveMeasurer;

/**
 * Contains the algorithm to calculate the Huffman encoding for a given 
 * distribution of bytes.
 */
class HuffmanCodeCalculator {
    private HuffmanCodeCalculator(){}
    
    /**
     * Calculate the Huffman codes for a given distribution of bytes. One of the
     * codes is a HuffmanTreeNode with the stop code variable set to true. The
     * pattern of that node should be used to mark the of a file encoded with 
     * the returned encoding.
     * @param characterFrequencies A Map with a byte as the key and the frequency
     *                             of that byte as value.
     * @return The root HuffmanTreeNode of the Huffman encoding tree for the 
     * given frequencies.
     * @see HuffmanTreeNode
     */
    static HuffmanTreeNode calculateHuffmanCodes(ByteFrequencyCollection characterFrequencies){
        ActiveMeasurer.getMeasurer().startHuffmanEncodingCalculation();
        
        HuffmanTreeNodeHeap nodes = new HuffmanTreeNodeHeap(257);
        for(int i = 0; i < characterFrequencies.size(); i++){
            byte character = (byte)i;
            long frequency = characterFrequencies.get(character);
            if(frequency != 0){
                nodes.insert(new HuffmanTreeNode(character, characterFrequencies.get(character)));
            }
        }
        nodes.insert(HuffmanTreeNode.createStopCode());
        
        while(nodes.size() > 1){
            HuffmanTreeNode minimumNode = nodes.poll();
            HuffmanTreeNode secondMinimumNode = nodes.poll();
            HuffmanTreeNode newNode = new HuffmanTreeNode(minimumNode, secondMinimumNode);
            nodes.insert(newNode);
        }
        
        ActiveMeasurer.getMeasurer().endHuffmanEncodingCalculation();
        return nodes.peek();
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
        while(i > 0 && heapArray[parentIndex(i)].compareTo(node) > 0){
            heapArray[i] = heapArray[parentIndex(i)];
            i = parentIndex(i);
        }
        heapArray[i] = node;
    }
    
    public int size(){
        return heapSize;
    }
}