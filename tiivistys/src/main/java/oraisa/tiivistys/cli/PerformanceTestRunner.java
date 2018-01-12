
package oraisa.tiivistys.cli;

import java.nio.file.*;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import oraisa.tiivistys.measuring.ActiveMeasurer;

public class PerformanceTestRunner {
    private final Path testDirectory;
    private final Path compressedFileDirectory;
    private static final String compressedFileDirectoryName = "compressed-files";
    
    public PerformanceTestRunner(Path testDirectory)throws IOException { 
        this.compressedFileDirectory = testDirectory.resolve(Paths.get(compressedFileDirectoryName));
        if(!Files.exists(this.compressedFileDirectory)){
            Files.createDirectory(this.compressedFileDirectory);
        }
        this.testDirectory = testDirectory;
    }
    
    public void runPerformanceTests(int times) throws IOException{
        String compressionTestResultCSV = "File;" + StopWatchMeasurer.CompressionCSVRowHeader;
        String unCompressionTestResultCSV = "File;" + StopWatchMeasurer.UnCompressionCSVRowHeader;
        
        StopWatchMeasurer measurer = new StopWatchMeasurer();
        ActiveMeasurer.setActiveMeasurer(measurer);
        
        for(Path path: Files.newDirectoryStream(testDirectory)){
            if(!Files.isDirectory(path) && !Files.isHidden(path)){
                Path outputPath = compressedFileDirectory.resolve(path.getFileName().toString() + 
                        FileProcessor.fileSuffix);

                for(int i = 0; i < times; i++){
                    System.gc();
                    FileProcessor.compressFile(path.toString(), outputPath.toString());
                    compressionTestResultCSV += path.getFileName().toString() + ";" + 
                            measurer.compressionMeasurementsCSVRow();
                    
                    System.gc();
                    FileProcessor.unCompressFile(outputPath.toString(), "");
                    unCompressionTestResultCSV += outputPath.getFileName().toString() + ";" + 
                            measurer.unCompressionMeasurementsCSVRow();
                }
            }
        }
        
        Files.write(testDirectory.resolve("compression-test.csv"), compressionTestResultCSV.getBytes());
        Files.write(testDirectory.resolve("uncompression-test.csv"), unCompressionTestResultCSV.getBytes());
    }
}
