package co.edu.unal.ce.codeAC;

import java.util.ArrayList;

public class ALGMultiObjective<T>{
    private Function<T> f;
    private Function<T> g;
    private Mutation<T> m;
    private Crossover<T> c;
    private Space<T> e;
    private Replace<T> r;
    private Selection<T> s;

    private Individual<T>[] F1;
    private ArrayList<Double> bests;

    private boolean printBest = false;


    public ALGMultiObjective(Replace<T> r, Space<T> e, Function<T> f, Function<T> g, Selection<T> s, Crossover<T> c, Mutation<T> m ){
        this.r = r;
        this.e = e;
        this.f = f;
        this.g = g;
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
            double vy = g.eval(x);
            p[i] = new Individual<T>(x, vx, vy);
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

    public Individual<T>[] apply( int n, int iters ){
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

                children[k] = new Individual<T>( created[0], f.eval(created[0]), g.eval(created[0]));
                children[k+1] = new Individual<T>( created[1], f.eval(created[1]), g.eval(created[1]));
                //statistics(-1,children[k]);
                //statistics(-1,children[k+1]);
            }

            pop = r.apply(pop, children);

            bestI = best(pop);
            statistics(i, bestI);
            this.bests.add(bestI.f());
            get1Pareto(pop);


        }
        F1 = get1Pareto(pop);

        return F1;
    }

    private Individual<T>[] get1Pareto(Individual<T>[] all) {

        int totP = all.length;

        Integer[] rank = new Integer[totP];


        for (int i = 0; i < totP; i++){
            rank[i]=0;
        }
        int currentRank = 1;
        int nSort=0;
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

        @SuppressWarnings("unchecked")
        Individual<T>[] pareto1F = new Individual[nSort];


        currentRank=1;


        nSort=0;
        for (int i=0; i<totP; i++){
            if (rank[i] == currentRank){
                pareto1F[nSort] = all[i];
                nSort++;
            }
        }
        return pareto1F;

    }

    private boolean dominated(Individual<T> ind1, Individual<T> ind2) {
        return ind2.f() < ind1.f() && ind2.g() < ind1.g();
    }

    public ArrayList<Double> getBests() {
        return bests;
    }

    public Individual<T>[] getF1() {
        return F1;
    }

}

