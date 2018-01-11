
package oraisa.tiivistys.logic;

public class ByteFrequencyCollection {
    //The frequency for byte b is at index b - Byte.MIN_VALUE
    private long[] frequencies;
    
    public ByteFrequencyCollection(){
        frequencies = new long[this.size()];
    }
    
    public long get(byte b){
        return frequencies[b - Byte.MIN_VALUE];
    }
    
    public void incrementByte(byte b){
        frequencies[b - Byte.MIN_VALUE]++;
    }
    
    public int size(){
        return 256;
    }
    
    public void put(byte index, long value){
        frequencies[index - Byte.MIN_VALUE] = value;
    }
}
