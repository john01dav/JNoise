package src.john01dav.jnoise.tests;

import src.john01dav.jnoise.HeightMap;
import src.john01dav.jnoise.templates.GradientTemplate;

import java.io.IOException;

public class GradientHeightMapTest{

    public static void main(String[] args) throws IOException{
        HeightMap heightMap = new HeightMap(512, new GradientTemplate());
        heightMap.generate();
        heightMap.saveImage("./gradient.png");
    }

}
