package src.john01dav.jnoise.tests;

import src.john01dav.jnoise.HeightMap;
import src.john01dav.jnoise.filters.BasicIslandFilter;
import src.john01dav.jnoise.templates.PerlinNoiseTemplate;

import java.io.IOException;

public class IslandPerlinNoiseTest{

    public static void main(String[] args) throws IOException{
        HeightMap heightMap = new HeightMap(512, new PerlinNoiseTemplate(2L, 9, 2), new BasicIslandFilter(245));
        heightMap.generate();
        heightMap.saveImage("./islandperlinnoise.png");
    }

}
