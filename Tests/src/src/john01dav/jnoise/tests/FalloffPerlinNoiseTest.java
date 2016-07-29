package src.john01dav.jnoise.tests;

import src.john01dav.jnoise.HeightMap;
import src.john01dav.jnoise.templates.perlin.ComplexIslandPerlinNoiseController;
import src.john01dav.jnoise.templates.perlin.FalloffPerlinNoiseController;
import src.john01dav.jnoise.templates.perlin.PerlinNoiseTemplate;

import java.io.IOException;

public class FalloffPerlinNoiseTest{

    public static void main(String[] args) throws IOException{
        long start = System.currentTimeMillis();
        HeightMap heightMap = new HeightMap(2048, new PerlinNoiseTemplate(0L, 9, 2, new FalloffPerlinNoiseController()));
        heightMap.generate();
        System.out.println("Generation Time: " + (System.currentTimeMillis() - start));

        heightMap.saveImage("./falloffislandperlinnoise.png");
    }

}
