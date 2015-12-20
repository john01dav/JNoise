package src.john01dav.jnoise.templates;

public class GradientTemplate extends AtomicTemplate{

    @Override
    public void initTemplate(int size){} //not used

    @Override
    public double calculate(int x, int y, int size){
        return x * y;
    }

}
