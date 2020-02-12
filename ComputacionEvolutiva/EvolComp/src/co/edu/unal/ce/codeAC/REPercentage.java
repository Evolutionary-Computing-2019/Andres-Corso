package co.edu.unal.ce.codeAC;

public class REPercentage<T> implements Replace<T> {
    private double perPatents;
    private double perChildren;
    public REPercentage(){
        perPatents = 50;
        perChildren = 50;
    }
    public REPercentage(double pParents, double pChildren){
        this.perPatents = pParents;
        this.perChildren = pChildren;
    }

    @Override
    public Individual<T>[] apply(Individual<T>[] pop, Individual<T>[] children) {
        @SuppressWarnings("unchecked")
        Individual<T>[] newChildren = new Individual[pop.length];
        Integer[] orderP =  Shuffle.apply(pop.length);
        Integer[] orderC =  Shuffle.apply(pop.length);
        Integer[] orderN =  Shuffle.apply(pop.length);
        for(int i = 0; i < (int)(pop.length * (perPatents/100)); i++){
            newChildren[orderN[i]] = pop[orderP[i]];
        }
        for(int i = (int)(pop.length * (perPatents/100)); i < pop.length; i++){
            newChildren[orderN[i]] = children[orderC[i]];
        }
        return newChildren;
    }
}

