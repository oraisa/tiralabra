
package oraisa.tiivistys.cli;
import java.io.*;
import java.nio.file.*;
import oraisa.tiivistys.logic.CompressedFile;
import oraisa.tiivistys.measuring.ActiveMeasurer;

public class Main {
    private static final String fileSuffix = ".tiiv";
    
    public static void main(String[] args){
        
        String file = "";
        String outputFile = "";
        boolean includeTiming = false;
        for(String arg: args){
            if(arg.equals("-t")){
                includeTiming = true;
            } else {
                if(file.equals("")){
                    file = arg;
                } else {
                    outputFile = arg;
                }
            }
        }
        if(!file.equals("")){
            if(includeTiming){
                ActiveMeasurer.setActiveMeasurer(new StopWatchMeasurer());
            }
            if(file.endsWith(fileSuffix)){
                ActiveMeasurer.getMeasurer().startEntireProcess();
                unCompressFile(file, outputFile);
                ActiveMeasurer.getMeasurer().endEntireProcess();
            } else {
                ActiveMeasurer.getMeasurer().startEntireProcess();
                compressFile(file, outputFile);
                ActiveMeasurer.getMeasurer().endEntireProcess();
            }
            if(includeTiming){
                System.err.println(ActiveMeasurer.getMeasurer().toString());
            }
        } else {
            System.err.println("No file name given.");
        }
    }
    
    private static void compressFile(String file, String outputFile){
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
        }
    }
    
    private static void unCompressFile(String file, String outputFile){
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
        }
    }
}
