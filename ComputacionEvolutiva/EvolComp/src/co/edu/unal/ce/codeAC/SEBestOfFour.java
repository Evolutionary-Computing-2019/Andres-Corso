package co.edu.unal.ce.codeAC;

public class SEBestOfFour<T> implements Selection<T> {
    private Individual<T> selBestOfFour(Individual<T>[] pop){
        Individual<T> best = pop[(int)(pop.length*Math.random())];
        for(int i=1; i<=3; i++ ){
            Individual<T> comp = pop[(int)(pop.length*Math.random())];
            if( comp.f() < best.f() ) best = comp;
        }
        return best;
    }
    @Override
    public Individual<T>[] get(Individual<T>[] pop, int n) {
        @SuppressWarnings("unchecked")
        Individual<T>[] s = new Individual[n];
        for( int i=0; i<n; i++ ){
            s[i] = selBestOfFour(pop);
        }
        return s;
    }
}
