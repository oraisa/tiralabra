
package oraisa.tiivistys.cli;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import oraisa.tiivistys.logic.CompressedFile;
import oraisa.tiivistys.measuring.ActiveMeasurer;

public class FileProcessor {
    
    public static final String fileSuffix = ".tiiv";
    
    public static void compressFile(String file, String outputFile){
        try{
            ActiveMeasurer.getMeasurer().startReadingFile();
            byte[] data = Files.readAllBytes(Paths.get(file));
            ActiveMeasurer.getMeasurer().endReadingFile();
            ActiveMeasurer.getMeasurer().reportFileSizeBeforeCompression(data.length);
            
            CompressedFile compressedFile = CompressedFile.fromUnCompressedBytes(data);
            byte[] compressedData = compressedFile.getCompressedDataWithHeader();
            ActiveMeasurer.getMeasurer().reportFileSizeAfterCompression(compressedData.length);
            
            ActiveMeasurer.getMeasurer().startWritingFile();
            Path outPath;
            if(outputFile.equals("")){
                outPath = Paths.get(file + fileSuffix);
            } else {
                outPath = Paths.get(outputFile);
            }
            Files.write(outPath, compressedData);
            ActiveMeasurer.getMeasurer().endWritingFile();
        } catch(IOException e){
            System.err.println(e.getLocalizedMessage());
            System.exit(-1);
        }
    }
    
    public static void unCompressFile(String file, String outputFile){
        try{
            ActiveMeasurer.getMeasurer().startReadingFile();
            byte[] data = Files.readAllBytes(Paths.get(file));
            ActiveMeasurer.getMeasurer().endReadingFile();
            
            CompressedFile compressedFile = CompressedFile.fromCompressedBytes(data);
            byte[] unCompressedData = compressedFile.getUnCompressedData();
            
            ActiveMeasurer.getMeasurer().startWritingFile();
            Path outPath;
            if(outputFile.equals("")){
                outPath = Paths.get(file.substring(0, file.length() - fileSuffix.length()));
            } else {
                outPath = Paths.get(outputFile);
            }
            Files.write(outPath, unCompressedData);
            ActiveMeasurer.getMeasurer().endWritingFile();
        } catch(IOException e){
            System.err.println(e.getLocalizedMessage());
            System.exit(-1);
        }
    }
    
    private FileProcessor(){}
}
