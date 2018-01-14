
package oraisa.tiivistys.cli;

import java.io.IOException;
import java.nio.file.Paths;
import oraisa.tiivistys.measuring.ActiveMeasurer;

public class Main {
    
    public static void main(String[] args){
        
        String file = "";
        String outputFile = "";
        boolean includeTiming = false;
        boolean runPerformanceTests = false;
        for(String arg: args){
            if(arg.equals("-t")){
                includeTiming = true;
            } if(arg.equals("--performance-tests")){
                runPerformanceTests = true;
            } else {
                if(file.equals("")){
                    file = arg;
                } else {
                    outputFile = arg;
                }
            }
        }
        if(runPerformanceTests){
            try{
                new PerformanceTestRunner(Paths.get(file)).runPerformanceTests(20);
            } catch(IOException e){
                System.err.println("Failed to run performance tests: " + e.getLocalizedMessage());
            }
            return;
        }
        if(!file.equals("")){
            if(includeTiming){
                ActiveMeasurer.setActiveMeasurer(new StopWatchMeasurer());
            }
            if(file.endsWith(FileProcessor.fileSuffix)){
                ActiveMeasurer.getMeasurer().startEntireProcess();
                FileProcessor.unCompressFile(file, outputFile);
                ActiveMeasurer.getMeasurer().endEntireProcess();
            } else {
                ActiveMeasurer.getMeasurer().startEntireProcess();
                FileProcessor.compressFile(file, outputFile);
                ActiveMeasurer.getMeasurer().endEntireProcess();
            }
            if(includeTiming){
                System.err.println(ActiveMeasurer.getMeasurer().toString());
            }
        } else {
            System.err.println("No file name given.");
        }
    }
    
}
