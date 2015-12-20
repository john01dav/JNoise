package src.john01dav.jnoise.templates.perlin;

public class DefaultPerlinNoiseHeightController implements PerlinNoiseHeightController{

    @Override
    public void init(int mapSize){}

    @Override
    public double getHeightMultiplier(int x, int y){
        return 1;
    }

    @Override
    public double getHeightAdder(int x, int y){
        return 0;
    }

}
