
package oraisa.tiivistys;


public class BitPattern {
    private byte pattern;
    public byte getPattern(){
        return pattern;
    }

    private byte bitsInPattern;
    public byte getBitsInPattern(){
        return bitsInPattern;
    }

    private byte replacement;
    public byte getReplacement(){
        return replacement;
    }

    public BitPattern(byte pattern, byte bitsInPattern, byte replacement){
        this.pattern = pattern;
        this.bitsInPattern = bitsInPattern;
        this.replacement = replacement;
    }

    public BitPattern(int pattern, int bitsInPattern, int replacement){
        this((byte)pattern, (byte)bitsInPattern, (byte)replacement);
    }
    
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
