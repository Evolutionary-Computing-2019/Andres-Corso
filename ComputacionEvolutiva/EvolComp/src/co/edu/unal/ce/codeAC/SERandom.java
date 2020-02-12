package co.edu.unal.ce.codeAC;

public class SERandom<T> implements Selection<T> {
    @Override
    public Individual<T>[] get(Individual<T>[] pop, int n) {
        @SuppressWarnings("unchecked")
        Individual<T>[] s = new Individual[n];
        for( int i=0; i<n; i++ ){
            s[i] = pop[(int)(pop.length*Math.random())];
        }
        return s;
    }
}
