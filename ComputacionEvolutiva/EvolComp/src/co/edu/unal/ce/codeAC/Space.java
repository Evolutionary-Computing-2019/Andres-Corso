package co.edu.unal.ce.codeAC;

public interface Space<T> {
    T get();
    T repair(T x);
}
