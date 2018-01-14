
package oraisa.tiivistys.measuring;

public interface Measurer {
    public void startHeaderParsing();
    public void endHeaderParsing();
    public void startHuffmanEncodingCalculation();
    public void endHuffmanEncodingCalculation();
    public void startWritingHeader();
    public void endWritingHeader();
    public void startEncodingData();
    public void endEncodingData();
    public void startDecodingData();
    public void endDecodingData();
    public void startCountingCharacters();
    public void endCountingCharacters();
    public void startReadingFile();
    public void endReadingFile();
    public void startWritingFile();
    public void endWritingFile();
    public void startEntireProcess();
    public void endEntireProcess();
    public void reportFileSizeBeforeCompression(long bytes);
    public void reportFileSizeAfterCompression(long bytes);
    public void reportDistinctCharacterCount(int characters);
}
