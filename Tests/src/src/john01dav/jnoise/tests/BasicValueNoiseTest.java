package src.john01dav.jnoise.tests;

import src.john01dav.jnoise.HeightMap;
import src.john01dav.jnoise.templates.ValueNoiseTemplate;

import java.io.IOException;

public class BasicValueNoiseTest{

    public static void main(String[] args) throws IOException{
        HeightMap heightMap = new HeightMap(512, new ValueNoiseTemplate(0L, 1, 4));
        heightMap.generate();
        heightMap.saveImage("./valuenoise.png");
    }

}
