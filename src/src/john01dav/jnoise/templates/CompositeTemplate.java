package src.john01dav.jnoise.templates;

import src.john01dav.jnoise.HeightMap;
import src.john01dav.jnoise.NoiseTemplate;

public class CompositeTemplate extends NoiseTemplate{
    private CompositeLayer[] templates;

    public CompositeTemplate(CompositeLayer... templates){
        this.templates = templates;
    }

    @Override
    public void generate(HeightMap heightMap, int size){
        HeightMap bufferHeightMap;
        int cx, cy;

        for(cx=0;cx<size;cx++){
            for(cy=0;cy<size;cy++){
                heightMap.setHeightData(cx, cy, 0);
            }
        }

        for(int i=0;i<templates.length;i++){
            bufferHeightMap = new HeightMap(size, templates[i].getTemplate());
            bufferHeightMap.generate();

            for(cx=0;cx<size;cx++){
                for(cy=0;cy<size;cy++){
                    heightMap.setHeightData(cx, cy, heightMap.getHeightAt(cx, cy) + bufferHeightMap.getHeightAt(cx, cy) * templates[i].getScale());
                }
            }
        }
    }

    public static class CompositeLayer{
        private NoiseTemplate template;
        private double scale;

        public CompositeLayer(NoiseTemplate template, double scale){
            this.template = template;
            this.scale = scale;
        }

        public NoiseTemplate getTemplate(){
            return template;
        }

        public double getScale(){
            return scale;
        }

    }

}
