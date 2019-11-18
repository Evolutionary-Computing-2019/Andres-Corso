package co.edu.unal.ce.codeAC;

public class CRBinary implements Crossover<Boolean[]> {
    @Override
    public Boolean[][] apply(Boolean[][] parents) {
        Boolean[][] children = new Boolean[2][];
        children[0] = parents[0].clone();
        children[1] = parents[1].clone();
        int pc = (int)((children[0].length-1)*Math.random());
        for( int i=pc; i<children[0].length; i++ ){
            boolean tmp = children[0][i];
            children[0][i] = children[1][i];
            children[1][i] = tmp;
        }
        return children;
    }
}
