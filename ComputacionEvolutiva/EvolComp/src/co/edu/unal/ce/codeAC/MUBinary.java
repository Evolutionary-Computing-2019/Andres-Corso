package co.edu.unal.ce.codeAC;

public class MUBinary implements Mutation<Boolean[]>{

    @Override
    public Boolean[] apply(Boolean[] child) {
        double p = 1.0/child.length;
        Boolean[] newChild = child.clone();
        for( int i=0; i<child.length; i++)
            if( Math.random() < p ) newChild[i] = !newChild[i];
        return newChild;
    }
}
