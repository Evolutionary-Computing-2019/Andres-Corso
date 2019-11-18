package co.edu.unal.ce.codeAC;

public class MUGaussian implements Mutation<Double> {
    protected double miu=0.0;
    protected double sigma=1.0;

    public MUGaussian(){}

    public MUGaussian( double miu, double sigma ){
        this.miu = miu;
        this.sigma = sigma;
    }

    @Override
    public Double apply(Double child) {
        double x,y;
        double r;
        do {
            x = 2.0 * Math.random() - 1.0;
            y = 2.0 * Math.random() - 1.0;
            r = x * x + y * y;
        } while (r >= 1.0);

        double z = Math.sqrt( -2.0 * Math.log(r) / r);
        return sigma*(y * z) + miu;
    }
}
