package co.edu.unal.ce.codeAC;

public class FUSphere implements Function<Double[]> {
    private int dim = 10;


    public FUSphere(int dim){
        this.dim = dim;
    }


    @Override
    public double eval(Double[] x) {
        //Function
        double fitness = 0;
        for (int i=0;i<dim;i++){
            fitness += Math.pow(x[i],2);
        }
        return fitness;
    }

    @Override
    public int getSize() {
        return dim;
    }
}
