package co.edu.unal.ce.codeAC;

public class REBestPop1vsCh1<T> implements Replace<T> {
    @Override
    public Individual<T>[] apply(Individual<T>[] pop, Individual<T>[] children) {
        @SuppressWarnings("unchecked")
        Individual<T>[] bests = new Individual[pop.length];
        Integer[] orderP =  Shuffle.apply(pop.length);
        Integer[] orderC =  Shuffle.apply(pop.length);
        for(int i = 0; i < pop.length; i++){
            if( children[orderC[i]].f() <= pop[orderP[i]].f() ){
                bests[i] = children[orderC[i]];
            }else{
                bests[i] = pop[orderP[i]];
            }

        }
        return bests;
    }
}
