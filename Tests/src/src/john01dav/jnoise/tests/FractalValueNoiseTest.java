package src.john01dav.jnoise.tests;

import src.john01dav.jnoise.HeightMap;
import src.john01dav.jnoise.templates.FractalValueNoiseTemplate;

import java.io.IOException;

public class FractalValueNoiseTest{

    public static void main(String[] args) throws IOException{
        HeightMap heightMap = new HeightMap(512, FractalValueNoiseTemplate.construct(0L, 4, 12, .75));
        heightMap.generate();
        heightMap.saveImage("./fractalvaluenoise.png");
    }

}
