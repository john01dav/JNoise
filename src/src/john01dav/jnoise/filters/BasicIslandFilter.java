package src.john01dav.jnoise.filters;

import src.john01dav.jnoise.HeightMap;
import src.john01dav.jnoise.NoiseFilter;
import src.john01dav.jnoise.util.MathUtil;

public class BasicIslandFilter extends NoiseFilter{
    private int radius;

    public BasicIslandFilter(int radius){
        this.radius = radius;
    }

    @Override
    public boolean requiresPreNormalization(){
        return false;
    }

    @Override
    public void filter(HeightMap heightMap, int size){
        int cx, cy;
        double distance;
        double multiplier;
        double radiusSquared = MathUtil.square(radius);
        int halfSize = size / 2;

        for(cx=0;cx<size;cx++){
            for(cy=0;cy<size;cy++){
                distance = MathUtil.distance(cx, cy, halfSize, halfSize);

                if(distance > radius){
                    multiplier = 0;
                }else{
                    multiplier = 1.0 - MathUtil.max(distance / radius, 1);
                }

                heightMap.setHeightData(cx, cy, MathUtil.min(heightMap.getHeightAt(cx, cy) * multiplier, 1));
            }
        }
    }

    @Override
    public boolean requiresPostNormalization(){
        return false;
    }

}
