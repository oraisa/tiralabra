
package oraisa.tiivistys.cli;
import oraisa.tiivistys.measuring.ActiveMeasurer;

public class Main {
    
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
