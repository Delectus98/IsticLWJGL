package fr.delectus98.isticlwjgl.system;

public abstract class ConstTime implements Comparable<ConstTime> {
    /**
     * Return only seconds
     * @return seconds
     */
    public abstract long getSeconds();

    /**
     * Return only milliseconds
     * @return milliseconds
     */
    public abstract long getMilliseconds();

    /**
     * Return only microseconds
     * @return microseconds
     */
    public abstract long getMicroseconds();

    /**
     * Return only nanoseconds
     * @return nanoseconds
     */
    @Deprecated
    public abstract long getNanoseconds();

    /**
     * Return time with seconds accuracy.
     * @return time with seconds accuracy.
     */
    public abstract double asSeconds();
    /**
     * Return time with milliseconds accuracy.
     * @return time with milliseconds accuracy.
     */
    public abstract double asMilliseconds();

    /**
     * Return time with microseconds accuracy.
     * @return time with microseconds accuracy.
     */
    public abstract double asMicroseconds();

    /**
     * Return time with nanoseconds accuracy.
     * @return time with nanoseconds accuracy.
     */
    @Deprecated
    public abstract double asNanoseconds();

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (o instanceof ConstTime) {
            ConstTime time = (ConstTime)o;
            return time.asNanoseconds() == asNanoseconds();
        }

        return false;
    }

    /**
     * Checks if 'this' is greater/lower than an other time or equal
     * @param o compared to
     * @return -1 if 'this' is lower, 1 if 'this' is greater, 0 else
     */
    @Override
    public int compareTo(ConstTime o) {
        if (this.getNanoseconds() > o.getNanoseconds())
            return +1;
        else if (this.getNanoseconds() < o.getNanoseconds())
            return -1;
        else
            return 0;
    }

}
