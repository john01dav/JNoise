package src.john01dav.jnoise.tests;

import src.john01dav.jnoise.HeightMap;
import src.john01dav.jnoise.templates.PerlinNoiseTemplate;

import java.io.IOException;

public class PerlinNoiseTest{

    public static void main(String[] args) throws IOException{
        HeightMap heightMap = new HeightMap(4096, new PerlinNoiseTemplate(0L, 9, 2));
        heightMap.generate();
        heightMap.saveImage("./perlinnoise.png");
    }

}
