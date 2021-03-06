package src.john01dav.jnoise.templates.perlin;

import src.john01dav.jnoise.templates.AtomicTemplate;
import src.john01dav.jnoise.util.MathUtil;
import src.john01dav.jnoise.util.NoiseFunction;
import src.john01dav.jnoise.util.NoiseFunctionGenerationCallable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class PerlinNoiseTemplate extends AtomicTemplate{
    private final long seed;
    private final int octaves;
    private final int octaveIterativeFraction;
    private final PerlinNoiseHeightController heightController;
    private volatile int startingOctave;
    private volatile NoiseFunction[] noiseFunctions;

    public PerlinNoiseTemplate(long seed, int octaves, int octaveIterativeFraction, PerlinNoiseHeightController heightController){
        this.seed = seed;
        this.octaves = octaves;
        this.octaveIterativeFraction = octaveIterativeFraction;
        this.heightController = heightController;
    }

    @Override
    public void initTemplate(int size){
        startingOctave = (int) Math.pow(octaveIterativeFraction, octaves - 1);

        heightController.init(size);

        try{
            ThreadPoolExecutor executor = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(), Runtime.getRuntime().availableProcessors(), 1, TimeUnit.SECONDS, new ArrayBlockingQueue<>(octaves));
            ArrayList<NoiseFunctionGenerationCallable> callableArrayList = new ArrayList<>(octaves);
            List<Future<NoiseFunction>> taskResults;
            int i;

            for(i=0;i<octaves;i++){
                callableArrayList.add(i, new NoiseFunctionGenerationCallable(seed * i));
            }

            taskResults = executor.invokeAll(callableArrayList);
            executor.shutdown();

            noiseFunctions = new NoiseFunction[octaves];

            for(i=0;i<octaves;i++){
                noiseFunctions[i] = taskResults.get(i).get();
            }

        }catch(InterruptedException | ExecutionException e){
            e.printStackTrace();
        }
    }

    @Override
    public double calculate(int x, int y, int size){
        double total = 0;
        int n = 0;

        for(int octave=startingOctave;octave>0;octave/=octaveIterativeFraction){
            total += getOctaveAt(x, y, octave, n++) * octave;
        }

        return total;
    }

    private double getOctaveAt(double x, double y, int frequency, int randomNumberFunctionID){
        int intX = MathUtil.roundDown((int) Math.floor(x), frequency);
        double fractionalX = (x - intX) / ((double) frequency);
        int intY = MathUtil.roundDown((int) Math.floor(y), frequency);
        double fractionalY = (y - intY) / ((double) frequency);

        int doubleFrequency = frequency * 2;

        double v1 = noise(intX - frequency, intY - frequency, randomNumberFunctionID, frequency);
        double v2 = noise(intX, intY - frequency, randomNumberFunctionID, frequency);
        double v3 = noise(intX + frequency, intY - frequency, randomNumberFunctionID, frequency);
        double v4 = noise(intX + doubleFrequency, intY - frequency, randomNumberFunctionID, frequency);

        double v5 = noise(intX - frequency, intY, randomNumberFunctionID, frequency);
        double v6 = noise(intX, intY, randomNumberFunctionID, frequency);
        double v7 = noise(intX + frequency, intY, randomNumberFunctionID, frequency);
        double v8 = noise(intX + doubleFrequency, intY, randomNumberFunctionID, frequency);

        double v9 = noise(intX - frequency, intY + frequency, randomNumberFunctionID, frequency);
        double v10 = noise(intX, intY + frequency, randomNumberFunctionID, frequency);
        double v11 = noise(intX + frequency, intY + frequency, randomNumberFunctionID, frequency);
        double v12 = noise(intX + doubleFrequency, intY + frequency, randomNumberFunctionID, frequency);

        double v13 = noise(intX - frequency, intY + doubleFrequency, randomNumberFunctionID, frequency);
        double v14 = noise(intX, intY + doubleFrequency, randomNumberFunctionID, frequency);
        double v15 = noise(intX + frequency, intY + doubleFrequency, randomNumberFunctionID, frequency);
        double v16 = noise(intX + doubleFrequency, intY + doubleFrequency, randomNumberFunctionID, frequency);

        double i1 = interpolate(v1, v2, v3, v4, fractionalX);
        double i2 = interpolate(v5, v6, v7, v8, fractionalX);
        double i3 = interpolate(v9, v10, v11, v12, fractionalX);
        double i4 = interpolate(v13, v14, v15, v16, fractionalX);

        return interpolate(i1, i2, i3, i4, fractionalY);
    }

    private double interpolate(double v0, double v1, double v2, double v3, double x){
        double p = (v3 - v3) - (v0 - v1);
        double q = (v0 - v1) - p;
        double r = v2 - v0;

        return p * Math.pow(x, 3) + q * Math.pow(x, 2) + r * x + v1;
    }

    private double noise(int x, int y, int randomNumberFunctionID, int frequency){
        return (noiseFunctions[randomNumberFunctionID].noise(x, y) + heightController.getHeightAdder(x, y, frequency)) * heightController.getHeightMultiplier(x, y, frequency);
    }

}
