package co.edu.unal.ce.codeAC;

public interface Replace<T> {
    Individual<T>[] apply(Individual<T>[] x, Individual<T>[] y );
}
