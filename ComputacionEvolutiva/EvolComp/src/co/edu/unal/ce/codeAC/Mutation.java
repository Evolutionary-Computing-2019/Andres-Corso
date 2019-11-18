package co.edu.unal.ce.codeAC;

public interface Mutation<T> {
    T apply( T child );
}
