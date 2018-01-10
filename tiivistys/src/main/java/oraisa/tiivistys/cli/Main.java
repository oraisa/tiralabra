
package oraisa.tiivistys.cli;
import java.util.*;
import java.io.*;
import java.nio.file.*;
import oraisa.tiivistys.logic.CompressedFile;

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
            StopWatch sw = new StopWatch();
            sw.start();
            byte[] data = Files.readAllBytes(Paths.get(file));
            sw.stop();
            System.out.println("Time to read file: " + sw.getElapsedTime());
            
            sw.start();
            CompressedFile compressedFile = CompressedFile.fromUnCompressedBytes(data);
            sw.stop();
            System.err.println("Time to create CompressedFile: " + sw.getElapsedTime());
            
            sw.start();
            byte[] compressedData = compressedFile.getCompressedDataWithHeader();
            sw.stop();
            System.err.println("Time to add header: " + sw.getElapsedTime());
            
            sw.start();
            Files.write(Paths.get(file + fileSuffix), compressedData);
            sw.stop();
            System.err.println("Time to write file: " + sw.getElapsedTime());
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
