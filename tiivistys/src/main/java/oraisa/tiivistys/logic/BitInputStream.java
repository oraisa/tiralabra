
package oraisa.tiivistys.logic;

import java.io.ByteArrayInputStream;

/**
 * An input stream to read one bit at a time. The bits are read from an underlying 
 * ByteArrayInputStream specified by the constructor.
 */
public class BitInputStream {
    
    private ByteArrayInputStream bytes;
    private byte buffer;
    private long bitPosition = 0;

    /**
     * Class constructor.
     * @param bytes An array of bytes representing the binary data that is read from.
     */
    public BitInputStream(byte[] bytes){
        this.bytes = new ByteArrayInputStream(bytes);
    }
    
    /**
     * Class constructor.
     * @param stream A ByteArrayInputStream containing the bits to read.
     */
    public BitInputStream(ByteArrayInputStream stream){
        this.bytes = stream;
    }

    private int getBitAtPositionInByte(long position, byte byt){
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
        if(bitPosition % 8 == 0){
            buffer = (byte)bytes.read();
        }
        long oldPosition = bitPosition;
        bitPosition += 1;
        return getBitAtPositionInByte(oldPosition % 8, buffer);
    }
}
