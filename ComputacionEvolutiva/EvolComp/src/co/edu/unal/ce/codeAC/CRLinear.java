package co.edu.unal.ce.codeAC;

public class CRLinear implements Crossover<Double> {
    @Override
    public Double[] apply(Double[] parents) {
        Double[] children = new Double[2];
        double alpha = Math.random();
        children[0] = alpha*parents[0] + (1.0-alpha)*parents[1];
        children[1] = alpha*parents[1] + (1.0-alpha)*parents[0];
        return children;
    }
}
