package co.edu.unal.ce.codeAC;

public class Shuffle {
    public static Integer[] apply(int n ){
        Integer[] a = new Integer[n];
        for( int i=0; i<n; i++ ){
            a[i] = i;
        }
        for( int k=0; k<2*n; k++ ){
            int i = (int)(n*Math.random());
            int j = (int)(n*Math.random());
            int t = a[i];
            a[i] = a[j];
            a[j] = t;
            /*
            a[i] = a[i] ^ a[j];
            a[j] = a[i] ^ a[j];
            a[i] = a[i] ^ a[j];
             */
        }
        return a;
    }
}
