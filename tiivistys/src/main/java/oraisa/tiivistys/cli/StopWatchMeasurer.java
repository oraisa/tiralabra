
package oraisa.tiivistys.cli;

import oraisa.tiivistys.measuring.Measurer;

/**
 * Measures the duration of calculations with StopWatches
 */
public class StopWatchMeasurer implements Measurer {
    private StopWatch headerParsing = new StopWatch();
    private StopWatch huffmanEncodingCalculation = new StopWatch();
    private StopWatch writingHeader = new StopWatch();
    private StopWatch encodingData = new StopWatch();
    private StopWatch decodingData = new StopWatch();
    private StopWatch countingCharacters = new StopWatch();
    private StopWatch readingFile = new StopWatch();
    private StopWatch writingFile = new StopWatch();
    private StopWatch entireProcess = new StopWatch();
    private long fileSizeBeforeCompression;
    private long fileSizeAfterCompression;
    private int distinctCharacters;

    @Override
    public void startHeaderParsing() {
        headerParsing.start();
    }

    @Override
    public void endHeaderParsing() {
        headerParsing.stop();
    }

    @Override
    public void startHuffmanEncodingCalculation() {
        huffmanEncodingCalculation.start();
    }

    @Override
    public void endHuffmanEncodingCalculation() {
        huffmanEncodingCalculation.stop();
    }

    @Override
    public void startWritingHeader() {
        writingHeader.start();
    }

    @Override
    public void endWritingHeader() {
        writingHeader.stop();
    }

    @Override
    public void startEncodingData() {
        encodingData.start();
    }

    @Override
    public void endEncodingData() {
        encodingData.stop();
    }

    @Override
    public void startDecodingData() {
        decodingData.start();
    }

    @Override
    public void endDecodingData() {
        decodingData.stop();
    }

    @Override
    public void startCountingCharacters() {
        countingCharacters.start();
    }

    @Override
    public void endCountingCharacters() {
        countingCharacters.stop();
    }

    @Override
    public void startReadingFile() {
        readingFile.start();
    }

    @Override
    public void endReadingFile() {
        readingFile.stop();
    }

    @Override
    public void startWritingFile() {
        writingFile.start();
    }

    @Override
    public void endWritingFile() {
        writingFile.stop();
    }

    @Override
    public void reportFileSizeBeforeCompression(long bytes) {
        fileSizeBeforeCompression = bytes;
    }

    @Override
    public void reportFileSizeAfterCompression(long bytes) {
        fileSizeAfterCompression = bytes;
    }
    
    @Override
    public void reportDistinctCharacterCount(int characters){
        distinctCharacters = characters;
    }
    
    @Override
    public String toString(){
        return "Time to parse header: " + headerParsing.getElapsedTime() + "\n" + 
                "Time to create Huffman Encoding: " + huffmanEncodingCalculation.getElapsedTime() + "\n" + 
                "Time to write Huffman Encoding: " + writingHeader.getElapsedTime() + "\n" + 
                "Time to encode data: " + encodingData.getElapsedTime() + "\n" + 
                "Time to decode data: " + decodingData.getElapsedTime() + "\n" + 
                "Time to count characters: " + countingCharacters.getElapsedTime() + "\n" + 
                "Time to read file: " + readingFile.getElapsedTime() + "\n" + 
                "Time to write file: " + writingFile.getElapsedTime() + "\n" + 
                "Time for the entire procedure: " + entireProcess.getElapsedTime() + "\n" + 
                "File size before: " + fileSizeBeforeCompression + "\n" + 
                "File size after: " + fileSizeAfterCompression;
    }

    @Override
    public void startEntireProcess() {
        entireProcess.start();
    }

    @Override
    public void endEntireProcess() {
        entireProcess.stop();
    }
    
    /**
     * Get a CSV row with the results of compression.
     * @return The CSV row of the compression results.
     */
    public String compressionMeasurementsCSVRow(){
        return huffmanEncodingCalculation.getElapsedTime() + ";" + 
                encodingData.getElapsedTime() + ";" + 
                countingCharacters.getElapsedTime() + ";" + 
                fileSizeBeforeCompression + ";" + 
                fileSizeAfterCompression + ";" + 
                distinctCharacters + "\n";
                
    }
    /**
     * The header for the CSV row returned by compressionMeasurementsCSVRow.
     */
    public static final String CompressionCSVRowHeader = 
            "Huffman Encoding Calculation;Encoding;Counting Characters;File size before;"
            + "File size after;Distinct characters\n";
    
    /**
     * Get a CSV row with the results of uncompression.
     * @return The CSV row with the results.
     */
    public String unCompressionMeasurementsCSVRow(){
        return headerParsing.getElapsedTime() + ";" + 
                decodingData.getElapsedTime() + "\n";
    }
    
    /**
     * The header for a CSV row returned by unCompressionMeasurementsCSVRow.
     */
    public static final String UnCompressionCSVRowHeader = 
            "Header parsing;Decoding\n";
}

