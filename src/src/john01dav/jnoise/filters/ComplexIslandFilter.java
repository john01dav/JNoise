package src.john01dav.jnoise.filters;

import src.john01dav.jnoise.HeightMap;
import src.john01dav.jnoise.NoiseFilter;
import src.john01dav.jnoise.util.Coordinates;
import src.john01dav.jnoise.util.MathUtil;

import java.util.Random;

public class ComplexIslandFilter extends NoiseFilter{
    private IslandNode[] nodes;

    public ComplexIslandFilter(long seed, int mapSize){
        this(new Random(seed), mapSize);
    }

    public ComplexIslandFilter(Random random, int mapSize){
        this(random, random.nextInt(4) + 3, 64, mapSize);
    }

    public ComplexIslandFilter(Random random, int nodeCount, int islandSizeRange, int mapSize){
        nodes = new IslandNode[nodeCount];

        for(int i=0;i<nodeCount;i++){
            Coordinates location = new Coordinates(random.nextInt(mapSize), random.nextInt(mapSize));
            int maxRadius = distanceToEdge(location, mapSize);
            int radius;

            if(maxRadius < 64){
                i--;
                continue;
            }

            radius = random.nextInt(islandSizeRange) + (maxRadius - islandSizeRange);

            nodes[i] = new IslandNode(location, radius);
        }

    }

    private int distanceToEdge(Coordinates coordinates, int mapSize){
        return MathUtil.least(
                coordinates.getX(),
                coordinates.getY(),
                mapSize - coordinates.getX(),
                mapSize - coordinates.getY()
        );
    }

    public ComplexIslandFilter(IslandNode... nodes){
        this.nodes = nodes;
    }

    @Override
    public boolean requiresPreNormalization(){
        return false;
    }

    @Override
    public void filter(HeightMap heightMap, int size){
        int cx, cy, i, ci;
        IslandNode[] currentNodes = new IslandNode[nodes.length];
        int[] distanceSquared = new int[nodes.length];
        int currentDistanceSquared;

        double averageDistance;
        double averageRadius;

        double multiplier;

        for(cx=0;cx<size;cx++){
            for(cy=0;cy<size;cy++){
                i = 0;

                for(IslandNode node : nodes){
                    currentDistanceSquared = node.distanceSquared(cx, cy);

                    if(currentDistanceSquared < node.getRadius()){
                        ci = i++;

                        currentNodes[ci] = node;
                        distanceSquared[ci] = currentDistanceSquared;
                    }
                }

                if(i > 0){
                    averageDistance = 0;
                    averageRadius = 0;

                    for(ci=0;ci<i;ci++){
                        averageDistance += distanceSquared[ci];
                        averageRadius += currentNodes[ci].getRadius();
                    }

                    averageDistance /= i;
                    averageRadius /= i;

                    multiplier = 1.0 - MathUtil.max(averageDistance / averageRadius, 1);
                }else{
                    multiplier = 0;
                }

                heightMap.setHeightData(cx, cy, MathUtil.min(heightMap.getHeightAt(cx, cy) * multiplier, 1));
            }
        }
    }

    @Override
    public boolean requiresPostNormalization(){
        return false;
    }

    public static final class IslandNode{
        private final Coordinates location;
        private final int radius;

        public IslandNode(Coordinates location, int radius){
            this.location = location;
            this.radius = radius;
        }

        public Coordinates getLocation(){
            return location;
        }

        public int getRadius(){
            return radius;
        }

        public int distanceSquared(int x, int y){
            return MathUtil.distanceSquared(getLocation().getX(), getLocation().getY(), x, y);
        }

    }

}
