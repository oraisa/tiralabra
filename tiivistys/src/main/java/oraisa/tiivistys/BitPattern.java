
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
}
