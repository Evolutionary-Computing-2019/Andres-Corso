package co.edu.unal.ce.codeAC;

public class MUDeviation implements Mutation<Double[]>{
    @Override
    public Double[] apply(Double[] child) {

        int size = child.length/2;
        for (int i=0; i<size; i++){
            MUGaussian m = new MUGaussian(0, child[i+size]);
            //System.out.println(m.apply(0.0));
            child[i] += m.apply(0.0);
            child[i+size] += m.apply(0.0);
        }
        return child;
    }
}
