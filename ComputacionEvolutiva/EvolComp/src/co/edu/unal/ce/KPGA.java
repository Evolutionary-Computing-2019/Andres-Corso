package co.edu.unal.ce;

import co.edu.unal.ce.codeAC.*;
import co.edu.unal.ce.tools.Plot;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class KPGA {
    public static ALGPopulationBased<Boolean[]> popBased(int nInd, int iterations ){

        /*
        Low Dimensional
        file = "input/instances_KP/low-dimensional/f1_l-d_kp_10_269";
        file = "input/instances_KP/low-dimensional/f2_l-d_kp_20_878";
        file = "input/instances_KP/low-dimensional/f3_l-d_kp_4_20";
        file = "input/instances_KP/low-dimensional/f4_l-d_kp_4_11";
        file = "input/instances_KP/low-dimensional/f5_l-d_kp_15_375";
        file = "input/instances_KP/low-dimensional/f6_l-d_kp_10_60";
        file = "input/instances_KP/low-dimensional/f7_l-d_kp_7_50";
        file = "input/instances_KP/low-dimensional/f8_l-d_kp_23_10000";
        file = "input/instances_KP/low-dimensional/f9_l-d_kp_5_80";
        file = "input/instances_KP/low-dimensional/f10_l-d_kp_20_879";

        LargeScale

        file = "input/instances_KP/large_scale/knapPI_1_10000_1000_1";
        file = "input/instances_KP/large_scale/knapPI_1_500_1000_1";
        file = "input/instances_KP/large_scale/knapPI_1_200_1000_1";
        file = "input/instances_KP/large_scale/knapPI_1_1000_1000_1";
        file = "input/instances_KP/large_scale/knapPI_1_100_1000_1";
        file = "input/instances_KP/large_scale/knapPI_1_2000_1000_1";
        file = "input/instances_KP/large_scale/knapPI_1_5000_1000_1";
        file = "input/instances_KP/large_scale/knapPI_2_5000_1000_1";
        file = "input/instances_KP/large_scale/knapPI_2_100_1000_1";
        file = "input/instances_KP/large_scale/knapPI_2_2000_1000_1";
        file = "input/instances_KP/large_scale/knapPI_2_200_1000_1";
        file = "input/instances_KP/large_scale/knapPI_2_10000_1000_1";
        file = "input/instances_KP/large_scale/knapPI_2_1000_1000_1";
        file = "input/instances_KP/large_scale/knapPI_2_500_1000_1";
        file = "input/instances_KP/large_scale/knapPI_3_10000_1000_1";
        file = "input/instances_KP/large_scale/knapPI_3_1000_1000_1";
        file = "input/instances_KP/large_scale/knapPI_3_100_1000_1";
        file = "input/instances_KP/large_scale/knapPI_3_2000_1000_1";
        file = "input/instances_KP/large_scale/knapPI_3_200_1000_1";
        file = "input/instances_KP/large_scale/knapPI_3_5000_1000_1";

         */
        String file;
        //file = "input/instances_KP/large_scale/knapPI_1_100_1000_1";
        file = "input/instances_KP/large_scale/knapPI_1_200_1000_1";
        file = "input/instances_KP/large_scale/knapPI_1_1000_1000_1";



        Function<Boolean[]> f = new FUKP(file);
        //Mutation<Double> m = new MUUniform();
        Mutation<Boolean[]> m = new MUBinary();
        Crossover<Boolean[]> c = new CRBinary();
        Space<Boolean[]> e = new SPBinary(1.0/f.getSize(), f.getSize());
        Replace<Boolean[]> r = new REBestPop1vsCh1<Boolean[]>();
        //Replace<Boolean[]> r = new REGenerational<>();
        Selection<Boolean[]> s = new SETournament<Boolean[]>(4,2.0/3.0);
        ALGPopulationBased<Boolean[]> ac = new ALGPopulationBased<Boolean[]>(r, e, f, s, c, m);
        Individual<Boolean[]> x = ac.apply(nInd, iterations);
        System.out.println("PopBased gets:"+x.f()+" with: "+ x);
        showRealOpt(file);
        return ac;

    }

    private static void showRealOpt(String file){
        String[] opt =  file.split("/");
        opt[2] += "-optimum";
        String fileOptimum = String.join("/",opt);
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileOptimum));
            String line = br.readLine();
            System.out.println(line);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        // write your code here
        //local();
        ArrayList<ArrayList<Double>> bests = new ArrayList<ArrayList<Double>>();
        int ejec = 30;
        for (int i = 0; i < ejec; i++) {

            ALGPopulationBased<Boolean[]> ac = popBased(200, 300);
            bests.add(ac.getBests());
        }
        Plot.report(bests);;
    }
}
