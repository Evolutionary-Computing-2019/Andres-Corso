package co.edu.unal.ce.codeAC;

public class bORRAR {

    // return Kendall tau distance between two permutations
    public static long distance(Integer[] a, Integer[] b) {
        if (a.length != b.length) {
            throw new IllegalArgumentException("Array dimensions disagree");
        }
        int n = a.length;

        int[] ainv = new int[n];
        for (int i = 0; i < n; i++)
            ainv[a[i]] = i;

        Integer[] bnew = new Integer[n];
        for (int i = 0; i < n; i++)
            bnew[i] = ainv[b[i]];

        return Inversions.count(bnew);
    }


    // return a random permutation of size n
    public static Integer[] permutation(int n) {

        return Shuffle.apply(n);
    }


    public static void main(String[] args) {

        // two random permutation of size n
        int n = 10;
        Integer[] a = bORRAR.permutation(n);
        Integer[] b = bORRAR.permutation(n);


        // print initial permutation
        for (int i = 0; i < n; i++)
            System.out.println(a[i]);
        for (int i = 0; i < n; i++)
            System.out.println(b[i]);
        System.out.println();

        System.out.println("inversions = " + bORRAR.distance(a, b));
    }
}

