
package oraisa.tiivistys.cli;

/**
 * A stop watch for measuring time. Uses System.currentTimeMillis to measure 
 * time.
 */
public class StopWatch {
    
    private long startTime;
    private long stopTime;
    private boolean running = false;
    
    /**
     * Start the stop watch.
     */
    public void start(){
        startTime = System.currentTimeMillis();
        running = true;
    }
    
    /**
     * Stop the stop watch.
     */
    public void stop(){
        stopTime = System.currentTimeMillis();
        running = false;
    }
    
    /**
     * Return the elapsed time. If this stop watch is running, return the time
     * elapsed between the last call to start and present. If this is not 
     * running, return the time between the latest calls to start and stop.
     * @return The elapsed time in milliseconds.
     */
    public long getElapsedTime(){
        if(isRunning()){
            return System.currentTimeMillis() - startTime;
        } else {
            return stopTime - startTime;
        }
    }
    
    /**
     * Is this stop watch running.
     * @return Is this stop watch running.
     */
    public boolean isRunning(){
        return running;
    }
}
