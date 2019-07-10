package fr.delectus98.isticlwjgl.system;


/**
 * Clock interface computing elapsed time between two timestamps.
 */
public class Clock {
    /**
     * Clock accuracy.
     */
    public enum Mode {
        NANOSECONDS_ACCURACY,
        MILLISECONDS_ACCURACY
    }

    /**
     * Computes last timestamp according to computer timestamp with specific time accuracy
     * @param mode specified time accuracy
     * @return computer timestamp
     */
    private static long ClockFunction(Mode mode) {
        switch (mode) {
            case NANOSECONDS_ACCURACY: return System.nanoTime();
            case MILLISECONDS_ACCURACY: return System.currentTimeMillis() * 1000000;
            default: return -1;
        }
    }

    private Mode mode = Mode.MILLISECONDS_ACCURACY;
    private long t1 = 0; // first timestamp
    private long t2 = 0; // second timestamp

    /**
     * Generates a clock that start to run with specific mode.
     * @param mode specified mode
     */
    public Clock(Mode mode) {
        this.mode = mode;
        t1 = t2 = ClockFunction(mode);
    }



    /**
     * Generates a clock that start to run with NANOSECONDS_ACCURACY.
     * Default accuracy is NANOSECONDS_ACCURACY.
     */
    public Clock(){
        t1 = t2 = ClockFunction(mode);
    }

    /**
     * Restarts the clock.
     * Compute the elapsed time between two timestamps.
     * @return elapsed time between now and the previous timestamp.
     */
    public Time restart(){
        t2 = ClockFunction(mode);
        long elapsed = t2 - t1;
        t1 = t2;
        return new Time(elapsed);
    }

    /**
     * Computes the elapsed time between two timestamps.
     * @return elapsed time between now and the previous timestamp.
     */
    public Time getElapsed(){
        t2 = ClockFunction(mode);
        long elapsed = t2 - t1;
        return new Time(elapsed);
    }
}
