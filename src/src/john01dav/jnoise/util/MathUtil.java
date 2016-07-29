package src.john01dav.jnoise.util;

public final class MathUtil{

    private MathUtil(){}

    public static boolean isPrime(int n){
        int sqrtOfN = (int) Math.ceil(Math.sqrt(n));

        for(int i=2;i<sqrtOfN;i++){
            if(n % i == 0){
                return false;
            }
        }

        return true;
    }

    public static int getNextPrime(int n){
        if(n % 2 == 0) n++;

        while(!isPrime(n)) n+=2;
        return n;
    }

    public static int roundDown(int n, int m){
        while(n % m != 0) n--;
        return n;
    }

    public static int distanceSquared(int xa, int ya, int xb, int yb){
        return square(xa - xb) + square(ya - yb);
    }

    public static int square(int i){
        return i * i;
    }

    public static double distance(int xa, int ya, int xb, int yb){
        return Math.sqrt(distanceSquared(xa, ya, xb, yb));
    }

    public static double min(double i, double m){
        return i < m ? m : i;
    }

    public static double max(double i, double m){
        return i > m ? m : i;
    }

    public static int least(int... ints){
        int least = ints[0];

        for(int i=1;i<ints.length;i++){
            if(ints[i] < least) least = ints[i];
        }

        return least;
    }

}
