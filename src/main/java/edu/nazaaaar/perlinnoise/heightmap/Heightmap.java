package edu.nazaaaar.perlinnoise.heightmap;

import utils.Func;
import utils.FuncArgs;

public class Heightmap {
    private int minvalue;
    private int maxvalue;
    private int[][] heightMap;
    private Mappable generator;
    private int size;

    public Heightmap(int MINVALUE, int MAXVALUE, int size, Mappable generator) {
        minvalue = MINVALUE;
        maxvalue = MAXVALUE;
        this.size = size;
        this.generator = generator;
    }

    public Mappable getGenerator() {
        return generator;
    }

    public void setGenerator(Mappable generator) {
        this.generator = generator;
    }

    public void create(){
        final int D = maxvalue-minvalue;

        heightMap = new int[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                heightMap[i][j]= minvalue + (int)(generator.generate(i,j)*D);
            }
        }
    }
    public void forEach(Func<MapArgs> func){
        for (int i = 0; i < heightMap.length; i++) {
            for (int j = 0; j < heightMap.length; j++) {
                func.invoke(new MapArgs(i, j, heightMap[i][j]));
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

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
