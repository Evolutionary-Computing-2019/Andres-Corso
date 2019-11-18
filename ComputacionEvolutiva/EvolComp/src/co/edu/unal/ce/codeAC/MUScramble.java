package co.edu.unal.ce.codeAC;

public class MUScramble implements Mutation<Integer[]> {
    @Override
    public Integer[] apply(Integer[] child) {
        int i = (int)(Math.random() * child.length-1);
        int l = (int)(Math.random() * (child.length-i));
        //System.out.println(i+"-"+l);
        Integer[] scambled = Shuffle.apply(l);
        for (int k = 0; k < l-1; k++) {
            Integer tmp = child[i+scambled[k]];
            child[i+scambled[k]] = child[i+scambled[k]+1];
            child[i+scambled[k]+1] = tmp;
        }
        return child;
    }
}
