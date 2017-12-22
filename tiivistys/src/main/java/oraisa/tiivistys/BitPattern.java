
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

    public BitPattern(byte pattern, byte bitsInPattern){
        this.pattern = pattern;
        this.bitsInPattern = bitsInPattern;
    }
}
