package co.edu.unal.ce.codeAC;

public class FURastrigin implements Function<Double[]> {
    private int dim = 10;
    private int A = 10;
    private double minF = -5.12;
    private double maxF = 5.12;

    public FURastrigin(int dim, int A){
        this.dim = dim;
        this.A = A;
    }


    @Override
    public double eval(Double[] x) {
        //Function
        double fitness = A * dim;
        for (int i=0;i<dim;i++){
            fitness += Math.pow(x[i],2) - A*Math.cos(2*Math.PI*x[i]);
        }
        return fitness;
    }

    @Override
    public int getSize() {
        return dim;
    }
}
