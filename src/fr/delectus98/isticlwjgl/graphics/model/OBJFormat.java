package fr.delectus98.isticlwjgl.graphics.model;


import java.io.*;
import java.util.*;

public class OBJFormat implements ModelFormat {
    private Map<String, Integer> samplesSize;
    private Map<String, float[]> vbos;
    private String objName = "";
    private int count = 0;

    private void update(Map<String, Integer> remaining, String what, int size, int line) throws IOException {
        if (remaining.containsKey(what)) {
            remaining.put(what, remaining.get(what) + size);
            if (samplesSize.get(what) != size) {
                throw new IOException("Size consistency error: " + samplesSize.get(what) + " != " + size + " at line " + line);
            }
        } else {
            remaining.put(what, size);
            samplesSize.put(what, size);
            vbos.put(what, new float[count * size]);
        }

        if (remaining.get(what) / size > count) {
            throw new IOException("Two much vertices according to file specification [o name count] for '" + what + "'"  + " at line " + line);
        }
    }

    private void switchName(String name, StringTokenizer tokenizer, Map<String, Integer> remaining, int line) throws IOException {
        if (name.charAt(0) == '#'){
            /*while (tokenizer.hasMoreTokens()) System.out.print(tokenizer.nextToken() + " ");
            System.out.println();*/
            return ;
        }

        switch (name) {
            case "o": {
                if (tokenizer.countTokens() != 2) throw new IOException();
                objName = tokenizer.nextToken();
                count = Integer.valueOf(tokenizer.nextToken());
                return ;
            }
            case "v": {
                if (tokenizer.countTokens() != 3) throw new IOException("Vertex needs at least 3 floating values at line "  + line);
                float x = Float.valueOf(tokenizer.nextToken());
                float y = Float.valueOf(tokenizer.nextToken());
                float z = Float.valueOf(tokenizer.nextToken());
                update(remaining, name, 3, line);
                int slot = count * 3 - remaining.get(name);
                vbos.get(name)[slot] = x;
                vbos.get(name)[slot + 1] = y;
                vbos.get(name)[slot + 2] = z;
                break;
            }
            case "vn": {
                if (tokenizer.countTokens() != 3) throw new IOException("Normal vector needs at least 3 floating values at line "  + line);
                float x = Float.valueOf(tokenizer.nextToken());
                float y = Float.valueOf(tokenizer.nextToken());
                float z = Float.valueOf(tokenizer.nextToken());
                update(remaining, name, 3, line);
                int slot = count * 3 - remaining.get(name);
                vbos.get(name)[slot] = x;
                vbos.get(name)[slot + 1] = y;
                vbos.get(name)[slot + 2] = z;
                break;
            }
            case "vt": {
                if (tokenizer.countTokens() != 3) throw new IOException("Vertex tangeant needs at least 3 floating values at line "  + line);
                float x = Float.valueOf(tokenizer.nextToken());
                float y = Float.valueOf(tokenizer.nextToken());
                float z = Float.valueOf(tokenizer.nextToken());
                update(remaining, name, 3, line);
                int slot = count * 3 - remaining.get(name);
                vbos.get(name)[slot] = x;
                vbos.get(name)[slot + 1] = y;
                vbos.get(name)[slot + 2] = z;
                break;
            }
            case "tx": {
                if (tokenizer.countTokens() != 2) throw new IOException("Texture coordinates needs at least 2 floating values at line "  + line);
                float x = Float.valueOf(tokenizer.nextToken());
                float y = Float.valueOf(tokenizer.nextToken());
                update(remaining, name, 2, line);
                int slot = count * 2 - remaining.get(name);
                vbos.get(name)[slot] = x;
                vbos.get(name)[slot + 1] = y;
                break;
            }
            case "c": {
                if (tokenizer.countTokens() != 4) throw new IOException("Vertex color needs at least 4 floating values at line "  + line);
                float r = Float.valueOf(tokenizer.nextToken());
                float g = Float.valueOf(tokenizer.nextToken());
                float b = Float.valueOf(tokenizer.nextToken());
                float a = Float.valueOf(tokenizer.nextToken());
                update(remaining, name, 4, line);
                int slot = count * 4 - remaining.get(name);
                vbos.get(name)[slot] = r;
                vbos.get(name)[slot + 1] = g;
                vbos.get(name)[slot + 2] = b;
                vbos.get(name)[slot + 3] = a;
                break;
            }
            default: {
                int tokens = tokenizer.countTokens();
                update(remaining, name, tokens, line);
                int slot = count * tokens - remaining.get(name);
                for (int i = 0 ; i < tokens ; ++i) {
                    vbos.get(name)[i + slot] = Float.valueOf(tokenizer.nextToken());
                }
                break;
            }
        }

    }

    public OBJFormat(String objFile) throws IOException {
        File file = new File(objFile);

        BufferedReader br = new BufferedReader(new FileReader(file));

        samplesSize = new HashMap<>();
        vbos = new HashMap<>();
        objName = "";
        count = 0;
        Map<String, Integer> remaining = new HashMap<>();

        int line = 0;
        String st;
        while ((st = br.readLine()) != null) {
            StringTokenizer tokenizer = new StringTokenizer(st);

            if (tokenizer.hasMoreTokens()) {
                String name = tokenizer.nextToken();
                switchName(name, tokenizer, remaining, line);
            }
            line++;
        }

        if (remaining.entrySet().stream().anyMatch(e -> (e.getValue() / samplesSize.get(e.getKey()) != count))) {
            throw new IOException("Too few vertices according to file specification [o name count].");
        }
    }

    @Override
    public String getObjectName() {
        return objName;
    }

    @Override
    public int getVerticesCount() {
        return count;
    }

    @Override
    public int getSampleSize(String name) {
        return samplesSize.getOrDefault(name, 0);
    }

    @Override
    public int[] getSamplesSize() {
        int[] tmp = new int[samplesSize.size()];
        List<String> names = new ArrayList<>(samplesSize.keySet());
        for (int i = 0 ; i < tmp.length ; ++i) {
            tmp[i] = samplesSize.get(names.get(i));
        }
        return tmp;
    }

    @Override
    public Set<String> getBufferNames() {
        return vbos.keySet();
    }

    @Override
    public float[] getBufferData(String type) throws IllegalArgumentException {
        if (!vbos.containsKey(type)) {
            throw new IllegalArgumentException("'type' is not a valid vbo name.");
        }

        return vbos.get(type);
    }
}
