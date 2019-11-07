package fr.delectus98.isticlwjgl.graphics.model;


import java.util.Set;

public interface ModelFormat {
    String getObjectName();
    int getVerticesCount();
    int getSampleSize(String name);
    int[] getSamplesSize();
    Set<String> getBufferNames();
    float[] getBufferData(String type);
}
