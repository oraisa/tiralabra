/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oraisa.tiivistys;

/**
 * Utility methods.
 */
public class Utils {
    private Utils(){}
    
    /**
     * Right shift for bytes. Java's behaviour of converting the bytes to 
     * ints before all operations doesn't work with right shifting.
     * @param byt The byte to shift.
     * @param amount How many times to shift.
     * @return The result of the right shift.
     */
    public static byte byteRightShift(byte byt, int amount){
        int fixedI = byt;
        if(fixedI < 0){
            fixedI = fixedI & 0x000000FF;
        }
        return (byte)(fixedI >>> amount);
    }
    
    /**
     * Right shift for shorts. Java's behaviour of converting the shorts to 
     * ints before all operations doesn't work with right shifting.
     * @param sho The short to shift.
     * @param amount How many times to shift.
     * @return The result of the right shift.
     */
    public static short shortRightShift(short sho, int amount){
        int fixedI = sho;
        if(fixedI < 0){
            fixedI = fixedI & 0x0000FFFF;
        }
        return (short)(fixedI >>> amount);
    }
}
