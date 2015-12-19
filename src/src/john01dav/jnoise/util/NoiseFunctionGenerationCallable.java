package src.john01dav.jnoise.util;

import java.util.Random;
import java.util.concurrent.Callable;

public final class NoiseFunctionGenerationCallable implements Callable<NoiseFunction>{
    private final long seed;

    public NoiseFunctionGenerationCallable(long seed){
        this.seed = seed;
    }

    @Override
    public NoiseFunction call() throws Exception{
        return new NoiseFunction(new Random(seed));
    }

}
