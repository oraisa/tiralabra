
package oraisa.tiivistys.measuring;

public class ActiveMeasurer {
    private ActiveMeasurer(){}
    
    private static Measurer activeMeasurer = new VoidMeasurer();
    public static Measurer getMeasurer(){
        return activeMeasurer;
    }
    public static void setActiveMeasurer(Measurer measurer){
        activeMeasurer = measurer;
    }
}

/**
 * A measurer that doesn't actually measure anything. Used as a default if no
 * measurer has been set.
 * @author ossi
 */
class VoidMeasurer implements Measurer{

    @Override
    public void startHeaderParsing() {
    }

    @Override
    public void endHeaderParsing() {
    }

    @Override
    public void startHuffmanEncodingCalculation() {
    }

    @Override
    public void endHuffmanEncodingCalculation() {
    }

    @Override
    public void startWritingHeader() {
    }

    @Override
    public void endWritingHeader() {
    }

    @Override
    public void startEncodingData() {
    }

    @Override
    public void endEncodingData() {
    }

    @Override
    public void startDecodingData() {
    }

    @Override
    public void endDecodingData() {
    }

    @Override
    public void startCountingCharacters() {
    }

    @Override
    public void endCountingCharacters() {
    }

    @Override
    public void startReadingFile() {
    }

    @Override
    public void endReadingFile() {
    }

    @Override
    public void startWritingFile() {
    }

    @Override
    public void endWritingFile() {
    }

    @Override
    public void reportFileSizeBeforeCompression(long bytes) {
    }

    @Override
    public void reportFileSizeAfterCompression(long bytes) {
    }

    @Override
    public void startEntireProcess() {
    }

    @Override
    public void endEntireProcess() {
    }

    @Override
    public void reportDistinctCharacterCount(int characters) {
    }
}
