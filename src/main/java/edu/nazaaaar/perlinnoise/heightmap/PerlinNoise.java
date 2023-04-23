package edu.nazaaaar.perlinnoise.heightmap;

import edu.nazaaaar.perlinnoise.heightmap.Mappable;
import utils.Vector2D;

import java.util.Random;

public class PerlinNoise implements Mappable {

    final static double minVal = -Math.sqrt(0.5);
    final static double maxVal = Math.sqrt(0.5);

    int[] permutation;

    private void makePermutation(){
        permutation = new int[512];
        for (int i = 0; i < permutation.length/2; i++) {
            permutation[i]=i;
        }
        Random random = new Random(seed);
        for (int e = permutation.length/2-1; e > 0; e--) {
            int index = random.nextInt(e);
            int temp = permutation[e];

            permutation[e] = permutation[index];
            permutation[index]=temp;
        }
        for (int i = permutation.length/2; i <permutation.length; i++) {
            permutation[i]=permutation[i-permutation.length/2];
        }
    }

    public PerlinNoise(long seed) {
        this.seed = seed;
        makePermutation();
    }

    public long getSeed() {
        return seed;
    }

    public void setSeedMakePermutation(long seed) {
        this.seed = seed;
        makePermutation();
    }

    private long seed;
    double fade(double t) { return t * t * t * (t * (t * 6 - 15) + 10); }
    double lerp(double t, double a, double b) {return a + t * (b - a); }

    Vector2D grad(int v) {
        //v is the value from the permutation table

        switch (v&3){
            case 0 -> {return new Vector2D(1,1);}
            case 1 -> {return new Vector2D(-1,1);}
            case 2 -> {return new Vector2D(-1,-1);}
            case 3 -> {return new Vector2D(1,-1);}
            default -> {return null;} //never happens
        }
    }

    public PerlinNoise(){
        Random random = new Random();
        seed = random.nextLong(Long.MAX_VALUE);

        makePermutation();
    }

    @Override
    public double generate(double x, double y) {
        record Corners<T>(T topRight,T topLeft,T bottomRight,T bottomLeft){ }

        int
                X = (int)Math.floor(x) & 255,
                Y = (int)Math.floor(y) & 255;

        x -= Math.floor(x);
        y -= Math.floor(y);

        var distances = new Corners<>(
                new Vector2D(x-1,y-1),
                new Vector2D(x,y-1),
                new Vector2D(x-1,y),
                new Vector2D(x,y));

        var gradients = new Corners<>(
                grad(permutation[permutation[X+1]+Y+1]),
                grad(permutation[permutation[X]+Y+1]),
                grad(permutation[permutation[X+1]+Y]),
                grad(permutation[permutation[X]+Y]));

        var dotProducts = new Corners<>(
                distances.topRight.dot(gradients.topRight),
                distances.topLeft.dot(gradients.topLeft),
                distances.bottomRight.dot(gradients.bottomRight),
                distances.bottomLeft.dot(gradients.bottomLeft)
        );

        double
                u = fade(x),
                v = fade(y);

        return  0.5 * (lerp(u,
                lerp(v, dotProducts.bottomLeft,dotProducts.topLeft),
                lerp(v,dotProducts.bottomRight,dotProducts.topRight))/ Math.sqrt(0.5d)
                + 1.0);
    }

}
