package src.john01dav.jnoise.templates;

public abstract class AtomicTemplate extends ThreadedTemplate{

    @Override
    public double[] calculateColumn(int cx, int size){
        double[] row = new double[size];

        for(int cy=0;cy<size;cy++){
            row[cy] = calculate(cx, cy, size);
        }

        return row;
    }

    public abstract double calculate(int x, int y, int size);

}
