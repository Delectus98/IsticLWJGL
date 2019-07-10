package fr.delectus98.isticlwjgl.graphics;


public abstract class ConstColor {
    public abstract float getR();

    public abstract float getG();

    public abstract float getB();

    public abstract float getA();

    @Override
    public final boolean equals(Object o) {
        if (o == this) return true;
        if (o instanceof ConstColor) {
            ConstColor color = (ConstColor)o;
            return color.getR() == getR() && color.getG() == getG() && color.getB() == getB() && color.getA() == getA();
        }

        return false;
    }
}
