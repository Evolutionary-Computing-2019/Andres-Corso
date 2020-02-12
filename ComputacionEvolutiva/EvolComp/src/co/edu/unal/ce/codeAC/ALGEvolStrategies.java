package co.edu.unal.ce.codeAC;

import java.util.ArrayList;

public class ALGEvolStrategies<T> {
    private Function<T> f;
    private Mutation<T> m;
    private Crossover<T> c;
    private Space<T> e;
    private Replace<T> r;
    private Selection<T> s;

    private ArrayList<Double> bests;
    private boolean printBest = false;

    public ALGEvolStrategies(Replace<T> r, Space<T> e, Function<T> f, Selection<T> s, Crossover<T> c, Mutation<T> m ){
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

    public Individual<T> apply( int miu, int ro,  int lambda, int iters ){
        miu = (miu>>1) << 1;
        Individual<T>[] pop = startPop(miu);
        Individual<T> bestI = best(pop);
        statistics(0, bestI);

        for( int i=1; i<=iters; i++){

            @SuppressWarnings("unchecked")
            Individual<T>[] children = new Individual[lambda];
            for( int k=0; k<lambda; k++){
                Individual<T>[] parents = s.get(pop, ro);

                T created = e.repair(m.apply(c.apply(parents)[0]));

                children[k] = new Individual<T>( created, f.eval(created));

                //statistics(-1,children[k]);
                //statistics(-1,children[k+1]);
            }

            pop = r.apply(pop, children);

            bestI = best(pop);
            statistics(i, bestI);
            this.bests.add(bestI.f());
        }
        return bestI;
    }

    public ArrayList<Double> getBests() {
        return bests;
    }
}
