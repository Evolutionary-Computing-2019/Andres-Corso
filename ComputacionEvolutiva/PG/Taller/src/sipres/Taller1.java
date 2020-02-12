package sipres;

import sipres.interpreter.*;
import sipres.language.LexicalException;
import sipres.language.SyntacticalException;
import unalcol.optimization.selection.Selection;

import java.io.IOException;
import java.util.ArrayList;

public class Taller1 {

    public static ALGGeneticProgramming geneticPrograming(int nInd, int iterations ) throws GoalException, ProgramException, SyntacticalException, LexicalException, IOException, ExampleException {
        String file = "data/iff.txt";//OK
        //String file = "data/even.txt"; //OK
        //String file = "data/min.txt"; //OK
        //String file = "data/mod3.txt"; //OK

        //String file = "data/geq.txt"; //T_F


        //String file = "data/double.txt";//P
        //String file = "data/max.txt";//P
        //String file = "data/sum.txt"; //P
        Fitness f = new Fitness();
        Crossover c = new Crossover();
        Mutation m = new Mutation();
        Replace r = new Replace();
        Select s = new Select();
        ALGGeneticProgramming ac = new ALGGeneticProgramming(r, f, s, c, m, file);
        Program x = ac.apply(nInd, iterations);
        System.out.println("GP gets: "+x.getCovering()+" with: "+ x + "\t\tIter: "+ac.getT_iter());
        return ac;

    }
    public static void main(String[] args) throws SyntacticalException, IOException, ExampleException, LexicalException, GoalException, ProgramException {

        ArrayList<ArrayList<Double>> bests = new ArrayList<ArrayList<Double>>();

        int ejec = 10;
        for (int i = 0; i < ejec; i++) {
            ALGGeneticProgramming ac = geneticPrograming(300, 100);
            bests.add(ac.getBests());
        }

        Plot.report(bests);


    }
}
