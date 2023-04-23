package edu.nazaaaar.perlinnoise.heightmap;

import edu.nazaaaar.perlinnoise.heightmap.PerlinNoise;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PerlinNoiseTest {

    @Test
    void PerlinDefaultConstructor(){
        PerlinNoise perlinNoise = new PerlinNoise();

        int[] p = perlinNoise.permutation;

        for (int i = 0; i < p.length/2; i++) {
            if (p[i]!=p[i+p.length/2]){fail();}
        }

        //System.out.println("Seed: "+ perlinNoise.getSeed());
        assertNotEquals(0,perlinNoise.getSeed());
    }

    @Test
    void PerlinSeedConstructor() {
        int seed = 25062004;
        PerlinNoise perlinNoise = new PerlinNoise(25062004);

        int[] p = perlinNoise.permutation;

        for (int i = 0; i < p.length / 2; i++) {
            if (p[i] != p[i + p.length / 2]) {
                fail();
            }
        }

        PerlinNoise perlinNoise2 = new PerlinNoise(25062004);

        assertAll(
                () -> assertEquals(seed, perlinNoise.getSeed()),
                () -> assertArrayEquals(perlinNoise.permutation, perlinNoise2.permutation)
        );
    }

    @Test
    void generate(){
        int seed = 25062004;
        PerlinNoise perlinNoise = new PerlinNoise(25062004);
        System.out.println(perlinNoise.generate(1,1));
    }
    @Test
    void generateLarge(){
        int seed = 25062004;
        PerlinNoise perlinNoise = new PerlinNoise(25062004);

        for (int i = 0; i < 100; i++) {
            System.out.println(perlinNoise.generate(i,i));
        }

    }
}