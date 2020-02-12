package co.edu.unal.ce;

import co.edu.unal.ce.codeAC.*;
import co.edu.unal.ce.tools.Plot;
import co.edu.unal.ce.tools.PlotPareto;

import java.util.ArrayList;

public class TTP2_CE {
    public static ALGPopCE<TTP2Ind, TTP2Ind> coEvolution(int nInd, int iterations ){
        String file;

        file = "input/TTP2/20_25_10_75.txt";
        //file = "input/TTP2/10_15_1_75.txt";


        Function<TTP2Ind> f = new FUTTP2_F_CE(file);
        Function<TTP2Ind> g = new FUTTP2_G_CE(f);


        Space<TTP2Ind> e1 = new SPPermutationCE(f.getSize(), nInd);
        Space<TTP2Ind> e2 = new SPKPTTP2(f, nInd);


        Mutation<TTP2Ind> m1 = new MUInversionCE(f.getSize());
        Mutation<TTP2Ind> m2 = new MUBinaryCE(f.getSize(), nInd);



        Crossover<TTP2Ind> c1 = new CROX1_CE();
        Crossover<TTP2Ind> c2 = new CRCut_CE();


        Replace<TTP2Ind> r1 = new REBestPop1vsCh1<TTP2Ind>();
        Replace<TTP2Ind> r2 = new REBestPop1vsCh1<TTP2Ind>();

        Selection<TTP2Ind> s1 = new SETournament<TTP2Ind>(4, 2.0/3);
        Selection<TTP2Ind> s2 = new SETournament<TTP2Ind>(4, 2.0/3);

        ALGPopCE<TTP2Ind, TTP2Ind> ac = new ALGPopCE<TTP2Ind, TTP2Ind>(r1, r2, e1, e2, f, g, s1, s2, c1, c2, m1, m2);
        Individual<TTP2Ind> y = ac.apply(nInd, iterations);

        System.out.println("Coevolution gets f: " + y.f() + " with:\t" + y);
        System.out.println("Coevolution gets g: " + ac.getBestI2().f() + " with:\t" + ac.getBestI2());


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
        ArrayList<ArrayList<Double>> bests1 = new ArrayList<ArrayList<Double>>();
        ArrayList<ArrayList<Double>> bests2 = new ArrayList<ArrayList<Double>>();
        int ejec = 50;
        ArrayList<Individual<TTPInd>[]> paretoFront = new ArrayList<Individual<TTPInd>[]>();
        for (int i = 0; i < ejec; i++) {
            ALGPopCE<TTP2Ind, TTP2Ind> ac = coEvolution(100, 300);
            bests1.add(ac.getBests1());
            bests2.add(ac.getBests2());

        }
        //Plot.report(bests);
        Plot.report(bests1);
        Plot.report(bests2);

    }
}
