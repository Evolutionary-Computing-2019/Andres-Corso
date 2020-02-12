package co.edu.unal.ce;

import co.edu.unal.ce.codeAC.*;
import co.edu.unal.ce.tools.Plot;

import java.util.ArrayList;

public class RastriginES {
    public static ALGEvolStrategies<Double[]> evolStrategy(int miu, int ro, int lambda, int iterations ){

        int dim = 10;

        Function<Double[]> f = new FURastrigin(dim,10);
        //Function<Double[]> f = new FUSphere(dim);
        //Function<Double[]> f = new FURosenbrock(dim);



        Mutation<Double[]> m = new MUDeviation();

        //Crossover<Double[]> c = new CRLinearC();
        Crossover<Double[]> c = new CRDiscreteES();
        //Crossover<Double[]> c = new CRNoCross();

        //Space<Double[]> e = new SPIntervalD(-5.12, 5.12, dim);
        Space<Double[]> e = new SPIntervalD(-5.12, 5.12, dim);
        Replace<Double[]> r = new REBestPop1vsCh1<Double[]>();
        //Replace<Double[]> r = new REPercentage<Double[]>(10,90); //No es estado estable
        //Replace<Double[]> r = new REBestsPer<Double[]>(30,50,20);

        Selection<Double[]> s = new SETournament<Double[]>(4,2.0/3.0);
        ALGEvolStrategies<Double[]> es = new ALGEvolStrategies<Double[]>(r, e, f, s, c, m);
        Individual<Double[]> x = es.apply(miu, ro, lambda, iterations);
        System.out.println("ES gets: "+x.f()+" with: "+ x);
        //showRealOpt(file);
        return es;

    }
    public static String print(Double[] a){
        String geneString = String.valueOf(a[0]);
        for (int i = 1; i< a.length; i++ ){
            geneString += "-"+String.valueOf(a[i]);

        }
        return geneString;
    }

    public static void main(String[] args) {
        /*
        Mutation<Double[]> m = new MUDisplacement();

        Double[] a = Shuffle.apply(10);
        System.out.println(print(a));
        m.apply(a);
        System.out.println(print(a));

        if (true) return;
         */

        // write your code here
        //local();
        ArrayList<ArrayList<Double>> bests = new ArrayList<ArrayList<Double>>();
        int ejec = 100;
        for (int i = 0; i < ejec; i++) {
            ALGEvolStrategies<Double[]> ac = evolStrategy(50,5,50, 100);
            bests.add(ac.getBests());
        }
        Plot.report(bests);;
    }
}
