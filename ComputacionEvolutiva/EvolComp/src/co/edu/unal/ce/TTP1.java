package co.edu.unal.ce;

import co.edu.unal.ce.codeAC.*;
import co.edu.unal.ce.tools.Plot;
import co.edu.unal.ce.tools.PlotPareto;


import java.util.ArrayList;

public class TTP1{
    public static ALGPopMM<TTPInd> multimodal(int nInd, int iterations ){
        String file;

        file = "input/TTP1/50_25_3_25.txt";

        Function<TTPInd> f = new FUTTP1(file);

        Mutation<TTPInd> m = new MUInv_CH(f.getSize());

        Crossover<TTPInd> c = new CRMOC_CUT(4);

        Space<TTPInd> e = new SPTTP(f,1);

        Replace<TTPInd> r = new REBestPop1vsCh1<TTPInd>();
        Selection<TTPInd> s = new SESharedF(10);
        ALGPopMM<TTPInd> ac = new ALGPopMM<>(r, e, f, s, c, m);
        Individual<TTPInd>[] x = ac.apply(nInd, iterations);
        for (int i=0; i<5; i++) {
            Bests<TTPInd> b = new Bests<TTPInd>();
            Individual<TTPInd>[] y = b.bests(x);
            System.out.println("Multimodal gets: " + y[i].f() + " with: " + y[i]);
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
        int ejec = 30;
        ArrayList<Individual<TTPInd>[]> paretoFront = new ArrayList<Individual<TTPInd>[]>();
        for (int i = 0; i < ejec; i++) {
            ALGPopMM<TTPInd> ac = multimodal(100, 100);
            bests.add(ac.getBests());

        }
        Plot.report(bests);
        //PlotPareto.report(paretoFront);
    }



}
/*Selection pop children*/