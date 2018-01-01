
package oraisa.tiivistys;

import java.util.*;

/**
 * Represents compressed data. Can be used to uncompress the data.
 */
public class CompressedFile {

    /**
     * Create a representation of compressed data.
     * @param bytes An array of bytes with the compressed data.
     * @return The object representing the compressed data.
     */
    public static CompressedFile fromCompressedBytes(byte[] bytes){
        int characters = 256;
        int headerLength = 3 * characters;

        BitPattern[] huffmanCodes = new BitPattern[characters];
        for(int i = 0; i < characters; i++){
            int j = i * 3;
            byte character = bytes[j];
            byte bitsInPattern = bytes[j + 1];
            byte pattern = bytes[j + 2];
            BitPattern bitPattern = new BitPattern(pattern, bitsInPattern, character);
            huffmanCodes[i] = bitPattern;
        }
        byte[] data = new byte[bytes.length - headerLength];
        for(int i = headerLength; i < bytes.length; i++){
            data[i - headerLength] = bytes[i];
        }
        return new CompressedFile(huffmanCodes, data);
    }
    
    /**
     * Compress data.
     * @param bytes An array of bytes representing the data to be compressed.
     * @return An object representing the compressed data.
     */
    public static CompressedFile fromUnCompressedBytes(byte[] bytes){
        Map<Byte, Long> characterFrequencies = countCharacters(bytes);
        BitPattern[] huffmanCodes = HuffmanCodeCalculator.calculateHuffmanCodes(characterFrequencies);
        BitArray array = new BitArray();
        for(byte byt: bytes){
            BitPattern patternForByte = huffmanCodes[byt - Byte.MIN_VALUE];
            array.addBitPattern(patternForByte);
        }
        array.addBitPattern(huffmanCodes[huffmanCodes.length - 1]);
        
        CompressedFile newFile = new CompressedFile(huffmanCodes, array.getBytes());
        return newFile;
    }
    
    private static Map<Byte, Long> countCharacters(byte[] bytes){
        Map<Byte, Long> characterFrequencies = new HashMap<Byte, Long>();
        for(int i = 0; i < bytes.length; i++){
            byte byt = bytes[i];
            if(characterFrequencies.containsKey(byt)){
                characterFrequencies.put(byt, characterFrequencies.get(byt) + 1);
            } else {
                characterFrequencies.put(byt, 1L);
            }
        }
        return characterFrequencies;
    }

    private BitPattern[] huffmanCodes;
    /**
     * Returns the Huffman codes used to encode the compressed data.
     * @return An array of BitPatterns where each pattern has the encoding of
     * a single byte.
     */
    public BitPattern[] getHuffmanCodes(){
        return huffmanCodes;
    }

    private byte[] data;
    /**
     * Gets the compressed data without the header.
     * @return An array of bytes with the compressed data.
     */
    public byte[] getCompressedData(){
        return data;
    }

    private CompressedFile(BitPattern[] huffmanCodes, byte[] data){
        this.huffmanCodes = huffmanCodes;
        this.data = data;
    }
    

    /**
     * Decodes the compressed data and return the uncompressed data.
     * @return An array of bytes with the uncompressed data.
     */
    public byte[] getUnCompressedData(){
        ArrayList<Byte> plainData = new ArrayList<Byte>();
        BitMatcher matcher = new BitMatcher(data);
        iterateData(plainData, matcher);
        
        byte[] plainDataArray = new byte[plainData.size()];
        for(int i = 0; i < plainDataArray.length; i++){
            plainDataArray[i] = plainData.get(i);
        }
        return plainDataArray;
    }
    
    private void iterateData(ArrayList<Byte> plainData, BitMatcher matcher){
        while(true){
            boolean matchedPattern = false;
            for(BitPattern pattern: huffmanCodes){
                if(pattern != null && matcher.matchBitPattern(pattern)){
                    if(pattern.isStopCode()){
                        return;
                    } else {
                        plainData.add(pattern.getReplacement());
                        matchedPattern = true;
                        break;
                    }
                }
            }
            if(!matchedPattern){
                return;
            }
        }
    }
}
