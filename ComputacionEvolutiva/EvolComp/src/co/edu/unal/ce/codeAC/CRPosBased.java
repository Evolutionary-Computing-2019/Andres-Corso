package co.edu.unal.ce.codeAC;

public class CRPosBased implements Crossover<Integer[]> {
    private int m;
    public CRPosBased(int n){
        this.m = n;
    }

    @Override
    public Integer[][] apply(Integer[][] parents) {
        int n = parents[0].length;
        Integer[] ic0 = new Integer[m];
        Integer[] ic1 = new Integer[m];
        Integer[][] children = new Integer[2][n];

        int i0;
        for (int k = 0; k < m; k++){
            do{
                i0 = (int) (Math.random() * n - 1);
            }while (inArray(ic0,i0));
            children[0][i0] = parents[0][i0];
            do{
                i0 = (int) (Math.random() * n - 1);
            }while (inArray(ic1,i0));
            children[1][i0] = parents[1][i0];
        }
        int j = 0;
        for (int k = 0; k < n; k++) {
            if (!( inArray(ic0,k))) {
                while (inArray(children[0], parents[1][j])) {
                    j = (j + 1) % n;
                }
                children[0][k] = parents[1][j];
            }
        }
        j = 0;
        for (int k = 0; k < n; k++) {
            if (!( inArray(ic1,k))) {
                while (inArray(children[1], parents[0][j])) {
                    j = (j + 1) % n;
                }
                children[1][k] = parents[0][j];
            }
        }
        return children;
    }

    private boolean inArray(Integer[] x, Integer v) {
        for (Integer i : x) {
            if (v.equals(i)) {
                return true;
            }
        }
        return false;
    }
}