package co.edu.unal.ce.codeAC;

import co.edu.unal.ce.codeAC.Crossover;

public class CRLinearC implements Crossover<Double[]> {

    @Override
    public Double[][] apply(Individual<Double[]>[] parents) {
        Double[][] child = new Double[1][parents[0].x().length];

        for (int i = 0; i< parents[0].x().length; i++){

            child[0][i] = parents[0].x()[i];
            for ( int j = 1; j < parents.length; j++){
                child[0][i] += parents[j].x()[i];
            }
            child[0][i] = child[0][i]/parents.length;
        }
        return child;
    }

}
