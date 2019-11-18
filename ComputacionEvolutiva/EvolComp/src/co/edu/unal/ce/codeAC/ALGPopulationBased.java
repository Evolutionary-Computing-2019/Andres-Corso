package co.edu.unal.ce.codeAC;

import java.util.ArrayList;
import java.util.Objects;

public class ALGPopulationBased<T> {
    private Function<T> f;
    private Mutation<T> m;
    private Crossover<T> c;
    private Space<T> e;
    private Replace<T> r;
    private Selection<T> s;

    private ArrayList<Double> bests;
    private boolean printBest = false;

    public ALGPopulationBased(Replace<T> r, Space<T> e, Function<T> f, Selection<T> s, Crossover<T> c, Mutation<T> m ){
        this.r = r;
        this.e = e;
        this.f = f;
        this.s = s;
        this.c = c;
        this.m = m;
        this.bests = new ArrayList<Double>();
    }

    public Individual<T>[] startPop(int n ){
        @SuppressWarnings("unchecked")
        Individual<T>[] p = new Individual[n];
        for( int i=0; i<n; i++ ){
            T x = e.get();
            double vx = f.eval(x);
            p[i] = new Individual<T>(x, vx);
        }
        return p;
    }

    protected Individual<T> best(Individual<T>[] pop){
        Individual<T> bestI = pop[0];
        for( int i=1; i<pop.length; i++)
            if( bestI.f() > pop[i].f() ) bestI = pop[i];
        return bestI;
    }

    public void statistics( int k, Individual<T> bestI){
        if (printBest) System.out.println(k+" "+bestI.f()+":"+bestI);
    }

    public Individual<T> apply( int n, int iters ){
        n = (n>>1) << 1;
        Individual<T>[] pop = startPop(n);
        Individual<T> bestI = best(pop);
        statistics(0, bestI);
        for( int i=1; i<=iters; i++){
            Individual<T>[] parents = s.get(pop, n);
            @SuppressWarnings("unchecked")
            Individual<T>[] children = new Individual[n];
            for( int k=0; k<n; k+=2){
                /*TODO: Cambiar cada vez*/
                /*Double
                Double[] parentsCR =  new Double[2];
                parentsCR[0] = (Double) parents[k].x();
                parentsCR[1] = (Double) parents[k + 1].x();
                */
                /*Boolean[]
                Boolean[][] parentsCR =  new Boolean[2][];
                parentsCR[0] = (Boolean[]) parents[k].x();
                parentsCR[1] = (Boolean[]) parents[k + 1].x();
                */
                /*Integer[]*/
                Integer[][] parentsCR =  new Integer[2][];
                parentsCR[0] = (Integer[]) parents[k].x();
                parentsCR[1] = (Integer[]) parents[k + 1].x();
                /**/

                T[] created = c.apply((T[])parentsCR);

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
        return bestI;
    }

    public ArrayList<Double> getBests() {
        return bests;
    }
}
