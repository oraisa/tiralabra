
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
     * Return how many bits of data are left.
     * @return The amount of bits left in the data.
     */
    public long bitsLeft(){
        return bytes.length * 8 - bitPosition;
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
        if(bits == 0){
            return false;
        }
        if(bits > bitsLeft()){
            return false;
        }
        
        for(int i = 0; i < bits; i++){
            int bitPositionInPattern = 16 - (bits - i);
            long bitPositionInData = i + bitPosition;
            if(getBitAtBitPosition(bitPositionInData) !=
            getBitAtPositionInShort(bitPositionInPattern, pattern.getPattern())){
                return false;
            }
        }
        bitPosition += bits;
        return true;
    }

    private byte getBitAtBitPosition(long position){
        if(position >= bytes.length * 8){
            throw new IllegalArgumentException("Position " + position + " is outside the data.");
        } else if(position < 0){
            throw new IllegalArgumentException("Negative position");
        }
        byte byt = bytes[(int)(position / 8)];
        return getBitAtPositionInShort(position % 8 + 8, (short)byt);
    }

    private byte getBitAtPositionInShort(long position, short shor){
        if(position >= 16){
            throw new IllegalArgumentException("Position " + position + " >= 16.");
        } else if(position < 0){
            throw new IllegalArgumentException("Negative position");
        }
        int mask = 1 << (16 - (position + 1));
        int result = shor & mask;
        if(result == 0){
            return 0;
        } else {
            return 1;
        }
    }
    
    public int getNextBit(){
        return getBitAtBitPosition(bitPosition++);
    }
}
