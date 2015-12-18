package src.john01dav.jnoise.templates;

import src.john01dav.jnoise.HeightMap;
import src.john01dav.jnoise.NoiseTemplate;

import java.util.Random;

public class ParticleDepositionTemplate extends NoiseTemplate{
    private Random random;
    private int particleCount;

    public ParticleDepositionTemplate(long seed, int particleCount){
        this(new Random(seed), particleCount);
    }

    public ParticleDepositionTemplate(Random random, int particleCount){
        this.random = random;
        this.particleCount = particleCount;
    }

    @Override
    public void generate(HeightMap heightMap, int size){
        int cx, cy;

        cx = random.nextInt(size);
        cy = random.nextInt(size);

        for(int i=0;i<particleCount;i++){
            cx += random.nextInt(3) - 1;
            cy += random.nextInt(3) - 1;

            if(cx < 0) cx += 512;
            if(cy < 0) cy += 512;

            cx %= size;
            cy %= size;

            heightMap.setHeightData(cx, cy, heightMap.getHeightAt(cx, cy) + 1);
        }
    }

}
