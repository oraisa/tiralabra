
package oraisa.tiivistys;

/**
 * A pattern of one to eight bits and the replacement of that pattern.
 */
public class BitPattern {
    private short pattern;
    /**
     * Returns the bit pattern of this object.
     * @return A short representing the bit pattern of this object.
     */
    public short getPattern(){
        return pattern;
    }

    private int bitsInPattern;
    /**
     * Returns the number of bits that this object's pattern has.
     * @return The number of bits in this objects pattern.
     */
    public int getBitsInPattern(){
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
    
    private boolean isStopCode;
    /**
     * Does this pattern represent a stop code.
     * @return A boolean indicating if this pattern represents a stop code.
     */
    public boolean isStopCode(){
        return isStopCode;
    }

    /**
     * Class constructor
     * @param pattern       The bit pattern. Some leading zeros may not be used,
     *                      depending on the parameter bitsInPattern.
     * @param bitsInPattern The number of bits this pattern has. Must be between 
     *                      0 and 16
     * @param replacement   The byte this pattern replaces.
     */
    public BitPattern(short pattern, int bitsInPattern, byte replacement){
        if(bitsInPattern > 16 || bitsInPattern < 0){
            throw new IllegalArgumentException("Cannot have more than 16 or less "
                    + "than zero bits in a bit pattern.");
        }
        this.pattern = pattern;
        this.bitsInPattern = bitsInPattern;
        this.replacement = replacement;
    }

    public BitPattern(int pattern, int bitsInPattern, int replacement){
        this((short)pattern, bitsInPattern, (byte)replacement);
    }
    
    /**
     * Create a BitPattern that represents the stop code of an encoding.
     * @param pattern       The bit pattern. Some leading zeros may not be used,
     *                      depending on the parameter bitsInPattern.
     * @param bitsInPattern The number of bits this pattern has. Must be between 
     *                      0 and 16
     * @return The created stop code BitPattern.
     */
    public static BitPattern createStopCode(short pattern, int bitsInPattern){
        BitPattern stopCode = new BitPattern(pattern, bitsInPattern, 0);
        stopCode.isStopCode = true;
        return stopCode;
    }
    
    /**
     * Create a BitPattern from the bytes returned by toBytes.
     * @param bytes An array of at least 3 or 4 bytes. Bytes past the first four 
     * are ignored. If the array has 3 bytes, a stop code is created. If the 
     * array has 4 or more bytes, the first byte is used as the replacement 
     * and the BitPattern will not be a stop code.
     * @return The created BitPattern.
     */
    public static BitPattern fromBytes(byte[] bytes){
        if(bytes.length == 3){
            short pattern = (short)((bytes[0] << 8) + Utils.bitwiseByteToInt(bytes[1]));
            int bitsInPattern = bytes[2];
            return createStopCode(pattern, bitsInPattern);
        } else if(bytes.length >= 4){
            byte replacement = bytes[0];
            short pattern = (short)((bytes[1] << 8) + Utils.bitwiseByteToInt(bytes[2]));
            int bitsInPattern = bytes[3];
            return new BitPattern(pattern, bitsInPattern, replacement);
        } else {
            throw new IllegalArgumentException("The given array has less than three bytes.");
        }
    }
    
    /**
     * Convert this object to bytes that can be used in the header of a compressed
     * file. If this pattern is a stop code, 3 bytes are given. The first two are
     * the pattern of this object and the last is the number of bits in the pattern.
     * If this pattern is not a stop code, the first byte will be the replacement
     * byte for this pattern and the other three are as above.
     * @return The 3 or 4 bytes representing this pattern.
     */
    public byte[] toBytes(){
        if(isStopCode){
            byte[] bytes = new byte[3];
            bytes[0] = (byte)Utils.shortRightShift(pattern, 8);
            bytes[1] = (byte)pattern;
            bytes[2] = (byte)bitsInPattern;
            return bytes;
        } else {
            byte[] bytes = new byte[4];
            bytes[0] = replacement;
            bytes[1] = (byte)Utils.shortRightShift(pattern, 8);
            bytes[2] = (byte)pattern;
            bytes[3] = (byte)bitsInPattern;
            return bytes;
        }
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
        if(bitsInPattern >= 16){
            throw new IllegalStateException("Cannot add a bit to a pattern that"
                    + " already has 16 bits.");
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
            fixedPattern = pattern - Short.MIN_VALUE * 2;
        }
        String withoutLeadingZeros = Integer.toString(fixedPattern, 2);
        String leadingZeros = "";
        for(int i = 0; i < bitsInPattern - withoutLeadingZeros.length(); i++){
            leadingZeros += "0";
        }
        return leadingZeros + withoutLeadingZeros;
    }
}
