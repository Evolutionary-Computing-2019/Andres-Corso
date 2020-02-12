package co.edu.unal.ce;

import co.edu.unal.ce.codeAC.*;
import co.edu.unal.ce.tools.Plot;

import java.util.ArrayList;

public class TSP {
    public static ALGPopulationBased<Integer[]> popBased(int nInd, int iterations ){
        String file;

        file = "input/TSP/att48.tsp";
        //file = "input/TSP/ulysses16.tsp";
        //file = "input/TSP/berlin52.tsp";
        Function<Integer[]> f = new FUTSP(file);

        Mutation<Integer[]> m = new MUInversion();
        //Mutation<Integer[]> m = new MUScramble();
        //Mutation<Integer[]> m = new MUSwap();

        Crossover<Integer[]> c = new CROX1();
        //Crossover<Integer[]> c = new CRPosBased(4);
        //Crossover<Integer[]> c = new CRMOC(4);

        Space<Integer[]> e = new SPPermutation(f.getSize());
        Replace<Integer[]> r = new REBestPop1vsCh1<Integer[]>();
        Selection<Integer[]> s = new SETournament<Integer[]>(4,2.0/3.0);
        ALGPopulationBased<Integer[]> ac = new ALGPopulationBased<Integer[]>(r, e, f, s, c, m);
        Individual<Integer[]> x = ac.apply(nInd, iterations);
        System.out.println("PopBased gets: "+x.f()+" with: "+ x);
        //showRealOpt(file);
        return ac;

    }
    public static String print(Integer[] a){
        String geneString = String.valueOf(a[0]);
        for (int i = 1; i< a.length; i++ ){
            geneString += "-"+String.valueOf(a[i]);

        }
        return geneString;
    }

    public static void main(String[] args) {
        /*
        Mutation<Integer[]> m = new MUDisplacement();

        Integer[] a = Shuffle.apply(10);
        System.out.println(print(a));
        m.apply(a);
        System.out.println(print(a));

        if (true) return;
         */

        // write your code here
        //local();
        ArrayList<ArrayList<Double>> bests = new ArrayList<ArrayList<Double>>();
        int ejec = 30;
        for (int i = 0; i < ejec; i++) {
            ALGPopulationBased<Integer[]> ac = popBased(100, 300);
            bests.add(ac.getBests());
        }
        Plot.report(bests);;
    }
}
/*Selection pop children*/