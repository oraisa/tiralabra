
package oraisa.tiivistys;

import java.util.*;

public class CompressedFile {

    public static CompressedFile fromBytes(byte[] bytes){
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

    private BitPattern[] huffmanCodes;
    public BitPattern[] getHuffmanCodes(){
        return huffmanCodes;
    }

    private byte[] data;
    public byte[] getData(){
        return data;
    }

    private CompressedFile(BitPattern[] huffmanCodes, byte[] data){
        this.huffmanCodes = huffmanCodes;
        this.data = data;
    }

    public byte[] getPlainData(){
        ArrayList<Byte> plainData = new ArrayList<Byte>();
        BitMatcher matcher = new BitMatcher(data);
        while(true){
            for(BitPattern pattern: huffmanCodes){
                if(matcher.matchBitPattern(pattern)){
                    plainData.add(pattern.replacement);
                    break;
                }
            }
        }
        return null;
    }

}
