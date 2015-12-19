package src.john01dav.jnoise.util;

public final class MathUtil{

    private MathUtil(){}

    public static boolean isPrime(int n){
        for(int i=2;i<n;i++){
            if(n % i == 0){
                return false;
            }
        }

        return true;
    }

    public static int getNextPrime(int n){
        while(!isPrime(n)) n++;
        return n;
    }

    public static int roundDown(int n, int m){
        while(n % m != 0) n--;
        return n;
    }

}
