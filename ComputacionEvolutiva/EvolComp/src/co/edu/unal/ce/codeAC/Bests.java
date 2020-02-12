package co.edu.unal.ce.codeAC;

public class Bests<T> {
    public Individual<T>[] bests(Individual<T>[] pop){
        @SuppressWarnings("unchecked")
        Individual<T>[] bestI = new Individual[pop.length];
        bestI = pop.clone();
        sort(bestI);
        return bestI;
    }

    private int partition(Individual<T>[] pop, int begin, int end) {
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

    private void quickSort(Individual<T>[] pop, int begin, int end) {
        if (end <= begin) return;
        int pivot = partition(pop, begin, end);
        quickSort(pop, begin, pivot-1);
        quickSort(pop, pivot+1, end);
    }

    private void sort(Individual<T>[] pop){
        quickSort(pop, 0, pop.length-1);
    }
}
