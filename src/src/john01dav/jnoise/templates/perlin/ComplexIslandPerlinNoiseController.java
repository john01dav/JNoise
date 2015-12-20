package src.john01dav.jnoise.templates.perlin;

import src.john01dav.jnoise.util.Coordinates;
import src.john01dav.jnoise.util.MathUtil;

import java.util.Random;

public class ComplexIslandPerlinNoiseController implements PerlinNoiseHeightController{
    private int minIslandRadius;
    private long seed;
    private IslandNode[] islandNodes;

    public ComplexIslandPerlinNoiseController(int minIslandRadius, long seed){
        this.minIslandRadius = minIslandRadius;
        this.seed = seed;
    }

    @Override
    public void init(int mapSize){
        Random random = new Random(seed);
        int islandCount = random.nextInt(9) + 8;

        if(!(minIslandRadius < mapSize)){
            throw new IllegalStateException("The minimum island radius (" + minIslandRadius + ") must be less than the map size (" + mapSize + ")");
        }

        islandNodes = new IslandNode[islandCount];

        for(int i=0;i<islandCount;i++){
            Coordinates location = new Coordinates(random.nextInt(mapSize), random.nextInt(mapSize));
            int maxRadius = distanceToEdge(location, mapSize);
            int radius;

            if(maxRadius < minIslandRadius){
                i--;
                continue;
            }

            radius = random.nextInt(maxRadius - minIslandRadius) + minIslandRadius;

            islandNodes[i] = new IslandNode(location, radius);
        }
    }

    @Override
    public double getHeightMultiplier(int x, int y, int frequency){
        double multiplier = 0;

        for(IslandNode islandNode : islandNodes){
            if(islandNode.getRadius() <= multiplier) continue;

            if(islandNode.distanceSquared(x, y) < MathUtil.square(islandNode.getRadius())){
                return 1;
                //multiplier = islandNode.getRadius();
            }
        }

        return 0;

        //multiplier = MathUtil.min(multiplier, 0);

        //return multiplier;
    }

    @Override
    public double getHeightAdder(int x, int y, int frequency){
        return 1;
    }

    private int distanceToEdge(Coordinates coordinates, int mapSize){
        return MathUtil.least(
                coordinates.getX(),
                coordinates.getY(),
                mapSize - coordinates.getX(),
                mapSize - coordinates.getY()
        );
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
