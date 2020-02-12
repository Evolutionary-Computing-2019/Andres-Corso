package co.edu.unal.ce.codeAC;

public class REBestsPer<T> implements Replace<T> {

    private double perPatents;
    private double perChildren;
    private double perAll;

    public REBestsPer(){
        perPatents = 30;
        perChildren = 50;
        perAll = 20;
    }
    public REBestsPer(double pParents, double pChildren, double pAll){
        this.perPatents = pParents;
        this.perChildren = pChildren;
        this.perAll = pAll;
    }


    private Individual<T>[] bests(Individual<T>[] pop){
        @SuppressWarnings("unchecked")
        Individual<T>[] bestI = new Individual[pop.length];
        bestI = pop.clone();
        sort(bestI);
        return bestI;
    }

    public int partition(Individual<T>[] pop, int begin, int end) {
        int pivot = end;

        int counter = begin;
        for (int i = begin; i < end; i++) {
            if (pop[i].f() < pop[pivot].f()) {
                Individual temp = pop[counter];
                pop[counter] = pop[i];
                pop[i] = temp;
                counter++;
            }
        }
        Individual temp = pop[pivot];
        pop[pivot] = pop[counter];
        pop[counter] = temp;

        return counter;
    }

    public void quickSort(Individual<T>[] pop, int begin, int end) {
        if (end <= begin) return;
        int pivot = partition(pop, begin, end);
        quickSort(pop, begin, pivot-1);
        quickSort(pop, pivot+1, end);
    }

    public void sort(Individual<T>[] pop){
        quickSort(pop, 0, pop.length-1);
    }

    @Override
    public Individual<T>[] apply(Individual<T>[] pop, Individual<T>[] children) {
        Individual<T>[] newChildren = new Individual[pop.length];
        Integer[] orderC =  Shuffle.apply(pop.length);
        Integer[] orderN =  Shuffle.apply(pop.length);

        for(int i = 0; i < (int)(pop.length * (perPatents/100)); i++){
            sort(pop);
            newChildren[orderN[i]] = pop[i];
        }
        for(int i = (int)(pop.length * (perPatents/100)); i < (int)(pop.length * ((100 - perAll)/100)); i++){
            sort(children);
            newChildren[orderN[i]] = children[i-(int)(pop.length * (perPatents/100))];
        }
        for(int i = (int)(pop.length * ((100 - perAll)/100)); i < pop.length; i++){
            newChildren[orderN[i]] = children[orderC[i]];
        }

        return newChildren;
    }
}

