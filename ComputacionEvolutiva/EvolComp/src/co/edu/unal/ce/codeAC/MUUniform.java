package co.edu.unal.ce.codeAC;

public class MUUniform implements Mutation<Double> {
    protected double min=0.0;
    protected double length=1.0;

    public MUUniform(){}

    public MUUniform( double min, double max ){
        this.min = min;
        this.length = max-min;
    }
    @Override
    public Double apply(Double child) {
        return child + min +length*Math.random();
    }
}
