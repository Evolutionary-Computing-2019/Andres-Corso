package co.edu.unal.ce.codeAC;

import java.util.ArrayList;

public class ALGPopMM <T> {
    private Function<T> f;
    private Mutation<T> m;
    private Crossover<T> c;
    private Space<T> e;
    private Replace<T> r;
    private Selection<T> s;

    private ArrayList<Double> bests;
    private boolean printBest = false;

    public ALGPopMM(Replace<T> r, Space<T> e, Function<T> f, Selection<T> s, Crossover<T> c, Mutation<T> m ){
        this.r = r;
        this.e = e;
        this.f = f;
        this.s = s;
        this.c = c;
        this.m = m;
        this.bests = new ArrayList<Double>();
    }

    private Individual<T>[] startPop(int n){
        @SuppressWarnings("unchecked")
        Individual<T>[] p = new Individual[n];
        for( int i=0; i<n; i++ ){
            T x = e.get();
            double vx = f.eval(x);
            p[i] = new Individual<T>(x, vx);
        }
        return p;
    }

    private Individual<T> best(Individual<T>[] pop){
        Individual<T> bestI = pop[0];
        for( int i=1; i<pop.length; i++)
            if( bestI.f() > pop[i].f() ) bestI = pop[i];
        return bestI;
    }

    private void statistics(int k, Individual<T> bestI){
        if (printBest) System.out.println(k+" "+bestI.f()+":"+bestI);
    }

    public Individual<T>[] apply(int n, int iters ){
        n = (n>>1) << 1;
        Individual<T>[] pop = startPop(n);
        Individual<T> bestI = best(pop);
        statistics(0, bestI);
        for( int i=1; i<=iters; i++){
            @SuppressWarnings("unchecked")
            Individual<T>[] children = new Individual[n];
            for( int k=0; k<n; k+=2){
                Individual<T>[] parents = s.get(pop, 2);

                T[] created = c.apply(parents);

                created[0] = e.repair(m.apply(created[0]));
                created[1] = e.repair(m.apply(created[1]));

                children[k] = new Individual<T>( created[0], f.eval(created[0]));
                children[k+1] = new Individual<T>( created[1], f.eval(created[1]));
                //statistics(-1,children[k]);
                //statistics(-1,children[k+1]);
            }

            pop = r.apply(pop, children);

            bestI = best(pop);
            statistics(i, bestI);
            this.bests.add(bestI.f());
        }
        return pop;
    }

    public ArrayList<Double> getBests() {
        return bests;
    }
}
