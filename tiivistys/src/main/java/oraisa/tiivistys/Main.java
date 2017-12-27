
package oraisa.tiivistys;

public class Main {
    public static void main(String[] args){
        System.out.println((byte)1 << 7);
        
        System.out.println(Integer.toString(0xFF, 2));
        byte byt = 4 + 8 + 2;
        int position = 3;
        int mask = 1 << (8 - (position + 1));
        int result = byt & mask;
        if(result == 0){
            System.out.println("0");
        } else {
            System.out.println("1");
        }
    }
}
