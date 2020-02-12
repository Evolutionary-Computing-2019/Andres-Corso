package sipres;

import sipres.interpreter.*;
import sipres.language.LexicalException;
import sipres.language.Parser;
import sipres.language.SyntacticalException;
import sipres.language.Term;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

public class ALGGeneticProgramming {
    private LinkedList<Term> examples;
    private Fitness f;
    private Evaluator ev;
    private Input data;
    private Extractor ex;
    private Crossover c;
    private Select s;
    private Mutation m;
    private Replace r;

    private int t_iter;

    private Program[] pop;

    private ArrayList<Double> bests;


    public ALGGeneticProgramming(Replace r, Fitness f, Select s, Crossover c, Mutation m, String file) throws LexicalException, ExampleException, SyntacticalException, IOException {
        this.f = f;
        this.r = r;
        this.s = s;
        this.c = c;
        this.m = m;
        this.data = new Input(file);
        this.ev = new Evaluator();
        this.ex = new Extractor(this.data.getExamples());
        this.examples = Parser.parsing(this.data.getExamples());

        this.bests = new ArrayList<Double>();
    }

    private Program[] startPop(int n) throws ProgramException, LexicalException, SyntacticalException, GoalException, ExampleException {
        Program[] p = new Program[n];

        for(int i = 0; i<n; i++ ){
            Program x = new Program(ex.getInitialPopulation(data.getnEq()-1, data.getnTerm(), 0.3));
            x.addEquation(0, ex.getInitialPopulation(1,1,0).getFirst());
            double vx = f.eval(ev, x, data);
            x.setCovering(vx);

            p[i] = x;
        }
//        Term a = p[0].getListEquations().get(0).getLhs();
        return p;
    }

    private Program best(Program[] pop){
        Program bestI = pop[0];
        for( int i=1; i<pop.length; i++)
            if( bestI.getCovering() > pop[i].getCovering() ) bestI = pop[i];
        return bestI;
    }


    public Program apply(int n, int iters) throws GoalException, ProgramException, SyntacticalException, LexicalException, ExampleException {
        n = (n>>1) << 1;
        pop = startPop(n);
        Program bestI = best(pop);
        t_iter = 0;
        while (bestI.getCovering() > -1.0 && t_iter < iters ) {
            int nChildren = 0;
            Program[] children =  new Program[n];

            for( int k=0; k<n; k+=2){
                Program[] parents = s.get(pop, 2);

                Program[] created = c.apply(parents);


                created[0] = m.apply(created[0], ex);
                created[1] = m.apply(created[1], ex);


                created[0].setCovering(f.eval(ev, created[0], data));
                created[1].setCovering(f.eval(ev, created[1], data));


                children[k] = created[0];
                children[k+1] = created[1];


            }


            pop = r.apply(pop, children);

            bestI = best(pop);

            this.bests.add(bestI.getCovering());

            t_iter++;
        }
        return bestI;
    }

    public ArrayList<Double> getBests() {
        return bests;
    }

    public int getT_iter() {
        return t_iter;
    }
}
