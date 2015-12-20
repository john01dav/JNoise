package src.john01dav.jnoise.templates.perlin;

public interface PerlinNoiseHeightController{

    void init(int mapSize);
    double getHeightMultiplier(int x, int y, int frequency);
    double getHeightAdder(int x, int y, int frequency);

}
