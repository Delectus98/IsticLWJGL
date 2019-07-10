package fr.delectus98.isticlwjgl.system;

/**
 * Time interface with seconds, milliseconds, microseconds, nanoseconds accuracy
 */
public final class Time extends ConstTime {
    private long nanoseconds;

    /**
     * Create a time interface initialized with 0 elapsed nanoseconds
     * @return time with 0 as initial value
     */
    public static Time zero() {
        return new Time(0);
    }

    /**
     * Create a time interface initialized with seconds
     * @param seconds initial value
     * @return time with default seconds
     */
    public static Time seconds(long seconds){
        return new Time(seconds * 1000000000);
    }

    /**
     * Create a time interface initialized with milliseconds
     * @param milliseconds initial value
     * @return time with default milliseconds
     */
    public static Time milliseconds(long milliseconds){
        return new Time(milliseconds * 1000000);
    }

    /**
     * Create a time interface initialized with microseconds
     * @param microseconds initial value
     * @return time with default microseconds
     */
    public static Time microseconds(long microseconds){
        return new Time(microseconds * 1000);
    }

    /**
     * Create a time interface initialized with nanoseconds
     * @param nanoseconds initial value
     * @return time with dafault nanoseconds
     */
    @Deprecated
    public static Time nanoseconds(long nanoseconds){
        return new Time(nanoseconds);
    }


    /**
     * Creates Time with a default nanoseconds value
     * @param value initial nanoseconds value
     */
    public Time(long value){
        this.nanoseconds = value;
    }

    /**
     * Creates Time using an other Time nanoseconds value
     * @param t time reference
     */
    public Time(Time t){
        this.nanoseconds = t.nanoseconds;
    }

    /**
     * Return only seconds
     * @return seconds
     */
    public long getSeconds() {
        return (long)(nanoseconds / (1000000000.0));
    }

    /**
     * Return only milliseconds
     * @return milliseconds
     */
    public long getMilliseconds(){
        return  (long)(nanoseconds / (1000000.0)) - this.getSeconds() * 1000;
    }

    /**
     * Return only microseconds
     * @return microseconds
     */
    public long getMicroseconds(){
        return nanoseconds / (1000) - this.getMilliseconds() * 1000 - this.getSeconds() * 1000000;
    }

    /**
     * Return only nanoseconds
     * @return nanoseconds
     */
    @Deprecated
    public long getNanoseconds(){
        return nanoseconds;
    }

    /**
     * Return time with seconds accuracy.
     * @return time with seconds accuracy.
     */
    public double asSeconds(){
        return (double)nanoseconds / (1000000000.0);
    }

    /**
     * Return time with milliseconds accuracy.
     * @return time with milliseconds accuracy.
     */
    public double asMilliseconds() {
        return (double)nanoseconds / (1000000.0);
    }

    /**
     * Return time with microseconds accuracy.
     * @return time with microseconds accuracy.
     */
    public double asMicroseconds(){
        return (double)nanoseconds / (1000.0);
    }

    /**
     * Return time with nanoseconds accuracy.
     * @return time with nanoseconds accuracy.
     */
    @Deprecated
    public double asNanoseconds(){
        return (double)nanoseconds;
    }

    /**
     * Multiplies time to factor. If time result is negative, result became 0
     * @param fact factor
     */
    public void mul(double fact) {
        this.nanoseconds *= fact;
        if (this.nanoseconds < 0)
            this.nanoseconds = 0;
    }

    /**
     * Add time to current.
     * @param time added time
     */
    public void add(Time time){
        this.nanoseconds += time.nanoseconds;
    }

    /**
     * Reduce time to current. If time result is negative, result became 0
     * @param time removed time
     */
    public void reduce(Time time){
        this.nanoseconds -= time.nanoseconds;
        if (this.nanoseconds < 0)
            this.nanoseconds = 0;
    }

    /**
     * Clone current time
     * @return cloned current time
     */
    @Override
    public Time clone(){
        return new Time(nanoseconds);
    }


}
