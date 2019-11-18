package co.edu.unal.ce.codeAC;

public interface Selection<T> {
    Individual<T>[] get(Individual<T>[] pop, int n );
}
