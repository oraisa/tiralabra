
package oraisa.tiivistys;

/**
 * A matcher for matching BitPatterns against given binary data.
 * @see BitPattern
 */
public class BitMatcher {
    private byte[] bytes;
    private long bitPosition = 0;

    /**
     * Class constructor.
     * @param bytes An array of bytes representing the binary data this object
     *              matches BitPatterns against.
     */
    public BitMatcher(byte[] bytes){
        this.bytes = bytes;
    }

    /**
     * Match a BitPattern against the data of this object. The pattern is 
     * matched against the beginning of the data that hasn't been matched yet. 
     * If the pattern matches, the bits it matched are marked and are not 
     * considered with future matches.
     * @param pattern   The pattern to match against.
     * @return          Whether the pattern matched.
     * @see             BitPattern
     */
    public boolean matchBitPattern(BitPattern pattern){
        int bits = pattern.getBitsInPattern();
        for(int i = 0; i < bits; i++){
            int bitPositionInPattern = 8 - (bits - i);
            long bitPositionInData = i + bitPosition;
            if(getBitAtBitPosition(bitPositionInData) !=
            getBitAtPositionInByte(bitPositionInPattern, pattern.getPattern())){
                return false;
            }
        }
        bitPosition += bits;
        return true;
    }

    private byte getBitAtBitPosition(long position){
        if(position >= bytes.length * 8){
            throw new ArrayIndexOutOfBoundsException("Position " + position + " is outside the data.");
        } else if(position < 0){
            throw new ArrayIndexOutOfBoundsException("Negative position");
        }
        byte byt = bytes[(int)(position / 8)];
        return getBitAtPositionInByte(position % 8, byt);
    }

    private byte getBitAtPositionInByte(long position, byte byt){
        if(position >= 8){
            throw new ArrayIndexOutOfBoundsException("Position " + position + " >= 8.");
        } else if(position < 0){
            throw new ArrayIndexOutOfBoundsException("Negative position");
        }
        int mask = 1 << (8 - (position + 1));
        int result = byt & mask;
        if(result == 0){
            return 0;
        } else {
            return 1;
        }
    }
}
