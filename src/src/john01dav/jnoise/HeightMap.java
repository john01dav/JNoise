package src.john01dav.jnoise;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class HeightMap{
    private int size;
    private NoiseTemplate template;
    private NoiseFilter[] filters;

    private boolean isNormalized;
    private double[][] heightData;
    private double[][] workingHeightData;

    public HeightMap(int size, NoiseTemplate template){
        this(size, template, new NoiseFilter[0]);
    }

    public HeightMap(int size, NoiseTemplate template, NoiseFilter... filters){
        this.size = size;
        this.template = template;
        this.filters = filters;

        heightData = new double[size][size];
    }

    public void generate(){
        template.generate(this, size);

        for(NoiseFilter filter : filters){
            if(filter.requiresPreNormalization()) normalize();
            filter.filter(this, size);
            if(filter.requiresPostNormalization()) normalize();
        }

        normalize();
    }

    public void normalize(){
        if(isNormalized) return;

        int cx, cy;
        double least = 0, greatest = 0;
        double multiplyBy;

        for(cx=0;cx<size;cx++){
            for(cy=0;cy<size;cy++){
                if(heightData[cx][cy] > greatest) greatest = heightData[cx][cy];
                if(heightData[cx][cy] < least) least = heightData[cx][cy];
            }
        }

        multiplyBy = 1 / (greatest - least);

        for(cx=0;cx<size;cx++){
            for(cy=0;cy<size;cy++){
                heightData[cx][cy] -= least;
                heightData[cx][cy] *= multiplyBy;
            }
        }

        isNormalized = true;
    }

    public double getHeightAt(int x, int y){
        return heightData[x][y];
    }

    public void setHeightData(int x, int y, double data){
        isNormalized = false;
        heightData[x][y] = data;
    }

    public void setupWorkingHeightData(){
        workingHeightData = new double[size][size];
    }

    public void swapWorkingHeightData(){
        double[][] swapBuffer;

        swapBuffer = workingHeightData;
        workingHeightData = heightData;
        heightData = swapBuffer;
    }

    public void disposeWorkingHeightData(){
        workingHeightData = null;
    }

    public double getWorkingHeightAt(int x, int y){
        return workingHeightData[x][y];
    }

    public void setWorkingHeightData(int x, int y, double data){
        workingHeightData[x][y] = data;
    }

    public BufferedImage makeHeightMapImage(){
        BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_BYTE_GRAY);
        int cx, cy;
        float value;

        for(cx=0;cx<size;cx++){
            for(cy=0;cy<size;cy++){
                value = (float) getHeightAt(cx, cy);
                image.setRGB(cx, cy, new Color(value, value, value).getRGB());
            }
        }

        return image;
    }

    public void saveImage(File file) throws IOException{
        ImageIO.write(makeHeightMapImage(), "png", file);
    }

    public void saveImage(String name) throws IOException{
        saveImage(new File(name));
    }

}
