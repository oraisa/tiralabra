
package oraisa.tiivistys;

/**
 * An array of bits.
 */
public class BitArray {
    
    private byte[] bytes;
    /**
     * Get the bits added to the array.
     * @return A byte array with all the bits added to the array. The last bits
     * of the last byte may be zeros if the added bits don't add up to a whole 
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
    public BitArray(){
        bytes = new byte[100];
    }
    /**
     * Class constructor.
     * @param bytes An array of bytes with the initial contents of this BitArray.
     */
    public BitArray(byte[] bytes){
        this.bytes = bytes;
        nextBitPosition = bytes.length * 8;
    }
    
    /**
     * Add the bits of a BitPattern to the end of this bit array.
     * @param pattern The BitPattern to add to the end of this array.
     * @see BitPattern
     */
    public void addBitPattern(BitPattern pattern){
        addBits(pattern.getPattern(), pattern.getBitsInPattern());
    }
    
    /**
     * Add bits to the end of this array.
     * @param bits A short with the bits to add.
     * @param numberOfBits The number of least significant bits to add from bits.
     */
    public void addBits(short bits, int numberOfBits){
        if(numberOfBits < 0 || numberOfBits > 16){
            throw new IllegalArgumentException("Cannot add more than 16 or less than"
                    + "zero bits to the bit array.");
        }
        if(numberOfBits < 16){
            addLessThan8Bits((byte)bits, numberOfBits);
        } else {
            //Split the short into two bytes.
            addLessThan8Bits((byte)(bits >> 8), numberOfBits - 8);
            addLessThan8Bits((byte)bits, 8);
        }
    }
    
    private void addLessThan8Bits(byte bits, int numberOfBits){
        while(bytes.length * 8 - nextBitPosition < numberOfBits){
            expandArray();
        }
        
        int bitIndexInLastByte = (int)(nextBitPosition % 8);
        
        int bitsToNextByte = numberOfBits + bitIndexInLastByte - 8;
        if(bitsToNextByte > 0){
            addBitsToLastByte((byte)(bits >> bitsToNextByte), (byte)(numberOfBits - bitsToNextByte));
            addBitsToLastByte((byte)bits, (byte)bitsToNextByte);
        } else {
            addBitsToLastByte(bits, numberOfBits);
        }
        
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
