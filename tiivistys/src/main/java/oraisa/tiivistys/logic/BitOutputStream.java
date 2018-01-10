
package oraisa.tiivistys.logic;

//TODO: make this a bit output stream
/**
 * An output stream where individual bits can be written to. The bits written to
 * the stream are stored in an underlying array.
 */
public class BitOutputStream {
    
    private byte[] bytes;
    /**
     * Get the bits written to the stream.
     * @return A byte array with all the bits written to the stream. The last bits
     * of the last byte may be zeros if the written bits don't add up to a whole 
     * number of bytes. This method return a newly created array each time it 
     * is called.
     */
    public byte[] getBytes(){
        byte[] arrayToReturn = new byte[(int)((nextBitPosition - 1) / 8 + 1)];
        for(int i = 0; i < arrayToReturn.length; i++){
            arrayToReturn[i] = bytes[i];
        }
        return arrayToReturn;
    }
    private long nextBitPosition = 0;
    
    /**
     * Class constructor.
     */
    public BitOutputStream(){
        bytes = new byte[100];
    }
    
    /**
     * Write bits to this stream.
     * @param bits A byte with the bits to write.
     * @param numberOfBits The number of least significant bits to write from
     * bits. Must be positive and at most 8.
     */
    public void writeBits(byte bits, int numberOfBits){
        if(numberOfBits <= 0 || numberOfBits > 8){
            throw new IllegalArgumentException("Cannot write more than 8 or less than"
                    + "one bits to the bit stream.");
        }
        while(bytes.length * 8 - nextBitPosition < numberOfBits){
            expandArray();
        }
        
        int bitIndexInLastByte = (int)(nextBitPosition % 8);
        
        int bitsToNextByte = numberOfBits + bitIndexInLastByte - 8;
        if(bitsToNextByte > 0){
            addBitsToLastByte(Utils.byteRightShift(bits, bitsToNextByte), 
                    (byte)(numberOfBits - bitsToNextByte));
            addBitsToLastByte((byte)bits, (byte)bitsToNextByte);
        } else {
            addBitsToLastByte(bits, numberOfBits);
        }
    }
    /**
     * Write bits to this stream.
     * @param bits An int with the bits to write.
     * @param numberOfBits The number of least significant bits to write from 
     * bits. Must be positive and at most 8.
     */
    public void writeBits(int bits, int numberOfBits){
        writeBits((byte)bits, numberOfBits);
    }
    
    private void addBitsToLastByte(byte bits, int numberOfBits){
        int lastByteIndex = (int)(nextBitPosition / 8);
        int bitIndexInLastByte = (int)(nextBitPosition % 8);
        byte lastByte = bytes[lastByteIndex];
        byte shiftedBits = (byte)(bits << (8 - numberOfBits - bitIndexInLastByte));
        byte newLastByte = (byte)(lastByte + shiftedBits);
        bytes[lastByteIndex] = newLastByte;
        nextBitPosition += numberOfBits;
    }
    
    private void expandArray(){
        byte[] newArray = new byte[bytes.length *  2];
        for(int i = 0; i < bytes.length; i++){
            newArray[i] = bytes[i];
        }
        bytes = newArray;
    }
}
