package co.edu.unal.ce.codeAC;

public class CRDiscreteES implements Crossover<Double[]> {

    @Override
    public Double[][] apply(Individual<Double[]>[] parents) {
        Double[][] child = new Double[1][parents[0].x().length];

        int popSize = parents.length;

        for (int i = 0; i<parents[0].x().length;i++){
            int choice = (int) (Math.random()*popSize);
            child[0][i] = parents[choice].x()[i];
        }

        return child;
    }

}
