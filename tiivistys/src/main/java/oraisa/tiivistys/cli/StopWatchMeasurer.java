/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oraisa.tiivistys.cli;

import oraisa.tiivistys.measuring.Measurer;

/**
 *
 * @author ossi
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
}
