package co.edu.unal.ce.codeAC;

public class CRLinear implements Crossover<Double[]> {
    @Override
    public Double[][] apply(Individual<Double[]>[] parents) {
        Double[][] children = new Double[2][];
        double alpha = Math.random();
        for (int i = 0; i< parents[0].x().length; i++){
            children[0][i] = alpha*parents[0].x()[i] + (1.0-alpha)*parents[1].x()[i];
            children[1][i] = alpha*parents[1].x()[i] + (1.0-alpha)*parents[0].x()[i];
        }
        return children;
    }
}
