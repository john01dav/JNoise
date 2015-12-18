package src.john01dav.jnoise.tests;

import src.john01dav.jnoise.HeightMap;
import src.john01dav.jnoise.templates.ParticleDepositionTemplate;

import java.io.IOException;

public class ParticleDepositionTest{

    public static void main(String[] args) throws IOException{
        HeightMap heightMap = new HeightMap(512, new ParticleDepositionTemplate(0L, 100000000));
        heightMap.generate();
        heightMap.saveImage("./particledepositionnoise.png");
    }

}
