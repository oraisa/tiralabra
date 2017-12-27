
package oraisa.tiivistys;

/**
 * A pattern of one to eight bits and the replacement of that pattern.
 */
public class BitPattern {
    private byte pattern;
    /**
     * Returns the bit pattern of this object.
     * @return A byte representing the bit pattern of this object.
     */
    public byte getPattern(){
        return pattern;
    }

    private byte bitsInPattern;
    /**
     * Returns the number of bits that this object's pattern has.
     * @return The number of bits in this objects pattern.
     */
    public byte getBitsInPattern(){
        return bitsInPattern;
    }

    private byte replacement;
    /**
     * The byte this bit pattern replaces.
     * @return The replacement for this bit pattern.
     */
    public byte getReplacement(){
        return replacement;
    }

    /**
     * Class constructor
     * @param pattern       The bit pattern. Some leading zeros may not be used,
     *                      depending on the parameter bitsInPattern.
     * @param bitsInPattern The number of bits this pattern has.
     * @param replacement   The byte this pattern replaces.
     */
    public BitPattern(byte pattern, byte bitsInPattern, byte replacement){
        this.pattern = pattern;
        this.bitsInPattern = bitsInPattern;
        this.replacement = replacement;
    }

    public BitPattern(int pattern, int bitsInPattern, int replacement){
        this((byte)pattern, (byte)bitsInPattern, (byte)replacement);
    }
    
    /**
     * Create a new BitPattern with the pattern of this object with a bit added
     * to the end. The new pattern will have the same replacement as this 
     * object.
     * @param bit   Either a 1 or a 0 to add to the pattern.
     * @return The new BitPattern.
     * @throws IllegalStateException If the pattern already has 8 bits.
     * @throws IllegalArgumentException If the parameter is not 1 or 0.
     */
    public BitPattern addBit(byte bit){
        if(bitsInPattern == 8){
            throw new IllegalStateException("Cannot add a bit to a pattern that"
                    + "already has 8 bits.");
        }
        if(bit < 0 || bit > 1){
            throw new IllegalArgumentException("Argument must be 1 or 0");
        }
        return new BitPattern((pattern << 1) + bit, bitsInPattern + 1, replacement);
    }
    
    /**
     * Converts this pattern to a string representing the binary numbers in this
     * pattern.
     * @return The string representation of this pattern.
     */
    @Override
    public String toString(){
        int fixedPattern = pattern;
        //Don't consider the pattern signed.
        if(pattern < 0){
            fixedPattern = pattern - Byte.MIN_VALUE * 2;
        }
        String withoutLeadingZeros = Integer.toString(fixedPattern, 2);
        String leadingZeros = "";
        for(int i = 0; i < bitsInPattern - withoutLeadingZeros.length(); i++){
            leadingZeros += "0";
        }
        return leadingZeros + withoutLeadingZeros;
    }
}
