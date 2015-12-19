package src.john01dav.jnoise.templates;

import src.john01dav.jnoise.HeightMap;
import src.john01dav.jnoise.NoiseTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public abstract class ThreadedTemplate extends NoiseTemplate{

    @Override
    public void generate(HeightMap heightMap, int size){
        initTemplate();

        try{
            ThreadPoolExecutor executor = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(), Runtime.getRuntime().availableProcessors(), 1, TimeUnit.SECONDS, new ArrayBlockingQueue<>(size));
            ArrayList<RowCallable> callableArrayList = new ArrayList<>(size);
            List<Future<double[]>> taskResults;
            int cx, cy;

            for(cx=0;cx<size;cx++){
                callableArrayList.add(cx, new RowCallable(cx, size));
            }

            taskResults = executor.invokeAll(callableArrayList);
            executor.shutdown();

            for(cx=0;cx<size;cx++){
                double[] row = taskResults.get(cx).get();

                for(cy=0;cy<size;cy++){
                    heightMap.setHeightData(cx, cy, row[cy]);
                }
            }

        }catch(InterruptedException | ExecutionException e){
            e.printStackTrace();
        }
    }

    private class RowCallable implements Callable<double[]>{
        private int column, size;

        public RowCallable(int column, int size){
            this.column = column;
            this.size = size;
        }

        @Override
        public double[] call() throws Exception{
            return calculateColumn(column, size);
        }
    }

    public abstract void initTemplate();

    public abstract double[] calculateColumn(int column, int size);

}
