
package oraisa.tiivistys.logic;

/**
 * A collection storing the frequencies of individual bytes.
 * @author ossi
 */
public class ByteFrequencyCollection {
    //The frequency for byte b is at index b - Byte.MIN_VALUE
    private long[] frequencies;
    
    /**
     * Class constructor.
     */
    public ByteFrequencyCollection(){
        frequencies = new long[this.size()];
    }
    
    /**
     * Get the frequency for byte b
     * @param b The byte to get the frequency for.
     * @return The frequency of the byte.
     */
    public long get(byte b){
        return frequencies[b - Byte.MIN_VALUE];
    }
    
    /**
     * Increment the frequency of b by 1.
     * @param b The byte whose frequency is incremented.
     */
    public void incrementByte(byte b){
        frequencies[b - Byte.MIN_VALUE]++;
    }
    
    /**
     * Get the amount of bytes in this collection.
     * @return The amount of bytes in this collection.
     */
    public int size(){
        return 256;
    }
    
    /**
     * Set the frequency of a byte.
     * @param index The byte whose frequency is set.
     * @param value The value to set the frequency to.
     */
    public void put(byte index, long value){
        frequencies[index - Byte.MIN_VALUE] = value;
    }
}
