package src.john01dav.jnoise.templates;

import src.john01dav.jnoise.HeightMap;
import src.john01dav.jnoise.NoiseTemplate;

import java.util.Random;

public class ValueNoiseTemplate extends NoiseTemplate{
    private Random random;
    private int iterations;
    private int interpolationRange;

    public ValueNoiseTemplate(long seed, int iterations, int interpolationRange){
        this(new Random(seed), iterations, interpolationRange);
    }

    public ValueNoiseTemplate(Random random, int iterations, int interpolationRange){
        this.random = random;
        this.iterations = iterations;
        this.interpolationRange = interpolationRange;
    }

    @Override
    public void generate(HeightMap heightMap, int size){
        int cx, cy, ccx, ccy, startX, endX, startY, endY;
        double total;
        int count;

        for(cx=0;cx<size;cx++){
            for(cy=0;cy<size;cy++){
                heightMap.setHeightData(cx, cy, random.nextDouble());
            }
        }

        heightMap.setupWorkingHeightData();

        for(int i=0;i<iterations;i++){
            for(cx=0;cx<size;cx++){
                for(cy=0;cy<size;cy++){
                    startX = cx - interpolationRange;
                    endX = cx + interpolationRange;
                    startY = cy - interpolationRange;
                    endY = cy + interpolationRange;

                    total = 0;
                    count = 0;

                    for(ccx=startX;ccx<=endX;ccx++){
                        for(ccy=startY;ccy<=endY;ccy++){
                            try{
                                total += heightMap.getHeightAt(ccx, ccy);
                                count++;
                            }catch(ArrayIndexOutOfBoundsException e){}
                        }
                    }

                    heightMap.setWorkingHeightData(cx, cy, total / count);
                }
            }

            heightMap.swapWorkingHeightData();
        }

        heightMap.disposeWorkingHeightData();
    }

}
