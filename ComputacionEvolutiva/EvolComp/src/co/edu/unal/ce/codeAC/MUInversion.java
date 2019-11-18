package co.edu.unal.ce.codeAC;

public class MUInversion implements Mutation<Integer[]> {
    @Override
    public Integer[] apply(Integer[] child) {
        int i = (int)(Math.random() * child.length-1);
        int l = (int)(Math.random() * (child.length-i));
        //System.out.println(i+"-"+l);
        for (int k = 0; k <= l/2; k++) {
            Integer tmp = child[i+k];
            child[i+k] = child[i+l-k];
            child[i+l-k] = tmp;
        }
        return child;
    }
}
