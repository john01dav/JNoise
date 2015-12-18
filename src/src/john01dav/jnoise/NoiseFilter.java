package src.john01dav.jnoise;

public abstract class NoiseFilter{

    public abstract boolean requiresPreNormalization();
    public abstract void filter(HeightMap heightMap, int size);
    public abstract boolean requiresPostNormalization();

}
