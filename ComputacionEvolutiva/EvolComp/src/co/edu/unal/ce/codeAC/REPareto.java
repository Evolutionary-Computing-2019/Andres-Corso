package co.edu.unal.ce.codeAC;

public class REPareto<T> implements Replace<T> {
    @Override
    public Individual<T>[] apply(Individual<T>[] pop, Individual<T>[] children) {
        int totP = pop.length + children.length;
        int nSort = 0;
        Integer[] rank = new Integer[totP];
        @SuppressWarnings("unchecked")
        Individual<T>[] all = new Individual[totP];
        System.arraycopy(pop, 0, all, 0, pop.length);
        System.arraycopy(children, 0, all, pop.length, children.length);
        for (int i = 0; i < totP; i++){
            rank[i]=0;
        }
        int currentRank = 0;
        while (totP > nSort){
            currentRank ++;
            for (int i = 0; i < totP; i++){
                if (rank[i] == 0){
                    boolean dom=false;
                    for (int j = 0; j < totP; j++){
                        if (rank[j] == 0 || rank[j] == currentRank) {
                            if (dominated(all[i], all[j])) {
                                dom = true;
                                break;
                            }
                        }
                    }
                    if (!dom) {
                        rank[i] = currentRank;
                        nSort++;
                    }

                }
            }
        }
        @SuppressWarnings("unchecked")
        Individual<T>[] replace = new Individual[pop.length];

        nSort = 0;
        currentRank=1;
        while (nSort<pop.length){
            for (int i=0; i<totP; i++){
                if (rank[i] == currentRank){
                    replace[nSort] = all[i];
                    nSort++;
                    if(nSort==pop.length) break;
                }
            }
            currentRank++;
        }

        return replace;
    }

    private boolean dominated(Individual<T> ind1, Individual<T> ind2) {
        return ind2.f() < ind1.f() && ind2.g() < ind1.g();
    }
}