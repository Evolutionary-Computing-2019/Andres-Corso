package sipres;

import sipres.interpreter.Extractor;
import sipres.interpreter.Program;
import sipres.language.Term;

import java.util.Random;

public class Mutation {
    private double prob = 0.1;
    public Program apply(Program created, Extractor ex) {
        double mut = Math.random();
        Program mutated = new Program(created);
        if ( mut <= prob ) {
            int neq = (int)(created.getListEquations().size()*Math.random());
            Term x = ex.generateRandomTerm(3, 0.9);
            Random r = new Random();
            if (r.nextBoolean()) {
                mutated.getListEquations().get(neq).setRhs(x);
            }else{
                mutated.getListEquations().get(neq).setLhs(x);
            }
            if (!mutated.getListEquations().get(neq).isValid()) {
                mutated.getListEquations().get(neq).repair();
            }


        }
        
        return mutated;
    }
}
