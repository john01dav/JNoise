package src.john01dav.jnoise.templates;

public class GradientTemplate extends AtomicTemplate{

    @Override
    public void initTemplate(){} //not used

    @Override
    public double calculate(int x, int y, int size){
        return x * y;
    }

}
