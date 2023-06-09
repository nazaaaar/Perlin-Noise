package edu.nazaaaar.perlinnoise.heightmap;

import java.util.LinkedList;
import java.util.List;

import utils.Func;
import utils.FuncArgs;

public class Heightmap {
    private int minvalue;
    private int maxvalue;
    private final List<List<Integer>> heightMap = new LinkedList<>();
    private Mappable generator;
    private int sizeX;
    private int sizeY;
    private double offsetX = 0, offsetY = 0;

    public Heightmap(int MINVALUE, int MAXVALUE, int sizeX, int sizeY, Mappable generator) {
        minvalue = MINVALUE;
        maxvalue = MAXVALUE;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.generator = generator;

        for (int i = 0; i < sizeX; i++) {
            List<Integer> row = new LinkedList<>();
            for (int j = 0; j < sizeY; j++) {
                row.add(0);
            }
            heightMap.add(row);
        }
    }

    public Mappable getGenerator() {
        return generator;
    }

    public void setGenerator(Mappable generator) {
        this.generator = generator;
    }

    public void create(){
        final int D = maxvalue-minvalue;

        for (int i = 0; i < sizeX; i++) {
            List<Integer> row = heightMap.get(i);
            for (int j = 0; j < sizeY; j++) {
                row.set(j, minvalue + (int)(generator.generate(i + offsetX, j+offsetY)*D));
            }
        }
    }
    public void forEach(Func<MapArgs> func){
        for (int i = 0; i < heightMap.size(); i++) {
            List<Integer> row = heightMap.get(i);
            for (int j = 0; j < row.size(); j++) {
                Integer value = row.get(j);
                func.invoke(new MapArgs(i, j, value));
            }
        }
    }

    public double getOffsetY() {
        return offsetY;
    }

    public void setOffsetY(double offsetY) {
        this.offsetY = offsetY;
    }

    public double getOffsetX() {
        return offsetX;
    }

    public void setOffsetX(double offsetX) {
        this.offsetX = offsetX;
    }

    public int getSizeY() {
        return sizeY;
    }

    public void setSizeY(int sizeY) {
        this.sizeY = sizeY;
        for (List<Integer> row : heightMap) {
            while (row.size() < sizeY) {
                row.add(0);
            }
            while (row.size() > sizeY) {
                row.remove(row.size() - 1);
            }
        }
    }

    static public class MapArgs extends FuncArgs {
        public MapArgs(int x, int y, int val) {
            this.x = x;
            this.y = y;
            this.val = val;
        }
        public int x;
        public int y;
        public int val;
    }

    public int getMinvalue() {
        return minvalue;
    }

    public void setMinvalue(int minvalue) {
        this.minvalue = minvalue;
    }

    public int getMaxvalue() {
        return maxvalue;
    }

    public void setMaxvalue(int maxvalue) {
        this.maxvalue = maxvalue;
    }

    public int getSizeX() {
        return sizeX;
    }

    public void setSizeX(int sizeX) {
        this.sizeX = sizeX;
        while (heightMap.size() < sizeX) {
            List<Integer> row = new LinkedList<>();
            while (row.size() < sizeY) {
                row.add(0);
            }
            heightMap.add(row);
        }
        while (heightMap.size() > sizeX) {
            heightMap.remove(heightMap.size() - 1);
        }
    }
}
