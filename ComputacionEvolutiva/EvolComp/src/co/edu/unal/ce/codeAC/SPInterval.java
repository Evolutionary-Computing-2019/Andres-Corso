package co.edu.unal.ce.codeAC;

public class SPInterval implements Space<Double> {
    protected MUUniform g;

    protected double min=0.0;
    protected double max=1.0;

    public SPInterval(){ g = new MUUniform(); }

    public SPInterval( double min, double max ){
        this.min = min;
        this.max = max;
        this.g = new MUUniform(min, max);
    }

    @Override
    public Double get() {
        return g.apply(0.0);
    }

    @Override
    public Double repair(Double x) {
        if( x < min ) return min;
        if( x > max ) return max;
        return x;
    }
}
