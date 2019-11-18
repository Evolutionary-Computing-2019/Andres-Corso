package co.edu.unal.ce.codeAC;

public class CROX1 implements Crossover<Integer[]> {

    @Override
    public Integer[][] apply(Integer[][] parents) {
        int n = parents[0].length;
        int i = (int)(Math.random() * n-1);
        int l = (int)(Math.random() * (n-i));
        //System.out.println("i: "+i+ " l: "+l);
        Integer[][] children = new Integer[2][n];
        int j = 0;
        for (int k = 0; k < n; k++){
            if(k<l){
                children[0][k+i] = parents[0][k+i];
                j = (i+k+1)%n;
            }else{
                while (inArray(children[0], parents[1][j])){
                    j = (j+1)%n;
                }
                children[0][(k+i)%n] = parents[1][j];
            }
        }
        j = (l+1)%n;
        for (int k = 0; k < n; k++){
            if(k<l){
                children[1][k+i] = parents[1][k+i];

            }else{
                while (inArray(children[1], parents[0][j])){
                    j = (j+1)%n;
                }
                children[1][(k+i)%n] = parents[0][j];
            }
        }
        return children;
    }

    private boolean inArray(Integer[] x, Integer v){
        for (Integer i: x){
            if(v.equals(i)){
                return true;
            }
        }
        return false;
    }
}
