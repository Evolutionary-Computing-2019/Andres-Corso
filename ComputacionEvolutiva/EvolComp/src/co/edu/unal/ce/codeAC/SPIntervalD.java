package co.edu.unal.ce.codeAC;

public class SPIntervalD implements Space<Double[]> {
    private MUUniform g;
    private MUUniform s;

    private double min=0.0;
    private double max=1.0;

    private int dim = 1;


    public SPIntervalD(){
        this.g = new MUUniform();
        this.s = new MUUniform(0,1);
    }

    public SPIntervalD( double min, double max, int dimensions){
        this.min = min;
        this.max = max;
        this.dim = dimensions;
        this.g = new MUUniform(min, max);
        this.s = new MUUniform(0,max-min);
    }

    @Override
    public Double[] get() {
        Double[] mGet = new Double[dim*2];
        for (int i = 0; i < dim; i++){
            mGet[i] = g.apply(0.0);
            mGet[i+dim] = s.apply(0.0);
        }
        return mGet;
    }

    @Override
    public Double[] repair(Double[] x) {
        for (int i = 0; i < dim; i++){
            if (x[i] < min) x[i] = min;
            else if (x[i] > max) x[i] = max;
        }
        return x;
    }
}