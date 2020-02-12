package co.edu.unal.ce.codeAC;

public class FURosenbrock implements Function<Double[]> {
    private int dim = 10;

    public FURosenbrock(int dim){
        this.dim = dim;
    }


    @Override
    public double eval(Double[] x) {
        //Function
        double fitness = 100*(Math.pow(x[0] - Math.pow(x[dim-1],2) ,2)) + Math.pow(1-x[dim-1],2);

        for (int i=0;i<dim-1;i++){
            fitness += 100*(Math.pow(x[i+1] - Math.pow(x[i],2) ,2)) + Math.pow(1-x[i],2);
        }
        return fitness;
    }

    @Override
    public int getSize() {
        return dim;
    }
}
