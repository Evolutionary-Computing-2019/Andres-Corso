package co.edu.unal.ce.codeAC;

public class MUBinaryCH implements Mutation<Integer[]>{
    private int n;

    public MUBinaryCH(int nC){
        this.n = nC;
    }

    @Override
    public Integer[] apply(Integer[] child) {
        double p = 1.0/child.length;
        Integer[] newChild = child.clone();
        for( int i=0; i<child.length; i++)
            if( Math.random() < p ) {
                if (child[i] >= 0) {
                    newChild[i] = -1;
                }else{
                    newChild[i] = (int)(n*Math.random());
                }
            }
        return newChild;
    }
}
