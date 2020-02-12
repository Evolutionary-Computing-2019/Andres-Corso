package co.edu.unal.ce;

import co.edu.unal.ce.codeAC.*;
import co.edu.unal.ce.tools.Plot;
import co.edu.unal.ce.tools.PlotPareto;

import java.util.ArrayList;

public class TTP2 {
    public static ALGMultiObjective<TTPInd> multiObjective(int nInd, int iterations ){
        String file;

        file = "input/TTP2/20_25_10_75.txt";
        file = "input/TTP2/10_15_1_75.txt";


        Function<TTPInd> f = new FUTTP2_F(file);
        Function<TTPInd> g = new FUTTP2_G(f);

        Mutation<TTPInd> m = new MUInv_CH(f.getSize());

        Crossover<TTPInd> c = new CRMOC_CUT(4);
        
        Space<TTPInd> e = new SPTTP(f,2);

        Replace<TTPInd> r = new REPareto<TTPInd>();
        Selection<TTPInd> s = new SERandom<TTPInd>();
        ALGMultiObjective<TTPInd> ac = new ALGMultiObjective<TTPInd>(r, e, f, g, s, c, m);
        Individual<TTPInd>[] y = ac.apply(nInd, iterations);
        for (Individual<TTPInd>x: y) {
            System.out.println("MultiObjective gets: " + x.f() + " and " + x.g() + " with:\t" + x);
        }
        System.out.println();
        //showRealOpt(file);
        //return ac;
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
        int ejec = 50;
        ArrayList<Individual<TTPInd>[]> paretoFront = new ArrayList<Individual<TTPInd>[]>();
        for (int i = 0; i < ejec; i++) {
            ALGMultiObjective<TTPInd> ac = multiObjective(100, 300);
            bests.add(ac.getBests());
            paretoFront.add(ac.getF1());
        }
        //Plot.report(bests);
        PlotPareto.report(paretoFront);
    }
}
/*Selection pop children*/