package co.edu.unal.ce.codeAC;

public class CRNoCross implements Crossover<Double[]> {
    @Override
    public Double[][] apply(Individual<Double[]>[] parents) {
        int popSize = parents.length;

        Double[][] children = new Double[popSize][];
        for (int i = 0; i< popSize; i++){
            children[i] = parents[i].x().clone();
        }
        return children;
    }
}
