package src.john01dav.jnoise.util;

import java.util.Random;

public final class NoiseFunction{
    private final int a, b, c;
    private final double d;

    public NoiseFunction(Random random){
        this.a = MathUtil.getNextPrime(random.nextInt(10000) + 10000);
        this.b = MathUtil.getNextPrime(random.nextInt(100000) + 700000);
        this.c = MathUtil.getNextPrime(random.nextInt(100000000) + 1000000000);
        this.d = MathUtil.getNextPrime(random.nextInt(100000000) + 1000000000);
    }

    public NoiseFunction(int a, int b, int c, double d){
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }

    public int noise(int x, int y){
        return noise(x + y * 57);
    }

    public int noise(int x){
        x = (x << 13) ^ x;
        return (int) Math.round(1.0 - ((x * (x * x * a + b) + c) & 0x7fffffff) / d);
    }

}
