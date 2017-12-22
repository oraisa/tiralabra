
package oraisa.tiivistys;

import java.util.*;

public class CompressedFile {

    public static CompressedFile fromBytes(byte[] bytes){
        int characters = 256;
        int headerLength = 3 * characters;

        Map<Byte, BitPattern> huffmanCodes = new HashMap<Byte, BitPattern>();
        for(int i = 0; i < characters; i++){
            int j = i * 3;
            byte character = bytes[j];
            byte bitsInPattern = bytes[j + 1];
            byte pattern = bytes[j + 2];
            BitPattern bitPattern = new BitPattern(pattern, bitsInPattern);
            huffmanCodes.put(character, bitPattern);
        }
        byte[] data = new byte[bytes.length - headerLength];
        for(int i = headerLength + 1; i < bytes.length; i++){
            data[i - headerLength - 1] = bytes[i];
        }
        return new CompressedFile(huffmanCodes, data);
    }

    private Map<Byte, BitPattern> huffmanCodes;
    private byte[] data;
    private CompressedFile(Map<Byte, BitPattern> huffmanCodes, byte[] data){
        this.huffmanCodes = huffmanCodes;
        this.data = data;
    }
}
