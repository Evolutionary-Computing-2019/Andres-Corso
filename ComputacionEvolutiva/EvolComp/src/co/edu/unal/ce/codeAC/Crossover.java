package co.edu.unal.ce.codeAC;

public interface Crossover<T>{
    T[] apply( Individual<T>[] parents );
}
