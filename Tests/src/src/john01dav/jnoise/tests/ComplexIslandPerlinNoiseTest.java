package src.john01dav.jnoise.tests;

import src.john01dav.jnoise.HeightMap;
import src.john01dav.jnoise.templates.perlin.ComplexIslandPerlinNoiseController;
import src.john01dav.jnoise.templates.perlin.PerlinNoiseTemplate;

import java.io.IOException;

public class ComplexIslandPerlinNoiseTest{

    public static void main(String[] args) throws IOException{
        HeightMap heightMap = new HeightMap(4096, new PerlinNoiseTemplate(512L, 9, 2, new ComplexIslandPerlinNoiseController(512, 512L)));
        heightMap.generate();
        heightMap.saveImage("./complexislandperlinnoise.png");
    }

}
