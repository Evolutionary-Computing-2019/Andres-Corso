package co.edu.unal.ce.codeAC;

public class MUSwap implements Mutation<Integer[]> {
    @Override
    public Integer[] apply(Integer[] child) {
        int i = (int)(Math.random() * child.length-1);
        int j = (int)(Math.random() * child.length-1);
        //System.out.println(i+"-"+j);
        Integer tmp = child[i];
        child[i] = child[j];
        child[j] = tmp;
        return child;
    }
}
