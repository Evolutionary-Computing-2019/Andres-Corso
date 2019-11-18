package co.edu.unal.ce.codeAC;

public class FUQuadratic implements Function<Double> {
    @Override
    public double eval(Double x) {
        return x*x+x;
    }

    @Override
    public int getSize() {
        return 1;
    }
}
