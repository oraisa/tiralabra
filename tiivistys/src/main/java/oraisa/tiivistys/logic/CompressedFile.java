
package oraisa.tiivistys.logic;

import java.io.*;
import oraisa.tiivistys.measuring.*;

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
        ByteArrayInputStream stream = new ByteArrayInputStream(bytes);

        HuffmanEncoding huffmanCodes = HuffmanEncoding.fromDataStream(stream);
        
        byte[] data = new byte[stream.available()];
        stream.read(data, 0, data.length);
        return new CompressedFile(huffmanCodes, data);
    }
    
    /**
     * Compress data.
     * @param bytes An array of bytes representing the data to be compressed.
     * @return An object representing the compressed data.
     */
    public static CompressedFile fromUnCompressedBytes(byte[] bytes){
        ByteFrequencyCollection characterFrequencies = countCharacters(bytes);
        HuffmanEncoding huffmanCodes = HuffmanEncoding.fromCharacterFrequencies(characterFrequencies);
        byte[] encodedData = huffmanCodes.encodeUnCompressedData(bytes);
        CompressedFile newFile = new CompressedFile(huffmanCodes, encodedData);
        return newFile;
    }
    
    private static ByteFrequencyCollection countCharacters(byte[] bytes){
        ActiveMeasurer.getMeasurer().startCountingCharacters();
        ByteFrequencyCollection characterFrequencies = new ByteFrequencyCollection();
        int disticntCharacters = 0;
        for(int i = 0; i < bytes.length; i++){
            byte byt = bytes[i];
            characterFrequencies.incrementByte(byt);
            disticntCharacters++;
        }
        ActiveMeasurer.getMeasurer().endCountingCharacters();
        ActiveMeasurer.getMeasurer().reportDistinctCharacterCount(disticntCharacters);
        return characterFrequencies;
    }

    private HuffmanEncoding huffmanCodes;
    /**
     * Returns the Huffman codes used to encode the compressed data.
     * @return An object representing the used Huffman encoding.
     */
    public HuffmanEncoding getHuffmanCodes(){
        return huffmanCodes;
    }

    private byte[] data;

    private CompressedFile(HuffmanEncoding huffmanCodes, byte[] data){
        this.huffmanCodes = huffmanCodes;
        this.data = data;
    }
    
    /**
     * Get the compressed data with a header that specifies the encoding.
     * @return An array of bytes containing the header and the compressed data.
     */
    public byte[] getCompressedDataWithHeader(){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        huffmanCodes.writeEncodingToOutputStream(stream);
        
        for(int i = 0; i < data.length; i++){
            stream.write(data[i]);
        }
        return stream.toByteArray();
    }

    /**
     * Decodes the compressed data and return the uncompressed data.
     * @return An array of bytes with the uncompressed data.
     */
    public byte[] getUnCompressedData(){
        return huffmanCodes.decodeCompressedData(data);
    }
}
