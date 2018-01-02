
package oraisa.tiivistys;
import java.util.*;
import java.io.*;
import java.nio.file.*;

public class Main {
    private static final String fileSuffix = ".tiiv";
    public static void main(String[] args){
        
        if(args.length > 0){
            String file = args[0];
            if(file.endsWith(fileSuffix)){
                unCompressFile(file);
            } else {
                compressFile(file);
            }
        } else {
            System.err.println("No file name given.");
        }
    }
    
    private static void compressFile(String file){
        try{
            byte[] data = Files.readAllBytes(Paths.get(file));
            CompressedFile compressedFile = CompressedFile.fromUnCompressedBytes(data);
            Files.write(Paths.get(file + fileSuffix), compressedFile.getCompressedDataWithHeader());
        } catch(IOException e){
            System.err.println("Failed to read or write file");
        }
    }
    
    private static void unCompressFile(String file){
        try{
            byte[] data = Files.readAllBytes(Paths.get(file));
            CompressedFile compressedFile = CompressedFile.fromCompressedBytes(data);
            Files.write(Paths.get(file.substring(0, file.length() - fileSuffix.length())), 
                    compressedFile.getUnCompressedData());
        } catch(IOException e){
            System.err.println("Failed to read or write file");
        }
    }
}
