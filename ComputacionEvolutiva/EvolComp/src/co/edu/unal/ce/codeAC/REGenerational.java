package co.edu.unal.ce.codeAC;

public class REGenerational<T> implements Replace<T> {
    @Override
    public Individual<T>[] apply(Individual<T>[] x, Individual<T>[] y) {
        return y;
    }
}
