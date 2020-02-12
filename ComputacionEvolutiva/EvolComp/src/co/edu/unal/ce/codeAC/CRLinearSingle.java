package co.edu.unal.ce.codeAC;

public class CRLinearSingle implements Crossover<Double> {
    @Override
    public Double[] apply(Individual<Double>[] parents) {
        Double[] children = new Double[2];
        double alpha = Math.random();

        children[0] = alpha*parents[0].x() + (1.0-alpha)*parents[1].x();
        children[1] = alpha*parents[1].x() + (1.0-alpha)*parents[0].x();

        return children;
    }
}
