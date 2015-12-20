package src.john01dav.jnoise.templates.perlin;

import src.john01dav.jnoise.util.Coordinates;
import src.john01dav.jnoise.util.MathUtil;

public class FalloffPerlinNoiseController implements PerlinNoiseHeightController{
    private int paddingSize;
    private int mapSize;

    public FalloffPerlinNoiseController(int paddingSize){
        this.paddingSize = paddingSize;
    }

    @Override
    public void init(int mapSize){
        this.mapSize = mapSize;
    }

    @Override
    public double getHeightMultiplier(int x, int y, int frequency){
        if(x < 0 || x >= mapSize || y < 0 || y >= mapSize || distanceToEdge(x, y) < paddingSize){
            return 0;
        }else{
            return 1;
        }
    }

    @Override
    public double getHeightAdder(int x, int y, int frequency){
        return 1;
    }

    private int distanceToEdge(int x, int y){
        return MathUtil.least(
                x,
                y,
                mapSize - x,
                mapSize - y
        );
    }

}
