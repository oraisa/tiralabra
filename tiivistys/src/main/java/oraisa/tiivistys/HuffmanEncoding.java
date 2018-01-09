
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
        BitPattern[] huffmanCodes = new BitPattern[CHARACTERS + 1];
        for(int i = 0; i < CHARACTERS; i++){
            byte[] fourBytes = new byte[4];
            stream.read(fourBytes, 0, fourBytes.length);
            BitPattern bitPattern = BitPattern.fromBytes(fourBytes);
            huffmanCodes[i] = bitPattern;
        }
        byte[] threeBytes = new byte[3];
        stream.read(threeBytes, 0, threeBytes.length);
        huffmanCodes[huffmanCodes.length - 1] = BitPattern.fromBytes(threeBytes);
        
        return new HuffmanEncoding(huffmanCodes);
    }
    
    BitPattern[] huffmanCodes;
    public BitPattern[] getCodes(){
        return huffmanCodes;
    }
    
    private HuffmanEncoding(BitPattern[] patterns){
        this.huffmanCodes = patterns;
    }
    
    public void writeEncodingToOutputStream(ByteArrayOutputStream stream){
        for(int i = 0; i < huffmanCodes.length; i++){
            if(huffmanCodes[i] != null){
                byte[] patternBytes = huffmanCodes[i].toBytes();
                stream.write(patternBytes, 0, patternBytes.length);
            } else {
                //Write four zeros to get the correct header size
                stream.write(0);
                stream.write(0);
                stream.write(0);
                stream.write(0);
            }
        }
    }
    
    public byte[] encodeUnCompressedData(byte[] bytes){
        BitArray array = new BitArray();
        for(byte byt: bytes){
            BitPattern patternForByte = huffmanCodes[byt - Byte.MIN_VALUE];
            array.addBitPattern(patternForByte);
        }
        array.addBitPattern(huffmanCodes[huffmanCodes.length - 1]);
        return array.getBytes();
    }
    
    public byte[] decodeCompressedData(byte[] data){
        BitMatcher matcher = new BitMatcher(data);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        while(true){
            boolean matchedPattern = false;
            for(BitPattern pattern: huffmanCodes){
                if(pattern != null && matcher.matchBitPattern(pattern)){
                    if(pattern.isStopCode()){
                        return stream.toByteArray();
                    } else {
                        stream.write(pattern.getReplacement());
                        matchedPattern = true;
                        break;
                    }
                }
            }
            if(!matchedPattern){
                return stream.toByteArray();
            }
        }
    }
}
