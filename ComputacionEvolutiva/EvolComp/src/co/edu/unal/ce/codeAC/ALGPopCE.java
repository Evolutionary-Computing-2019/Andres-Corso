package co.edu.unal.ce.codeAC;

import java.util.ArrayList;

public class ALGPopCE<T1,T2> {
    private Function<T1> f;
    private Function<T2> g;
    private Mutation<T1> m1;
    private Mutation<T2> m2;
    private Crossover<T1> c1;
    private Crossover<T2> c2;
    private Space<T1> e1;
    private Space<T2> e2;
    private Replace<T1> r1;
    private Replace<T2> r2;
    private Selection<T1> s1;
    private Selection<T2> s2;


    private Individual<T1>[] pop1;
    private Individual<T2>[] pop2;

    private Individual<T2> bestI2;

    private ArrayList<Double> bests1;
    private ArrayList<Double> bests2;

    private boolean printBest = false;


    public ALGPopCE(Replace<T1> r1, Replace<T2> r2, Space<T1> e1, Space<T2> e2, Function<T1> f, Function<T2> g, Selection<T1> s1, Selection<T2> s2, Crossover<T1> c1, Crossover<T2> c2, Mutation<T1> m1, Mutation<T2> m2 ){
        this.r1 = r1;
        this.e1 = e1;
        this.f = f;
        this.s1 = s1;
        this.c1 = c1;
        this.m1 = m1;

        this.r2 = r2;
        this.e2 = e2;
        this.g = g;
        this.s2 = s2;
        this.c2 = c2;
        this.m2 = m2;

        this.bests1 = new ArrayList<Double>();
        this.bests2 = new ArrayList<Double>();


    }

    private Individual<T1>[] startPop1(int n){
        @SuppressWarnings("unchecked")
        Individual<T1>[] p = new Individual[n];
        for( int i=0; i<n; i++ ){
            T1 x = e1.get();
            //double vx = f.eval(x);
            p[i] = new Individual<T1>(x, 0);
        }
        return p;
    }

    private Individual<T2>[] startPop2(int n){
        @SuppressWarnings("unchecked")
        Individual<T2>[] p = new Individual[n];
        for( int i=0; i<n; i++ ){
            T2 x = e2.get();
            //double vx = g.eval(x);
            p[i] = new Individual<T2>(x, 0);
        }
        return p;
    }

    private Individual<T1>[] assignFriends1(Individual<T1>[] pop){

        for( int i=0; i<pop.length; i++ ){
            TTP2Ind a = (TTP2Ind) pop[i].x();
            a.setGeneFriend(((TTP2Ind)pop2[a.getFriend()].x()).getGene());
            pop[i].setF(f.eval(pop[i].x()));
        }
        return pop;
    }

    private Individual<T2>[] assignFriends2(Individual<T2>[] pop){

        for( int i=0; i<pop.length; i++ ){
            TTP2Ind a = (TTP2Ind) pop[i].x();
            a.setGeneFriend(((TTP2Ind)pop1[a.getFriend()].x()).getGene());
            pop[i].setF(g.eval(pop[i].x()));
        }
        return pop;
    }



    private Individual<T1> best1(Individual<T1>[] pop){
        Individual<T1> bestI = pop[0];
        for( int i=1; i<pop.length; i++)
            if( bestI.f() > pop[i].f() ) bestI = pop[i];
        return bestI;
    }

    private Individual<T2> best2(Individual<T2>[] pop){
        Individual<T2> bestI = pop[0];
        for( int i=1; i<pop.length; i++)
            if( bestI.f() > pop[i].f() ) bestI = pop[i];
        return bestI;
    }

    private void statistics(int k, Individual<T1> bestI){
        if (printBest) System.out.println(k+" "+bestI.f()+":"+bestI);
    }

    public Individual<T1> apply( int n, int iters ){
        n = (n>>1) << 1;
        //Population 1
        pop1 = startPop1(n);
        Individual<T1> bestI1 = best1(pop1);

        pop2 = startPop2(n);
        bestI2 = best2(pop2);


        pop1 = assignFriends1(pop1);
        pop2 = assignFriends2(pop2);


        for( int i=1; i<=iters; i++){
            @SuppressWarnings("unchecked")
            Individual<T1>[] children1 = new Individual[n];
            @SuppressWarnings("unchecked")
            Individual<T2>[] children2 = new Individual[n];
            for( int k=0; k<n; k+=2){
                Individual<T1>[] parents1 = s1.get(pop1, 2);
                Individual<T2>[] parents2 = s2.get(pop2, 2);

                T1[] created1 = c1.apply(parents1);
                T2[] created2 = c2.apply(parents2);

                created1[0] = e1.repair(m1.apply(created1[0]));
                created1[1] = e1.repair(m1.apply(created1[1]));

                created2[0] = e2.repair(m2.apply(created2[0]));
                created2[1] = e2.repair(m2.apply(created2[1]));

                children1[k] = new Individual<T1>(created1[0], 0);
                children1[k+1] = new Individual<T1>(created1[1], 0);

                children2[k] = new Individual<T2>(created2[0], 0);
                children2[k+1] = new Individual<T2>(created2[1], 0);
                //statistics(-1,children[k]);
                //statistics(-1,children[k+1]);

            }
            assignFriends1(children1);
            assignFriends2(children2);

            pop1 = r1.apply(pop1, children1);
            pop2 = r2.apply(pop2, children2);

            bestI1 = best1(pop1);
            bestI2 = best2(pop2);

            this.bests1.add(bestI1.f());
            this.bests2.add(bestI2.f());

        }

        return bestI1;
    }

    public ArrayList<Double> getBests1() {
        return bests1;
    }

    public ArrayList<Double> getBests2() {
        return bests2;
    }

    public Individual<T2> getBestI2() {
        return bestI2;
    }
}
