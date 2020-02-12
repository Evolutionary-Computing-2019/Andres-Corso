package co.edu.unal.ce.codeAC;

public class CRCut implements Crossover<Integer[]> {
    @Override
    public Integer[][] apply(Individual<Integer[]>[] parents) {
        Integer[][] children = new Integer[2][];
        children[0] = parents[0].x().clone();
        children[1] = parents[1].x().clone();
        int pc = (int)((children[0].length-1)*Math.random());
        for( int i=pc; i<children[0].length; i++ ){
            Integer tmp = children[0][i];
            children[0][i] = children[1][i];
            children[1][i] = tmp;
        }
        return children;
    }
}