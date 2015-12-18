package src.john01dav.jnoise.templates;

import java.util.Random;

public class FractalValueNoiseTemplate extends CompositeTemplate{

    public static FractalValueNoiseTemplate construct(long seed, int iterations, int octaveCount, double persistence){
        return construct(new Random(seed), iterations, octaveCount, persistence);
    }

    public static FractalValueNoiseTemplate construct(Random random, int iterations, int octaveCount, double persistence){
        CompositeLayer[] compositeLayers = new CompositeLayer[octaveCount];

        for(int i=0;i<octaveCount;i++){
            compositeLayers[i] = new CompositeLayer(
                new ValueNoiseTemplate(random, iterations, i),
                Math.pow(persistence, octaveCount - i)
            );
        }

        return new FractalValueNoiseTemplate(compositeLayers);
    }

    private FractalValueNoiseTemplate(CompositeLayer... layers){
        super(layers);
    }

}
