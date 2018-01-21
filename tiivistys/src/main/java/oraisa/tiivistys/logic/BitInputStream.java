
package oraisa.tiivistys.logic;

/**
 * An input stream read one bit at a time. The bits are read from an underlying 
 * byte array specified by the constructor.
 */
public class BitInputStream {
    
    private byte[] bytes;
    private long bitPosition = 0;

    /**
     * Class constructor.
     * @param bytes An array of bytes representing the binary data that is read from.
     */
    public BitInputStream(byte[] bytes){
        this.bytes = bytes;
    }
    
    /**
     * Return how many bits of data are left.
     * @return The amount of bits left in the data.
     */
    public long bitsLeft(){
        return bytes.length * 8 - bitPosition;
    }

    private byte getBitAtBitPosition(long position){
        if(position >= bytes.length * 8){
            throw new IllegalArgumentException("Position " + position + " is outside the data.");
        } else if(position < 0){
            throw new IllegalArgumentException("Negative position");
        }
        byte byt = bytes[(int)(position / 8)];
        return getBitAtPositionInByte(position % 8, byt);
    }

    private byte getBitAtPositionInByte(long position, byte byt){
        int mask = 1 << (8 - (position + 1));
        int result = byt & mask;
        if(result == 0){
            return 0;
        } else {
            return 1;
        }
    }
    
    /**
     * Read a bit from the underlying data.
     * @return The next bit of data.
     */
    public int readBit(){
        return getBitAtBitPosition(bitPosition++);
    }
}
